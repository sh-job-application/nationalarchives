import java.nio.file.Path;
import java.nio.file.Paths;

public class Arguments {
    private final Path filePath;

    public Arguments(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFilePath() {
        return filePath;
    }

    public static Arguments parse(String[] args) {
        if (args.length == 0) {
            throw new InvalidArgumentException();
        }

        return new Arguments(Paths.get(args[0]));
    }

    public static class InvalidArgumentException extends RuntimeException {
    }
}
