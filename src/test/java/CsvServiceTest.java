import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class CsvServiceTest {
    private final CsvService csvService = new CsvService();

    @Test
    public void readsFileWithNoRowData() throws URISyntaxException, IOException {
        Path csvInputPath = fixturePath("emptyRows.csv");
        CsvFile csvFile = csvService.readCsv(csvInputPath);

        assertTrue(csvFile.getRows().isEmpty());
    }

    @Test
    public void readsHeadersFromFileWithData() throws URISyntaxException, IOException {
        Path csvInputPath = fixturePath("hasData.csv");
        CsvFile csvFile = csvService.readCsv(csvInputPath);

        List<String> columnNames = csvFile.getColumnNames();
        assertThat(columnNames).containsExactly("column1", "column2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsCompletelyEmptyFile() throws URISyntaxException, IOException {
        Path missingPath = fixturePath("empty.csv");

        csvService.readCsv(missingPath);
    }

    @Test(expected = FileNotFoundException.class)
    public void rejectsNonExistentFile() throws IOException {
        Path missingPath = Paths.get("nonExistentFile.csv");
        csvService.readCsv(missingPath);
    }

    private Path fixturePath(String fixtureFile) throws URISyntaxException {
        URL fileUrl = this.getClass()
                .getResource("/fixtures/csvServiceTest/" + fixtureFile);
        return Paths.get(fileUrl.toURI());
    }
}