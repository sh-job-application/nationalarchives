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

    public void updateField(String columnName, int rowNumber, String newValue) {
        if (!columnNames.contains(columnName)) {
            throw new IllegalArgumentException(String.format(
                    "Column name '%s' not in columns in file: %s",
                    columnName,
                    String.join(", ", columnNames)
            ));
        }
        if (rowNumber < 1 || rowNumber > rows.size()) {
            throw new IllegalArgumentException(String.format(
                    "Cannot update row %d of CSV with %d rows",
                    rowNumber,
                    rows.size()
            ));
        }

        int rowIndex = rowNumber - 1;
        rows.get(rowIndex).updateField(columnName, newValue);
    }
}
