package menu;

import app.TerminalApp;

public class SubMenuItem implements MenuItem
{
    private final String key;
    private final String label;
    private final Menu menu;

    public SubMenuItem(String key, String label, Menu menu)
    {
        this.key = key;
        this.label = label;
        this.menu = menu;
    }

    @Override
    public String getKey()
    {
        return key;
    }

    @Override
    public String getLabel()
    {
        return label;
    }

    @Override
    public void execute(TerminalApp app)
    {
        app.openMenu(menu);
    }
}