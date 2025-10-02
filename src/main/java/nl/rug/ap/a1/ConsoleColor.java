package nl.rug.ap.a1;

public enum ConsoleColor {
    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    BLUE("\u001B[94m"),
    YELLOW("\u001B[33m");

    private final String code;

    ConsoleColor(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}

