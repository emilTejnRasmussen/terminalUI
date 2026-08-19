package dev.terminalui.menu;

import java.util.Objects;
import java.util.function.BooleanSupplier;

public final class MenuItem {
    public enum Kind { ACTION, SUBMENU, BACK, EXIT }
    private final String key, label, hint; private final Kind kind; private final MenuAction action; private final Menu submenu; private final BooleanSupplier enabled;
    MenuItem(String key,String label,String hint,Kind kind,MenuAction action,Menu submenu,BooleanSupplier enabled) {
        this.key=Objects.requireNonNull(key); this.label=Objects.requireNonNull(label); this.hint=hint; this.kind=kind; this.action=action; this.submenu=submenu; this.enabled=enabled;
    }
    public String key(){return key;} public String label(){return label;} public String hint(){return hint;} public boolean enabled(){return enabled.getAsBoolean();}
    public Kind kind(){return kind;} public MenuAction action(){return action;} public Menu submenu(){return submenu;}
}
