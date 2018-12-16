package nationalarchives.techtest;

import nationalarchives.techtest.data.CsvFile;
import nationalarchives.techtest.service.CsvService;
import nationalarchives.techtest.ui.Arguments;

import java.io.IOException;
import java.nio.file.Path;

public class CsvUpdater {
    private final CsvService csvService;

    public CsvUpdater(CsvService csvService) {
        this.csvService = csvService;
    }

    public void updateCsv(String[] args) throws IOException {
        Arguments arguments = Arguments.parse(args);

        Path filePath = arguments.getFilePath();

        CsvFile csvFile = csvService.readCsv(filePath);
        csvFile.updateField(arguments.getColumnName(), arguments.getRowNumber(), arguments.getNewValue());
        csvService.saveCsv(csvFile, filePath);
    }

    public static void main(String[] args) throws IOException {
        CsvService csvService = new CsvService();
        new CsvUpdater(csvService).updateCsv(args);
    }
}
