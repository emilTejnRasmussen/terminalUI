package dev.terminalui.component;

import dev.terminalui.UiContext;
import java.util.*;

/** A simple Unicode table with aligned columns. */
public final class Table {
    private final List<String> headers; private final List<List<String>> rows;
    private Table(Builder b){headers=List.copyOf(b.headers);rows=b.rows.stream().map(List::copyOf).toList();}
    public static Builder builder(String... headers){return new Builder(List.of(headers));}
    public void render(UiContext ui){
        int[] widths=new int[headers.size()];for(int i=0;i<widths.length;i++)widths[i]=headers.get(i).length();
        for(List<String> row:rows)for(int i=0;i<widths.length;i++)widths[i]=Math.max(widths[i],row.get(i).length());
        String separator=separator(widths);ui.print(separator);ui.print(row(headers,widths));ui.print(separator);for(List<String> value:rows)ui.print(row(value,widths));ui.print(separator);
    }
    private String separator(int[] widths){StringJoiner j=new StringJoiner("┼","├","┤");for(int w:widths)j.add("─".repeat(w+2));return j.toString();}
    private String row(List<String> values,int[] widths){StringJoiner j=new StringJoiner(" │ ","│ "," │");for(int i=0;i<widths.length;i++)j.add(values.get(i)+" ".repeat(widths[i]-values.get(i).length()));return j.toString();}
    public static final class Builder {
        private final List<String> headers; private final List<List<String>> rows=new ArrayList<>();
        private Builder(List<String> headers){if(headers.isEmpty())throw new IllegalArgumentException("headers cannot be empty");this.headers=List.copyOf(headers);}
        public Builder row(Object... cells){if(cells.length!=headers.size())throw new IllegalArgumentException("Expected "+headers.size()+" cells");rows.add(Arrays.stream(cells).map(String::valueOf).toList());return this;}
        public Table build(){return new Table(this);}
    }
}
