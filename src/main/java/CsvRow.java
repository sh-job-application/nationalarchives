import org.apache.commons.csv.CSVRecord;

import java.util.Map;

public class CsvRow {
    private final Map<String, String> fields;

    public CsvRow(CSVRecord csvRecord) {
        fields = csvRecord.toMap();
    }

    public String get(String columnName) {
        return fields.get(columnName);
    }
}
