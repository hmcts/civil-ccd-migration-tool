package uk.gov.hmcts.reform.migration.service;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class DataMigrationServiceImpl implements DataMigrationService<Map<String, Object>> {

    private static final String MIGRATION_ID_KEY = "migrationId";
    private static final String MIGRATION_ID_VALUE = "GSMigration";

    @Override
    public Predicate<CaseDetails> accepts() {
        return caseDetails -> Optional.ofNullable(caseDetails)
            .filter(caseAlreadyProcessed())
            .isPresent();
    }

    private Predicate<CaseDetails> caseAlreadyProcessed() {
        return caseDetails -> !caseDetails.getData().containsKey(MIGRATION_ID_KEY)
            || !caseDetails.getData().getOrDefault(MIGRATION_ID_KEY, "").equals(MIGRATION_ID_VALUE);
    }

    @Override
    public Map<String, Object> migrate(CaseDetails caseDetails) {
        /*
         Populate a map here with data that wants to be present when connecting with the callback service.
        */
        return Map.of(MIGRATION_ID_KEY, MIGRATION_ID_VALUE);
    }
}
