import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class ArgumentsTest {
    @Test
    public void parsesValidFilePath() {
        Arguments arguments = Arguments.parse(new String[] {"someFilePath", "origin", "7", "someNewValue"});

        assertEquals(Paths.get("someFilePath"), arguments.getFilePath());
    }

    @Test(expected = Arguments.InvalidArgumentException.class)
    public void rejectsEmptyFilePath() {
        Arguments arguments = Arguments.parse(new String[] {"", "origin", "7", "someNewValue"});
    }

    @Test(expected = Arguments.InvalidArgumentException.class)
    public void rejectsEmptyArguments() {
        Arguments.parse(new String[] {});
    }
}