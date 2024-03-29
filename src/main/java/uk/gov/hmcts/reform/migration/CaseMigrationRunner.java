package uk.gov.hmcts.reform.migration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.CollectionUtils;
import uk.gov.hmcts.reform.domain.exception.MigrationLimitReachedException;
import uk.gov.hmcts.reform.idam.client.models.User;
import uk.gov.hmcts.reform.migration.processor.MigrationProcessor;
import uk.gov.hmcts.reform.migration.repository.IdamRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@SpringBootApplication
@PropertySource("classpath:application.yml")
@EnableFeignClients(basePackages = {
    "uk.gov.hmcts.reform.idam.client"})
@RequiredArgsConstructor
public class CaseMigrationRunner implements CommandLineRunner {

    private final MigrationProcessor migrationProcessor;
    private final MigrationProperties migrationProperties;
    private final IdamRepository idamRepository;

    private final CaseIdsFileReader caseIdsFileReader;

    public static void main(String[] args) {
        SpringApplication.run(CaseMigrationRunner.class, args);
    }

    @Override
    public void run(String... args) {
        StopWatch stopWatch = StopWatch.createStarted();
        try {
            User user = idamRepository.authenticateUser();
            log.info("User authentication successful.");
            var caseIds = caseIdsFileReader.readCaseIds();
            if (!CollectionUtils.isEmpty(caseIds)) {
                log.info("Data migration of cases from file caseIds.txt started: " + migrationProperties.getCaseIds());
                caseIds
                    .stream()
                    .map(String::trim)
                    .forEach(caseId -> {
                        migrationProcessor.migrateSingleCase(user, caseId);
                    });
            } else if (migrationProperties.getCaseIds() != null && !migrationProperties.getCaseIds().isBlank()) {
                log.info("Data migration of cases from caseIds property started: " + migrationProperties.getCaseIds());
                List<String> caseIdsList = Stream.of(migrationProperties.getCaseIds().split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
                caseIdsList
                    .stream()
                    .map(String::trim)
                    .forEach(caseId -> {
                        migrationProcessor.migrateSingleCase(user, caseId);
                    });
            } else {
                log.info("Data migration of cases started");
                migrationProcessor.process(user);
            }
        } catch (MigrationLimitReachedException ex) {
            log.info("Data migration stopped after limit reached");
        } catch (Exception e) {
            log.error("Migration failed with the following reason: {}", e.getMessage(), e);
        } finally {
            stopWatch.stop();
            log.info("-----------------------------------------");
            log.info("Data migration completed in: {} minutes ({} seconds).",
                stopWatch.getTime(TimeUnit.MINUTES), stopWatch.getTime(TimeUnit.SECONDS)
            );
            log.info("-----------------------------------------");
        }
    }
}
