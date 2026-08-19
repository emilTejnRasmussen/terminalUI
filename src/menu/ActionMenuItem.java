package menu;

import app.TerminalApp;

public class ActionMenuItem implements MenuItem
{
    private final String key;
    private final String label;
    private final Runnable action;

    public ActionMenuItem(String key, String label, Runnable action)
    {
        this.key = key;
        this.label = label;
        this.action = action;
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
        action.run();
    }
}