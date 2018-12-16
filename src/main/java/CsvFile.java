import java.util.ArrayList;
import java.util.List;

public class CsvFile {
    private final List<String> columnNames;

    public CsvFile(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<CsvData> getRows() {
        return new ArrayList<>();
    }

    public List<String> getColumnNames() {
        return columnNames;
    }
}
