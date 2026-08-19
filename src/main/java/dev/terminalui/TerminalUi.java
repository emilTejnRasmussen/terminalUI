package dev.terminalui;

import dev.terminalui.internal.Renderer;
import dev.terminalui.io.AnsiMode;
import dev.terminalui.io.SystemTerminalIO;
import dev.terminalui.io.TerminalIO;
import dev.terminalui.menu.Menu;
import dev.terminalui.menu.MenuItem;
import dev.terminalui.style.Theme;
import dev.terminalui.style.Themes;
import java.util.*;
import java.util.function.Consumer;

/** Runs an interactive, menu-driven terminal application. */
public final class TerminalUi {
    private final Deque<Menu> stack=new ArrayDeque<>(); private final TerminalIO io; private final Renderer renderer;
    private final boolean clearScreen,pauseAfterAction,catchActionErrors; private final Consumer<Exception> errorHandler; private boolean running;
    private TerminalUi(Builder b){stack.push(b.root);io=b.io;boolean ansi=b.ansiMode==AnsiMode.ENABLED||(b.ansiMode==AnsiMode.AUTO&&b.io.interactive());renderer=new Renderer(io,b.theme,ansi);clearScreen=b.clearScreen;pauseAfterAction=b.pauseAfterAction;catchActionErrors=b.catchActionErrors;errorHandler=b.errorHandler;}
    public static Builder builder(Menu root){return new Builder(root);}
    public void run(){
        running=true; UiContext context=new UiContext(this,io,renderer);
        while(running&&!stack.isEmpty()){
            if(clearScreen)renderer.clear(); Menu current=stack.peek();renderer.menu(current,stack.size());renderer.prompt("Select:");String input=io.readLine();
            if(input==null){exit();break;} Optional<MenuItem> selected=current.find(input);if(selected.isEmpty()){context.error("Unknown option ‘"+input.trim()+"’. Try one of the keys above.");context.pause();continue;}
            MenuItem item=selected.get();if(!item.enabled()){context.warning("That option is currently unavailable.");context.pause();continue;} execute(item,context);
        }
        io.flush();
    }
    private void execute(MenuItem item,UiContext context){
        switch(item.kind()){
            case SUBMENU->open(item.submenu()); case BACK->back(); case EXIT->exit();
            case ACTION->{if(clearScreen)renderer.clear();try{item.action().execute(context);}catch(Exception e){if(!catchActionErrors)throw new TerminalUiException("Menu action failed",e);context.error(e.getMessage()==null?"Something went wrong.":e.getMessage());errorHandler.accept(e);}if(running&&pauseAfterAction)context.pause();}
        }
    }
    void open(Menu menu){stack.push(Objects.requireNonNull(menu));} void back(){if(stack.size()>1)stack.pop();} void home(){while(stack.size()>1)stack.pop();} void exit(){running=false;}
    public static final class Builder {
        private final Menu root; private TerminalIO io=new SystemTerminalIO(); private Theme theme=Themes.modern(); private AnsiMode ansiMode=AnsiMode.AUTO;
        private boolean clearScreen=true,pauseAfterAction=true,catchActionErrors=true; private Consumer<Exception> errorHandler=e->{};
        private Builder(Menu root){this.root=Objects.requireNonNull(root,"root menu");}
        public Builder io(TerminalIO value){io=Objects.requireNonNull(value);return this;} public Builder theme(Theme value){theme=Objects.requireNonNull(value);return this;}
        public Builder ansi(AnsiMode value){ansiMode=Objects.requireNonNull(value);return this;} public Builder clearScreen(boolean value){clearScreen=value;return this;}
        public Builder pauseAfterAction(boolean value){pauseAfterAction=value;return this;} public Builder catchActionErrors(boolean value){catchActionErrors=value;return this;}
        public Builder onError(Consumer<Exception> value){errorHandler=Objects.requireNonNull(value);return this;} public TerminalUi build(){return new TerminalUi(this);} public void run(){build().run();}
    }
}
