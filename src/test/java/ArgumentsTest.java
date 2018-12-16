import org.junit.Test;

public class ArgumentsTest {
    @Test(expected = Arguments.InvalidArgumentException.class)
    public void rejectsEmptyArguments() {
        Arguments.parse(new String[] {});
    }
}