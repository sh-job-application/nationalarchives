package nationalarchives.techtest.data;

import java.util.Map;

public class CsvRow {
    private final Map<String, String> fields;

    public CsvRow(Map<String, String> fields) {
        this.fields = fields;
    }

    public String get(String columnName) {
        return fields.get(columnName);
    }

    public void updateField(String columnName, String newValue) {
        fields.put(columnName, newValue);
    }
}
