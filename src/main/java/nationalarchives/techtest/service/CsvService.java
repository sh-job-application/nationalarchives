package nationalarchives.techtest.service;

import nationalarchives.techtest.data.CsvFile;
import nationalarchives.techtest.data.CsvRow;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CsvService {
    public CsvFile readCsv(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException(String.format("Could not find file at %s", filePath));
        }

        File file = new File(filePath.toUri());
        Reader fileReader = new FileReader(file);
        CSVParser csvRecords = CSVFormat.DEFAULT
                .withIgnoreSurroundingSpaces()
                .withFirstRecordAsHeader()
                .parse(fileReader);

        Map<String, Integer> headerMap = csvRecords.getHeaderMap();
        if (headerMap.isEmpty()) {
            throw new IllegalArgumentException("Cannot update CSV with no header row");
        }

        List<String> columnNames = getOrderedColumnNames(headerMap);
        List<CsvRow> rows = csvRecords.getRecords()
                .stream()
                .map(record -> new CsvRow(record.toMap()))
                .collect(Collectors.toList());

        return new CsvFile(columnNames, rows);
    }

    public void saveCsv(CsvFile csvFile, Path path) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            List<String> columnNames = csvFile.getColumnNames();
            CSVPrinter csvPrinter = CSVFormat.DEFAULT
                    .withRecordSeparator("\n")
                    .withHeader(columnNames.toArray(new String[columnNames.size()]))
                    .print(writer);

            for (CsvRow row : csvFile.getRows()) {
                List<String> record = columnNames.stream()
                        .map(columnName -> row.get(columnName))
                        .collect(Collectors.toList());
                csvPrinter.printRecord(record);
            }
        }
    }

    private static List<String> getOrderedColumnNames(Map<String, Integer> headerMap) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(headerMap.entrySet());
        entries.sort(Comparator.comparing(Map.Entry::getValue));

        return entries.stream().map(stringIntegerEntry -> stringIntegerEntry.getKey()).collect(Collectors.toList());
    }
}
