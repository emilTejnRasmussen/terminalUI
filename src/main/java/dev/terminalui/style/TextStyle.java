package dev.terminalui.style;

/** Immutable terminal text style. */
public record TextStyle(Color color, boolean bold, boolean dim) {
    public static TextStyle of(Color color) { return new TextStyle(color, false, false); }
    public TextStyle bolded() { return new TextStyle(color, true, dim); }
    public TextStyle dimmed() { return new TextStyle(color, bold, true); }
    public String apply(String text, boolean ansi) {
        if (!ansi || text.isEmpty()) return text;
        return (bold ? "\u001b[1m" : "") + (dim ? "\u001b[2m" : "") + color.sequence() + text + "\u001b[0m";
    }
}
