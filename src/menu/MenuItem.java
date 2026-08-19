package menu;


import app.TerminalApp;

public interface MenuItem
{
    String getKey();

    String getLabel();

    void execute(TerminalApp app);
}