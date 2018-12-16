import java.util.List;

public class CsvUpdater {
    private final CsvService csvService;

    public CsvUpdater(CsvService csvService) {
        this.csvService = csvService;
    }

    public void updateCsv(String[] args) {
        Arguments arguments = Arguments.parse(args);

        List<CsvData> csvData = csvService.readCsv(arguments.getFilePath());
    }

    public static void main(String[] args) {
        CsvService csvService = new CsvService();
        new CsvUpdater(csvService).updateCsv(args);
    }
}
