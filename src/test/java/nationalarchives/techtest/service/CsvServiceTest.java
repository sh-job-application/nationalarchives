package nationalarchives.techtest.service;

import nationalarchives.techtest.data.CsvFile;
import nationalarchives.techtest.data.CsvRow;
import nationalarchives.techtest.service.CsvService;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
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

    @Test
    public void readsValuesFromFileWithData() throws URISyntaxException, IOException {
        Path csvInputPath = fixturePath("hasData.csv");
        CsvFile csvFile = csvService.readCsv(csvInputPath);

        List<CsvRow> rows = csvFile.getRows();
        assertThat(rows).hasSize(2);
        assertThat(rows.get(0).get("column1")).isEqualTo("value1");
        assertThat(rows.get(0).get("column2")).isEqualTo("value2");
        assertThat(rows.get(1).get("column1")).isEqualTo("value3");
        assertThat(rows.get(1).get("column2")).isEqualTo("value4");
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