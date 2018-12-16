package nationalarchives.techtest.ui;

import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class ArgumentsTest {
    @Test
    public void parsesValidFilePath() {
        Arguments arguments = Arguments.parse(new String[] {"someFilePath", "someColumnName", "7", "someNewValue"});

        assertEquals(Paths.get("someFilePath"), arguments.getFilePath());
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsEmptyFilePath() {
        Arguments.parse(new String[] {"", "someColumnName", "7", "someNewValue"});
    }

    @Test
    public void parsesValidColumnName() {
        Arguments arguments = Arguments.parse(new String[] {"someFilePath", "someColumnName", "7", "someNewValue"});

        assertEquals("someColumnName", arguments.getColumnName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsEmptyColumnName() {
        Arguments.parse(new String[] {"someFilePath", "", "7", "someNewValue"});
    }

    @Test
    public void parsesValidRowNumber() {
        Arguments arguments = Arguments.parse(new String[] {"someFilePath", "someColumnName", "7", "someNewValue"});

        assertEquals(7, arguments.getRowNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsMissingRowNumber() {
        Arguments.parse(new String[] {"someFilePath", "someColumnName", "", "someNewValue"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsInvalidRowNumber() {
        Arguments.parse(new String[] {"someFilePath", "someColumnName", "notANumber", "someNewValue"});
    }

    @Test
    public void parsesNewFieldValue() {
        Arguments arguments = Arguments.parse(new String[] {"someFilePath", "someColumnName", "7", "someNewValue"});

        assertEquals("someNewValue", arguments.getNewValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsEmptyArguments() {
        Arguments.parse(new String[] {});
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsWrongNumberOfArguments() {
        Arguments.parse(new String[] {"1", "2", "3", "4", "5"});
    }
}