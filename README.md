# TerminalUI

A polished, dependency-free Java library for building menu-driven terminal applications. TerminalUI handles the repetitive parts—layout, navigation, input validation, colour support and errors—while application code stays focused on the journey.

Requires Java 17 or newer.

## Quick start

Install the library into your local Maven repository:

```shell
mvn install
```

Then add it to another Maven project:

```xml
<dependency>
    <groupId>dev.terminalui</groupId>
    <artifactId>terminalui</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

Create a menu and run it:

```java
import dev.terminalui.*;
import dev.terminalui.menu.Menu;
import dev.terminalui.style.Themes;

Menu home = Menu.builder("My application")
    .subtitle("What would you like to do?")
    .action("1", "Greet someone", ui -> {
        String name = ui.ask("Name");
        ui.success("Hello, " + name + "!");
    })
    .exit("q", "Quit")
    .build();

TerminalUi.builder(home)
    .theme(Themes.modern())
    .run();
```

See [ExampleApp.java](src/example/java/dev/terminalui/example/ExampleApp.java) for nested menus, guided actions, panels and tables.

## Menus and journeys

Menus are immutable and reusable. An action receives a `UiContext`, which can collect input, display content, or change where the user goes next.

```java
Menu settings = Menu.builder("Settings")
    .action("1", "Profile", "Change your display name", ui -> {
        String name = ui.ask("Display name", "Guest");
        ui.success("Saved " + name);
    })
    .back("b", "Back")
    .build();

Menu home = Menu.builder("Dashboard")
    .submenu("s", "Settings", settings)
    .action("r", "Restricted", "Available after sign-in", user::isSignedIn,
        ui -> ui.open(accountMenu))
    .exit("q", "Quit")
    .build();
```

Actions can call `open(menu)`, `back()`, `home()` or `exit()`. Checked exceptions from actions are presented cleanly by default; use `.catchActionErrors(false)` during development if you prefer them to propagate.

## Input and output

`UiContext` includes:

- `ask`, defaults and custom validation
- bounded `askInt`
- `confirm` with a configurable default
- numbered `choose`
- `success`, `info`, `warning` and `error`
- headings, panels and tables
- explicit `pause`, navigation and exit controls

Input and output go through `TerminalIO`. Supply your own implementation to embed TerminalUI or control terminal sizing. `ScriptedTerminal` makes complete journeys deterministic in tests.

```java
ScriptedTerminal terminal = new ScriptedTerminal("1", "Emil", "", "q");
TerminalUi.builder(home)
    .io(terminal)
    .ansi(AnsiMode.DISABLED)
    .clearScreen(false)
    .run();

assert terminal.output().contains("Hello, Emil");
```

## Styling

Built-in themes are `Themes.modern()`, `ocean()`, `minimal()` and `monochrome()`. A complete custom theme can be constructed with `Theme.builder()`:

```java
import dev.terminalui.style.Color;
import dev.terminalui.style.TextStyle;
import dev.terminalui.style.Theme;

Theme custom = Theme.builder()
    .accent(TextStyle.of(Color.BRIGHT_MAGENTA).bolded())
    .contentWidth(64)
    .pointer("→")
    .build();
```

ANSI output defaults to `AUTO`: it is used for a real interactive console and omitted for redirected/test output. Override with `AnsiMode.ENABLED` or `DISABLED`.

## Build

```shell
mvn test       # test the library
mvn package    # create target/terminalui-1.0.0-SNAPSHOT.jar
mvn install    # make it importable by local Maven projects
```

The library has no runtime dependencies.
