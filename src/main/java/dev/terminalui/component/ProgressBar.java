package dev.terminalui.component;

/** Stateless progress bar for logs and periodically refreshed screens. */
public final class ProgressBar {
    private ProgressBar(){}
    public static String render(int current,int total,int width){
        if(total<=0)throw new IllegalArgumentException("total must be positive");if(width<5)throw new IllegalArgumentException("width must be at least 5");
        double ratio=Math.max(0,Math.min(1,(double)current/total));int filled=(int)Math.round(ratio*width);
        return "█".repeat(filled)+"░".repeat(width-filled)+" "+Math.round(ratio*100)+"%";
    }
}
