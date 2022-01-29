package grammar;

public class TermRule {
    private String name;
    private String value;
    private boolean isRegex;

    public TermRule(boolean isRegex, String name, String data) {
        this.name = name;
        this.value = data;
        this.isRegex = isRegex;
    }

    public String getName() {
        return name;
    }

    public boolean isRegex() {
        return isRegex;
    }

    public String getValue() {
        return value;
    }
}
