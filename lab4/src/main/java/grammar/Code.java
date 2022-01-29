package grammar;

public class Code implements RuleToken {
    private String code;

    public Code(String code) {
        this.code = code.substring(1, code.length() - 1);
    }


    @Override
    public String getName() {
        return code;
    }
}
