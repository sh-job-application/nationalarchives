package nationalarchives.techtest;

import nationalarchives.techtest.data.CsvFile;
import nationalarchives.techtest.service.CsvService;
import nationalarchives.techtest.ui.Arguments;

import java.io.IOException;

public class CsvUpdater {
    private final CsvService csvService;

    public CsvUpdater(CsvService csvService) {
        this.csvService = csvService;
    }

    public void updateCsv(String[] args) throws IOException {
        Arguments arguments = Arguments.parse(args);

        CsvFile csvFile = csvService.readCsv(arguments.getFilePath());
    }

    public static void main(String[] args) throws IOException {
        CsvService csvService = new CsvService();
        new CsvUpdater(csvService).updateCsv(args);
    }
}
