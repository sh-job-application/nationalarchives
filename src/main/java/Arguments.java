import java.nio.file.Path;
import java.nio.file.Paths;

public class Arguments {
    private final Path filePath;
    private final String columnName;

    public Arguments(Path filePath, String columnName) {
        this.filePath = filePath;
        this.columnName = columnName;
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getColumnName() {
        return columnName;
    }

    public static Arguments parse(String[] args) {
        if (args.length == 0) {
            throw new InvalidArgumentException();
        }

        Path path = parsePath(args[0]);
        String columnName = parseColumnName(args[1]);

        return new Arguments(path, columnName);
    }

    private static Path parsePath(String pathArg) {
        if (pathArg.isEmpty()) {
            throw new InvalidArgumentException("CSV file path cannot be empty");
        }

        return Paths.get(pathArg);
    }

    private static String parseColumnName(String columnNameArg) {
        if (columnNameArg.isEmpty()) {
            throw new InvalidArgumentException("Column name cannot be empty");
        }

        return columnNameArg;
    }

    public static class InvalidArgumentException extends RuntimeException {
        public InvalidArgumentException() {
            super();
        }

        public InvalidArgumentException(String message) {
            super(message);
        }
    }
}
