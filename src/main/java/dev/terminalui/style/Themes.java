package dev.terminalui.style;

public final class Themes {
    private Themes() { }
    public static Theme modern() { return Theme.builder().build(); }
    public static Theme ocean() { return Theme.builder().accent(TextStyle.of(Color.BRIGHT_BLUE)).title(TextStyle.of(Color.BRIGHT_CYAN).bolded()).build(); }
    public static Theme minimal() { return Theme.builder().border("-", "|", "+", "+", "+", "+").pointer(">").prompt(">").build(); }
    public static Theme monochrome() {
        TextStyle normal = TextStyle.of(Color.DEFAULT);
        return Theme.builder().accent(normal).title(normal.bolded()).muted(normal.dimmed()).success(normal).warning(normal).error(normal).build();
    }
}
