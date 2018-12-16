public class Arguments {
    public static Arguments parse(String[] args) {
        throw new InvalidArgumentException();
    }

    public static class InvalidArgumentException extends RuntimeException {
    }
}
