package generator;

import grammar.Grammar;
import grammar.TermRule;

public class TokenGenerator extends ClassPrinter {
    public TokenGenerator(String name, Grammar grammar, String path) {
        super(name, grammar, path, name + "Token.java");
    }

    @Override
    protected void printImports() {
    }

    @Override
    protected void printClass() {
        printLine(0, "public enum ", tokenClassName, "{");
        printTokens();
        printLine(0, "}");
    }

    private void printTokens() {
        for (TermRule termRule : grammar.getTermRules()) {
            printLine(1, termRule.getName(), ",");
        }
    }
}
