package nationalarchives.techtest.data;

import java.util.*;

public class CsvFileBuilder {
    private List<String> columnNames = new ArrayList<>();
    private List<CsvRow> rows = new ArrayList<>();


    public CsvFileBuilder withHeaders(String... columnNames) {
        this.columnNames = Arrays.asList(columnNames);
        return this;
    }

    public CsvFileBuilder withRow(String... values) {
        Map<String, String> rowData = new HashMap<>();

        for (int i = 0; i < columnNames.size(); i++) {
            rowData.put(columnNames.get(i), values[i]);
        }

        rows.add(new CsvRow(rowData));

        return this;
    }

    public CsvFile build() {
        return new CsvFile(columnNames, rows);
    }
}
