package nationalarchives.techtest.data;

import java.util.List;

public class CsvFile {
    private final List<String> columnNames;
    private final List<CsvRow> rows;

    public CsvFile(List<String> columnNames, List<CsvRow> rows) {
        this.columnNames = columnNames;
        this.rows = rows;
    }

    public List<CsvRow> getRows() {
        return rows;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }
}
