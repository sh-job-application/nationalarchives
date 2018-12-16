import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CsvServiceTest {
    private final CsvService csvService = new CsvService();

    @Test
    public void readsFileWithNoRowData() throws URISyntaxException, FileNotFoundException {
        Path csvInputPath = fixturePath("emptyRows.csv");
        List<CsvData> csvData = csvService.readCsv(csvInputPath);

        assertTrue(csvData.isEmpty());
    }

    @Test(expected = FileNotFoundException.class)
    public void rejectsNonExistentFile() throws FileNotFoundException {
        Path missingPath = Paths.get("nonExistentFile.csv");
        csvService.readCsv(missingPath);
    }

    private Path fixturePath(String fixtureFile) throws URISyntaxException {
        URL fileUrl = this.getClass()
                .getResource("/fixtures/csvServiceTest/" + fixtureFile);
        return Paths.get(fileUrl.toURI());
    }
}