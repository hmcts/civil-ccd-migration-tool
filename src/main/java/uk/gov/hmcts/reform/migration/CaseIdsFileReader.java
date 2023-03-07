package uk.gov.hmcts.reform.migration;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CaseIdsFileReader {

    public List<String> readCaseIds() throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource("caseIds.txt").toURI());

        return Files.lines(path)
            .filter(Objects::nonNull)
            .filter(Predicate.not(String::isEmpty))
            .collect(Collectors.toList());
    }
}
