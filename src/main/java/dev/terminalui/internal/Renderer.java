package dev.terminalui.internal;

import dev.terminalui.io.TerminalIO;
import dev.terminalui.menu.Menu;
import dev.terminalui.menu.MenuItem;
import dev.terminalui.style.TextStyle;
import dev.terminalui.style.Theme;
import java.util.List;

public final class Renderer {
    private final TerminalIO io; private final Theme theme; private final boolean ansi;
    public Renderer(TerminalIO io, Theme theme, boolean ansi){this.io=io;this.theme=theme;this.ansi=ansi;}
    public Theme theme(){return theme;}
    int width(){return Math.min(theme.contentWidth(),Math.max(30,io.width()-2));}
    public void clear(){if(ansi){io.print("\u001b[H\u001b[2J\u001b[3J");io.flush();}}
    public String style(String text,TextStyle style){return style.apply(text,ansi);}
    public void menu(Menu menu,int depth){
        int w=width(); String inner=theme.horizontal().repeat(w-2);
        io.println(style(theme.topLeft()+inner+theme.topRight(),theme.muted()));
        io.println(style("  "+menu.title(),theme.title()));
        if(menu.subtitle()!=null&&!menu.subtitle().isBlank())io.println(style("  "+menu.subtitle(),theme.muted()));
        if(depth>1)io.println(style("  "+"· ".repeat(depth-1)+"current",theme.muted()));
        io.println(style(theme.bottomLeft()+inner+theme.bottomRight(),theme.muted())); io.println("");
        for(MenuItem item:menu.items()){
            String key=style("["+item.key()+"]",item.enabled()?theme.accent():theme.muted());
            String label=item.enabled()?item.label():style(item.label(),theme.muted());
            io.println("  "+style(theme.pointer(),theme.muted())+" "+key+"  "+label);
            if(item.hint()!=null&&!item.hint().isBlank())io.println("       "+style(item.hint(),theme.muted()));
        }
        io.println("");
    }
    public void status(String symbol,String message,TextStyle style){io.println("  "+style(symbol,style)+"  "+message);}
    public void prompt(String label){io.print("  "+style(theme.prompt(),theme.accent())+" "+label+" ");io.flush();}
    public void heading(String title){io.println(style(title,theme.title()));io.println(style(theme.horizontal().repeat(Math.min(width(),Math.max(8,title.length()))),theme.muted()));}
    public void panel(String title,List<String> lines){
        int w=width(); io.println(style(theme.topLeft()+theme.horizontal().repeat(w-2)+theme.topRight(),theme.muted()));
        if(title!=null&&!title.isBlank())io.println(style(theme.vertical(),theme.muted())+" "+style(title,theme.title()));
        for(String line:lines) for(String wrapped:Text.wrap(line,w-4)) io.println(style(theme.vertical(),theme.muted())+" "+wrapped);
        io.println(style(theme.bottomLeft()+theme.horizontal().repeat(w-2)+theme.bottomRight(),theme.muted()));
    }
}
