import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvService {
    public List<CsvData> readCsv(Path filePath) throws FileNotFoundException {
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException(String.format("Could not find file at %s", filePath));
        }

        return new ArrayList<>();
    }
}
