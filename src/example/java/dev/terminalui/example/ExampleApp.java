package dev.terminalui.example;

import dev.terminalui.*;
import dev.terminalui.component.*;
import dev.terminalui.menu.Menu;
import dev.terminalui.style.Themes;
import java.util.List;

public final class ExampleApp {
    public static void main(String[] args) {
        Menu settings=Menu.builder("Settings").subtitle("Shape the experience")
            .action("1","Choose a theme","Changes can be applied when constructing your app",ui->{String theme=ui.choose("Theme",List.of("Modern","Ocean","Minimal"));ui.success(theme+" selected.");})
            .back("b","Back").build();

        Menu home=Menu.builder("Acme workspace").subtitle("Everything important, one keystroke away")
            .action("1","View project summary",ui->{
                ui.heading("Project summary");ui.panel("Status","Build is healthy","Last release: 2 days ago");ui.blank();
                ui.table(Table.builder("Service","State","Latency").row("API","Healthy","42 ms").row("Worker","Healthy","18 ms").build());
            })
            .action("2","Create a release","Guided and validated",ui->{String name=ui.ask("Release name");boolean publish=ui.confirm("Publish "+name+" now?");if(publish)ui.success(name+" published.");else ui.info("Release kept as a draft.");})
            .submenu("s","Settings",settings).exit("q","Quit").build();

        TerminalUi.builder(home).theme(Themes.ocean()).run();
    }
}
