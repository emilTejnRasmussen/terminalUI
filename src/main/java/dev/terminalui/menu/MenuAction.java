package dev.terminalui.menu;

import dev.terminalui.UiContext;

@FunctionalInterface public interface MenuAction { void execute(UiContext ui) throws Exception; }
