package dev.terminalui.style;

import java.util.Objects;

/** Complete visual vocabulary for a TerminalUI application. */
public record Theme(TextStyle accent, TextStyle title, TextStyle muted, TextStyle success, TextStyle warning,
                    TextStyle error, String horizontal, String vertical, String topLeft, String topRight,
                    String bottomLeft, String bottomRight, String pointer, String prompt, int contentWidth) {
    public Theme {
        Objects.requireNonNull(accent); Objects.requireNonNull(title); Objects.requireNonNull(muted);
        Objects.requireNonNull(success); Objects.requireNonNull(warning); Objects.requireNonNull(error);
        if (contentWidth < 30) throw new IllegalArgumentException("contentWidth must be at least 30");
    }
    public static Builder builder() { return new Builder(); }
    public static final class Builder {
        private TextStyle accent = TextStyle.of(Color.BRIGHT_CYAN), title = TextStyle.of(Color.BRIGHT_WHITE).bolded();
        private TextStyle muted = TextStyle.of(Color.BRIGHT_BLACK), success = TextStyle.of(Color.BRIGHT_GREEN);
        private TextStyle warning = TextStyle.of(Color.BRIGHT_YELLOW), error = TextStyle.of(Color.BRIGHT_RED);
        private String horizontal="─", vertical="│", topLeft="╭", topRight="╮", bottomLeft="╰", bottomRight="╯", pointer="›", prompt="❯";
        private int contentWidth = 72;
        public Builder accent(TextStyle v){accent=v;return this;} public Builder title(TextStyle v){title=v;return this;}
        public Builder muted(TextStyle v){muted=v;return this;} public Builder success(TextStyle v){success=v;return this;}
        public Builder warning(TextStyle v){warning=v;return this;} public Builder error(TextStyle v){error=v;return this;}
        public Builder border(String h,String v,String tl,String tr,String bl,String br){horizontal=h;vertical=v;topLeft=tl;topRight=tr;bottomLeft=bl;bottomRight=br;return this;}
        public Builder pointer(String v){pointer=v;return this;} public Builder prompt(String v){prompt=v;return this;}
        public Builder contentWidth(int v){contentWidth=v;return this;}
        public Theme build(){return new Theme(accent,title,muted,success,warning,error,horizontal,vertical,topLeft,topRight,bottomLeft,bottomRight,pointer,prompt,contentWidth);}
    }
}
