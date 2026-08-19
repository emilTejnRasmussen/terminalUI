package app;

import menu.Menu;
import menu.MenuItem;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class TerminalApp
{
    private final Deque<Menu> menuStack;
    private final Scanner scanner;

    private boolean running;

    public TerminalApp(Menu rootMenu)
    {
        this.menuStack = new ArrayDeque<>();
        this.scanner = new Scanner(System.in);

        this.menuStack.push(rootMenu);
    }

    public void run()
    {
        running = true;

        while (running)
        {
            Menu currentMenu = getCurrentMenu();

            currentMenu.show();

            System.out.print("> ");

            String input = scanner.nextLine().trim();

            MenuItem item = currentMenu.findItem(input);

            if (item == null)
            {
                System.out.println("Invalid option.");
                continue;
            }

            item.execute(this);
        }
    }

    public void openMenu(Menu menu)
    {
        menuStack.push(menu);
    }

    public void back()
    {
        if (menuStack.size() > 1)
        {
            menuStack.pop();
        }
    }

    public void exit()
    {
        running = false;
    }

    public Menu getCurrentMenu()
    {
        return menuStack.peek();
    }
}