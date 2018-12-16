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

        String pathArg = args[0];
        if (pathArg.isEmpty()) {
            throw new InvalidArgumentException("CSV file path cannot be empty");
        }

        return new Arguments(Paths.get(args[0]), args[1]);
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
