package dev.terminalui.io;

/** Replaceable input/output boundary, useful for testing and embedding. */
public interface TerminalIO {
    String readLine();
    void print(String value);
    default void println(String value) { print(value + System.lineSeparator()); }
    default void flush() { }
    default boolean interactive() { return System.console() != null; }
    default int width() { return 80; }
}
