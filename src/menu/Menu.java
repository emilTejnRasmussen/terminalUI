package menu;

import component.Header;

import java.util.ArrayList;
import java.util.List;

public class Menu
{
    private final Header header;
    private final List<MenuItem> items;

    private Menu(Builder builder)
    {
        if (builder.subtitle == null)
        {
            this.header = new Header(builder.title);
        }
        else
        {
            this.header = new Header(builder.title, builder.subtitle);
        }

        this.items = new ArrayList<>(builder.items);
    }

    public static Builder builder(String title)
    {
        return new Builder(title);
    }

    public void show()
    {
        header.display();

        for (MenuItem item : items)
        {
            System.out.printf(
                    "  %s - %s%n",
                    item.getKey(),
                    item.getLabel()
            );
        }

        System.out.println();
    }

    public MenuItem findItem(String key)
    {
        return items.stream()
                .filter(item -> item.getKey().equalsIgnoreCase(key))
                .findFirst()
                .orElse(null);
    }

    public static class Builder
    {
        private final String title;
        private String subtitle;

        private final List<MenuItem> items = new ArrayList<>();

        private Builder(String title)
        {
            this.title = title;
        }

        public Builder subtitle(String subtitle)
        {
            this.subtitle = subtitle;
            return this;
        }

        public Builder item(String key, String label, Runnable action)
        {
            items.add(
                    new ActionMenuItem(key, label, action)
            );

            return this;
        }

        public Builder submenu(String key, String label, Menu menu)
        {
            items.add(
                    new SubMenuItem(key, label, menu)
            );

            return this;
        }

        public Builder back(String key, String label)
        {
            items.add(
                    new BackMenuItem(key, label)
            );

            return this;
        }

        public Builder exit(String key, String label)
        {
            items.add(
                    new ExitMenuItem(key, label)
            );

            return this;
        }

        public Menu build()
        {
            return new Menu(this);
        }
    }
}