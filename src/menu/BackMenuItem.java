package menu;

import app.TerminalApp;

public class BackMenuItem implements MenuItem
{
    private final String key;
    private final String label;

    public BackMenuItem(String key, String label)
    {
        this.key = key;
        this.label = label;
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
        app.back();
    }
}