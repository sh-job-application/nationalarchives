import org.apache.commons.csv.CSVRecord;

import java.util.List;
import java.util.stream.Collectors;

public class CsvFile {
    private final List<String> columnNames;
    private final List<CsvRow> rows;

    public CsvFile(List<String> columnNames, List<CSVRecord> records) {
        this.columnNames = columnNames;
        rows = records.stream().map(strings -> new CsvRow(strings)).collect(Collectors.toList());
    }

    public List<CsvRow> getRows() {
        return rows;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }
}
