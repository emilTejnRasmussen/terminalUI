package dev.terminalui.io;

import java.io.PrintWriter;
import java.util.Scanner;

/** Terminal I/O backed by standard input and output. */
public final class SystemTerminalIO implements TerminalIO {
    private final Scanner input;
    private final PrintWriter output;

    public SystemTerminalIO() { this(new Scanner(System.in), new PrintWriter(System.out, true)); }
    public SystemTerminalIO(Scanner input, PrintWriter output) { this.input = input; this.output = output; }
    @Override public String readLine() { return input.hasNextLine() ? input.nextLine() : null; }
    @Override public void print(String value) { output.print(value); }
    @Override public void flush() { output.flush(); }
    @Override public int width() {
        try { return Math.max(40, Integer.parseInt(System.getenv().getOrDefault("COLUMNS", "80"))); }
        catch (NumberFormatException ignored) { return 80; }
    }
}
