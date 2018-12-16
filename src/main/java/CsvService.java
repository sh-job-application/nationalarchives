import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class CsvService {
    public CsvFile readCsv(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException(String.format("Could not find file at %s", filePath));
        }

        File file = new File(filePath.toUri());
        Reader fileReader = new FileReader(file);
        CSVParser csvRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader);

        Map<String, Integer> headerMap = csvRecords.getHeaderMap();
        if (headerMap.isEmpty()) {
            throw new IllegalArgumentException("Cannot update CSV with no header row");
        }

        return new CsvFile();
    }
}
