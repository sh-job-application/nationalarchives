package nationalarchives.techtest.service;

import nationalarchives.techtest.data.CsvFile;
import nationalarchives.techtest.data.CsvRow;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CsvService {
    public CsvFile readCsv(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException(String.format("Could not find file at %s", filePath));
        }

        Reader fileReader = new FileReader(filePath.toFile());
        CSVParser csvParser = CSVFormat.DEFAULT
                .withIgnoreSurroundingSpaces()
                .withFirstRecordAsHeader()
                .parse(fileReader);

        Map<String, Integer> headerMap = csvParser.getHeaderMap();
        List<CSVRecord> records = csvParser.getRecords();

        validateHeaders(headerMap);
        validateRecords(records);

        List<String> columnNames = getOrderedColumnNames(headerMap);
        List<CsvRow> rows = buildCsvRows(records);

        return new CsvFile(columnNames, rows);
    }

    private void validateHeaders(Map<String, Integer> headers) {
        if (headers.isEmpty()) {
            throw new InvalidCsvException("Cannot update CSV with no header row");
        }
    }

    private void validateRecords(List<CSVRecord> records) {
        if (records.stream().anyMatch(record -> !record.isConsistent())) {
            throw new InvalidCsvException("Some CSV rows have different number of fields to column names");
        }
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

    private List<CsvRow> buildCsvRows(List<CSVRecord> csvRecords) throws IOException {
        return csvRecords
                .stream()
                .map(record -> new CsvRow(record.toMap()))
                .collect(Collectors.toList());
    }

    private static List<String> getOrderedColumnNames(Map<String, Integer> headerMap) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(headerMap.entrySet());
        entries.sort(Comparator.comparing(Map.Entry::getValue));

        return entries.stream().map(stringIntegerEntry -> stringIntegerEntry.getKey()).collect(Collectors.toList());
    }

    public class InvalidCsvException extends RuntimeException {

        public InvalidCsvException(String message) {
            super(message);
        }
    }
}
