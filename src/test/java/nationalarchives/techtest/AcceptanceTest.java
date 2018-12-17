package nationalarchives.techtest;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.Assert.assertEquals;

public class AcceptanceTest {

    private static final String INPUT_FILE = "/fixtures/acceptanceTests/validInput.csv";
    private static final String EXPECTED_OUTPUT_FILE = "/fixtures/acceptanceTests/expectedOutput.csv";
    private static final String RESTORE_INPUT_FILE = "/fixtures/acceptanceTests/originalInput.csv";

    @Before
    public void resetInputFile() throws IOException {
        File backupFile = new File(this.getClass().getResource(RESTORE_INPUT_FILE).getFile());
        File inputFile = new File(this.getClass().getResource(INPUT_FILE).getFile());

        Path backupPath = Paths.get(backupFile.getAbsolutePath());
        Path inputPath = Paths.get(inputFile.getAbsolutePath());

        Files.copy(backupPath, inputPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void updatesFieldInValidCsv() throws IOException {
        URL inputPath = this.getClass().getResource("/fixtures/acceptanceTests/validInput.csv");
        File inputFile = new File(inputPath.getFile());
        URL expectedOutputPath = this.getClass().getResource("/fixtures/acceptanceTests/expectedOutput.csv");

        String[] inputArgs = new String[] {inputFile.getAbsolutePath(), "origin", "3", "London"};
        CsvUpdater.main(inputArgs);

        String updatedContents = readFile(inputPath);
        String expectedOutput = readFile(expectedOutputPath);
        assertEquals(expectedOutput, updatedContents);
    }

    private static String readFile(URL url) throws IOException {
        File file = new File(url.getFile());
        return FileUtils.readFileToString(file, Charset.defaultCharset());
    }
}
