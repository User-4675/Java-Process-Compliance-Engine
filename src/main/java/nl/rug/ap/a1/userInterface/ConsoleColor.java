package nl.rug.ap.a1.userInterface;

/**
 * Represents ANSI escape codes for coloring console text.
 * <p>
 * Each enum constant corresponds to a color or style, and its {@link #toString()}
 * method returns the ANSI code that can be used in console output.
 */
public enum ConsoleColor {

    /** Resets text formatting and color to default. */
    RESET("\u001B[0m"),

    /** Red text color. */
    RED("\u001B[31m"),

    /** Green text color. */
    GREEN("\u001B[32m"),

    /** Blue text color. */
    BLUE("\u001B[94m"),

    /** Yellow text color. */
    YELLOW("\u001B[33m");

    /** The ANSI escape code for this color. */
    private final String code;

    /**
     * Constructs a new {@code ConsoleColor} with the given ANSI escape code.
     *
     * @param code the ANSI escape code for this color
     */
    ConsoleColor(final String code) {
        this.code = code;
    }

    /**
     * Returns the ANSI escape code of this color.
     *
     * @return the ANSI code as a {@link String}
     */
    @Override
    public String toString() {
        return code;
    }
}
