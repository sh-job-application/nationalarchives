package nationalarchives.techtest.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Arguments {
    private final Path filePath;
    private final String columnName;
    private final int rowNumber;
    private final String newValue;

    public Arguments(Path filePath, String columnName, int rowNumber, String newValue) {
        this.filePath = filePath;
        this.columnName = columnName;
        this.rowNumber = rowNumber;
        this.newValue = newValue;
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getColumnName() {
        return columnName;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public String getNewValue() {
        return newValue;
    }

    public static Arguments parse(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentException(
                    String.format("Expected 4 arguments but got %d", args.length)
            );
        }

        Path path = parsePath(args[0]);
        String columnName = parseColumnName(args[1]);
        int rowNumber = parseRowNumber(args[2]);
        String newValue = args[3];

        return new Arguments(path, columnName, rowNumber, newValue);
    }

    private static Path parsePath(String pathArg) {
        if (pathArg.isEmpty()) {
            throw new IllegalArgumentException("CSV file path cannot be empty");
        }

        return Paths.get(pathArg);
    }

    private static String parseColumnName(String columnNameArg) {
        if (columnNameArg.isEmpty()) {
            throw new IllegalArgumentException("Column name cannot be empty");
        }

        return columnNameArg;
    }

    private static int parseRowNumber(String rowNumberArg) {
        try {
            return Integer.parseInt(rowNumberArg);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    String.format("Invalid row number: got '%s' but expected an integer", rowNumberArg),
                    e
            );
        }
    }
}
