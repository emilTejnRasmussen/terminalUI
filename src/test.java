import app.TerminalApp;
import component.Header;
import menu.Menu;

void main()
{
    Menu settings = Menu.builder("Settings")
            .subtitle("Application settings")
            .item(
                    "1",
                    "Change username",
                    () -> System.out.println("Changing username...")
            )
            .item(
                    "2",
                    "Change theme",
                    () -> System.out.println("Changing theme...")
            )
            .back("B", "Go back")
            .build();

    Menu main = Menu.builder("GitHub User Activity")
            .subtitle("Choose an option")
            .item(
                    "1",
                    "Hello World",
                    () -> System.out.println("Hello World!")
            )
            .item(
                    "2",
                    "Search GitHub user",
                    () -> System.out.println("Searching...")
            )
            .submenu(
                    "3",
                    "Settings",
                    settings
            )
            .exit("Q", "Exit")
            .build();

    TerminalApp app = new TerminalApp(main);

    app.run();
}

private void listUsers()
{
    System.out.println("Users: 1, 2, 3");
}

private void hello()
{
    System.out.println("Hello World!");
}