package grammar;

public class Term implements RuleToken {
    private String name;

    public Term(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
