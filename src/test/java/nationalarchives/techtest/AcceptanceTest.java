package nationalarchives.techtest;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

public class AcceptanceTest {

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
