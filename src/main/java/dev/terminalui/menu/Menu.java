package dev.terminalui.menu;

import java.util.*;
import java.util.function.BooleanSupplier;

/** Immutable menu/screen definition. */
public final class Menu {
    private final String title, subtitle; private final List<MenuItem> items;
    private Menu(Builder b){title=b.title;subtitle=b.subtitle;items=List.copyOf(b.items);}
    public static Builder builder(String title){return new Builder(title);}
    public String title(){return title;} public String subtitle(){return subtitle;} public List<MenuItem> items(){return items;}
    public Optional<MenuItem> find(String key){return items.stream().filter(i->i.key().equalsIgnoreCase(key.trim())).findFirst();}
    public static final class Builder {
        private final String title; private String subtitle; private final List<MenuItem> items=new ArrayList<>();
        private Builder(String title){if(title==null||title.isBlank())throw new IllegalArgumentException("Menu title cannot be blank");this.title=title;}
        public Builder subtitle(String value){subtitle=value;return this;}
        public Builder action(String key,String label,MenuAction action){return action(key,label,null,()->true,action);}
        public Builder action(String key,String label,String hint,MenuAction action){return action(key,label,hint,()->true,action);}
        public Builder action(String key,String label,String hint,BooleanSupplier enabled,MenuAction action){add(new MenuItem(key,label,hint,MenuItem.Kind.ACTION,Objects.requireNonNull(action),null,enabled));return this;}
        public Builder submenu(String key,String label,Menu menu){add(new MenuItem(key,label,null,MenuItem.Kind.SUBMENU,null,Objects.requireNonNull(menu),()->true));return this;}
        public Builder back(String key,String label){add(new MenuItem(key,label,null,MenuItem.Kind.BACK,null,null,()->true));return this;}
        public Builder exit(String key,String label){add(new MenuItem(key,label,null,MenuItem.Kind.EXIT,null,null,()->true));return this;}
        private void add(MenuItem item){if(item.key().isBlank())throw new IllegalArgumentException("Menu key cannot be blank");if(items.stream().anyMatch(i->i.key().equalsIgnoreCase(item.key())))throw new IllegalArgumentException("Duplicate menu key: "+item.key());items.add(item);}
        public Menu build(){if(items.isEmpty())throw new IllegalStateException("A menu needs at least one item");return new Menu(this);}
    }
}
