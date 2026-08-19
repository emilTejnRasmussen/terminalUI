package dev.terminalui;

import dev.terminalui.component.ProgressBar;
import dev.terminalui.io.AnsiMode;
import dev.terminalui.menu.Menu;
import dev.terminalui.testing.ScriptedTerminal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TerminalUiTest {
    @Test void runsANestedJourneyAndReturnsHome(){
        Menu child=Menu.builder("Child").back("b","Back").build();
        Menu root=Menu.builder("Home").submenu("1","Open",child).exit("q","Quit").build();
        ScriptedTerminal terminal=new ScriptedTerminal("1","b","q");
        TerminalUi.builder(root).io(terminal).ansi(AnsiMode.DISABLED).clearScreen(false).pauseAfterAction(false).run();
        assertTrue(terminal.output().contains("Home"));assertTrue(terminal.output().contains("Child"));
    }
    @Test void validatesInputAndExecutesAction(){
        Menu root=Menu.builder("App").action("1","Age",ui->ui.success("Age: "+ui.askInt("Age",1,120))).exit("q","Quit").build();
        ScriptedTerminal terminal=new ScriptedTerminal("1","nope","140","42","q");
        TerminalUi.builder(root).io(terminal).ansi(AnsiMode.DISABLED).clearScreen(false).pauseAfterAction(false).run();
        assertTrue(terminal.output().contains("Enter a whole number from 1 to 120"));assertTrue(terminal.output().contains("Age: 42"));
    }
    @Test void rejectsDuplicateKeys(){assertThrows(IllegalArgumentException.class,()->Menu.builder("X").exit("q","One").exit("Q","Two"));}
    @Test void progressIsClamped(){assertEquals("█████ 100%",ProgressBar.render(20,10,5));}
}
