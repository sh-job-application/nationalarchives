package nationalarchives.techtest.service;

import nationalarchives.techtest.data.CsvFile;
import nationalarchives.techtest.data.CsvFileBuilder;
import nationalarchives.techtest.data.CsvRow;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CsvServiceTest {
    private final CsvService csvService = new CsvService();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void readsFileWithNoRowData() throws IOException {
        Path csvInputPath = fixturePath("emptyRows.csv");
        CsvFile csvFile = csvService.readCsv(csvInputPath);

        assertTrue(csvFile.getRows().isEmpty());
    }

    @Test
    public void readsHeadersFromFileWithData() throws IOException {
        Path csvInputPath = fixturePath("hasData.csv");
        CsvFile csvFile = csvService.readCsv(csvInputPath);

        List<String> columnNames = csvFile.getColumnNames();
        assertThat(columnNames).containsExactly("column1", "column2");
    }

    @Test
    public void readsValuesFromFileWithData() throws IOException {
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
    public void rejectsCompletelyEmptyFile() throws IOException {
        Path missingPath = fixturePath("empty.csv");

        csvService.readCsv(missingPath);
    }

    @Test(expected = FileNotFoundException.class)
    public void rejectsNonExistentFile() throws IOException {
        Path missingPath = Paths.get("nonExistentFile.csv");
        csvService.readCsv(missingPath);
    }

    @Test
    public void savesDataToFile() throws IOException {
        CsvFile csvFile = new CsvFileBuilder()
                .withHeaders("someHeader", "otherHeader")
                .withRow("value1", "value2")
                .withRow("value3", "value4")
                .build();
        Path path = outputPath("output.csv");

        csvService.saveCsv(csvFile, path);

        String expectedContents = "someHeader,otherHeader\n" +
                "value1,value2\n" +
                "value3,value4\n";
        String fileContents = readFile(path);
        assertEquals(expectedContents, fileContents);
    }

    private Path fixturePath(String fixtureFileName) {
        URL fileUrl = this.getClass().getResource("/fixtures/csvServiceTest/" + fixtureFileName);
        return Paths.get(fileUrl.getPath());
    }

    private Path outputPath(String outputFileName) throws IOException {
        File tempFile = temporaryFolder.newFile(outputFileName);
        return tempFile.toPath();
    }

    private static String readFile(Path path) throws IOException {
        return FileUtils.readFileToString(path.toFile(), Charset.defaultCharset());
    }
}