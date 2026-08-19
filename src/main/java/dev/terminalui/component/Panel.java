package dev.terminalui.component;

import dev.terminalui.UiContext;
import java.util.*;

public record Panel(String title, List<String> lines) {
    public Panel { lines=List.copyOf(lines); }
    public static Panel of(String title,String... lines){return new Panel(title,List.of(lines));}
    public void render(UiContext ui){ui.renderPanel(title,lines);}
}
