package component;

public class Header
{
    private final String title;
    private final String subtitle;
    private final int DIVIDER_LENGTH = 50;

    public Header(String title)
    {
        this.title = title;
        this.subtitle = null;
    }

    public Header(String title, String subtitle)
    {
        this.title = title;
        this.subtitle = subtitle;
    }

    public void display() {
        System.out.println("=".repeat(DIVIDER_LENGTH));

        printCentered(title);
        if (subtitle != null){
            printCentered(subtitle);
        }
        System.out.println("=". repeat(DIVIDER_LENGTH));
    }

    private void printCentered(String toPrint) {
        System.out.println(" ".repeat(Math.max(0, (DIVIDER_LENGTH/2) - (toPrint.length() / 2))) + toPrint);
    }
}
