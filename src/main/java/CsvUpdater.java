import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class CsvUpdater {
    private final CsvService csvService;

    public CsvUpdater(CsvService csvService) {
        this.csvService = csvService;
    }

    public void updateCsv(String[] args) throws IOException {
        Arguments arguments = Arguments.parse(args);

        List<CsvData> csvData = csvService.readCsv(arguments.getFilePath());
    }

    public static void main(String[] args) throws IOException {
        CsvService csvService = new CsvService();
        new CsvUpdater(csvService).updateCsv(args);
    }
}
