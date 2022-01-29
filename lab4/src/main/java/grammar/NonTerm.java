package grammar;

import java.util.ArrayList;
import java.util.List;

public class NonTerm implements RuleToken {
    private String name;
    private List<String> parameters = new ArrayList<>();

    public NonTerm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addParameter(String parameter) {
        parameters.add(parameter);
    }

    public List<String> getParameters() {
        return parameters;
    }
}
