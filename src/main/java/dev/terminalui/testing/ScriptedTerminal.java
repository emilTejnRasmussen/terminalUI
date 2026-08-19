package dev.terminalui.testing;

import dev.terminalui.io.TerminalIO;
import java.util.*;

/** In-memory terminal for application tests and scripted demos. */
public final class ScriptedTerminal implements TerminalIO {
    private final Deque<String> input; private final StringBuilder output=new StringBuilder(); private final int width;
    public ScriptedTerminal(String... input){this(80,input);} public ScriptedTerminal(int width,String... input){this.width=width;this.input=new ArrayDeque<>(List.of(input));}
    @Override public String readLine(){return input.pollFirst();} @Override public void print(String value){output.append(value);}
    @Override public boolean interactive(){return false;} @Override public int width(){return width;} public String output(){return output.toString();}
}
