package dev.terminalui;

import dev.terminalui.component.Panel;
import dev.terminalui.component.Table;
import dev.terminalui.internal.Renderer;
import dev.terminalui.io.TerminalIO;
import dev.terminalui.menu.Menu;
import dev.terminalui.style.TextStyle;
import dev.terminalui.style.Theme;
import java.util.*;
import java.util.function.Predicate;

/** The action-facing API for input, output and journey control. */
public final class UiContext {
    private final TerminalUi app; private final TerminalIO io; private final Renderer renderer;
    UiContext(TerminalUi app,TerminalIO io,Renderer renderer){this.app=app;this.io=io;this.renderer=renderer;}
    public void open(Menu menu){app.open(menu);} public void back(){app.back();} public void home(){app.home();} public void exit(){app.exit();}
    public Theme theme(){return renderer.theme();}
    public void print(String text){io.println(text);} public void blank(){io.println("");}
    public void heading(String text){renderer.heading(text);} public void panel(String title,String... lines){new Panel(title,List.of(lines)).render(this);}
    public void table(Table table){table.render(this);} public void success(String message){renderer.status("✓",message,theme().success());}
    public void info(String message){renderer.status("i",message,theme().accent());} public void warning(String message){renderer.status("!",message,theme().warning());}
    public void error(String message){renderer.status("×",message,theme().error());}
    public String ask(String label){return ask(label,null,s->!s.isBlank(),"A value is required.");}
    public String ask(String label,String defaultValue){return ask(label,defaultValue,s->true,null);}
    public String ask(String label,String defaultValue,Predicate<String> validator,String validationMessage){
        while(true){renderer.prompt(label+(defaultValue==null?":":" ["+defaultValue+"]:"));String value=io.readLine();if(value==null){app.exit();return defaultValue;}
            value=value.trim();if(value.isEmpty()&&defaultValue!=null)value=defaultValue;if(validator.test(value))return value;error(validationMessage==null?"Invalid value.":validationMessage);}
    }
    public int askInt(String label,int min,int max){
        while(true){String value=ask(label);try{int number=Integer.parseInt(value);if(number>=min&&number<=max)return number;}catch(NumberFormatException ignored){}error("Enter a whole number from "+min+" to "+max+".");}
    }
    public boolean confirm(String label){return confirm(label,false);}
    public boolean confirm(String label,boolean defaultValue){
        String suffix=defaultValue?" [Y/n]:":" [y/N]:";
        while(true){renderer.prompt(label+suffix);String value=io.readLine();if(value==null){app.exit();return false;}value=value.trim().toLowerCase(Locale.ROOT);
            if(value.isEmpty())return defaultValue;if(Set.of("y","yes").contains(value))return true;if(Set.of("n","no").contains(value))return false;error("Answer yes or no.");}
    }
    public String choose(String label,List<String> choices){
        if(choices.isEmpty())throw new IllegalArgumentException("choices cannot be empty");
        blank();heading(label);for(int i=0;i<choices.size();i++)io.println("  "+renderer.style("["+(i+1)+"]",theme().accent())+"  "+choices.get(i));
        return choices.get(askInt("Choose",1,choices.size())-1);
    }
    public void pause(){renderer.prompt("Press Enter to continue");io.readLine();}
    public void renderPanel(String title,List<String> lines){renderer.panel(title,lines);}
    public String styled(String text,TextStyle style){return renderer.style(text,style);}
}
