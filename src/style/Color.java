package style;

// Use like this: System.out.println(Color.GREEN.apply("Success!"));

public enum Color {

    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    CYAN("\u001B[36m");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    public String apply(String text) {
        return code + text + RESET.code;
    }
}