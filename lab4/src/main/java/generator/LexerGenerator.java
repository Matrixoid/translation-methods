package generator;

import grammar.Grammar;
import grammar.TermRule;

public class LexerGenerator extends ClassPrinter {
    public LexerGenerator(String name, Grammar grammar, String path) {
        super(name, grammar, path, name + "Lexer.java");
    }

    @Override
    protected void printImports() {
        printImport("java.text.ParseException");
        printImport("java.util.regex.Matcher");
        printImport("java.util.regex.Pattern");
        printImport("java.util.Map");
        printImport("java.util.HashMap");
        printLine(0);
    }

    @Override
    protected void printClass() {
        printLine(0, "public class ", lexerClassName, "{");
        printFields();
        printConstructor();
        printMethods();
        printLine(0, "}");
    }

    private void printFields() {
        printLine(1, "private String input;");
        printLine(1, "private int curPos;");
        printLine(1, "private ", tokenClassName, " curToken;");
        printLine(1, "private Map<", tokenClassName, ", Pattern> regex;");
        printLine(1, "private Pattern skipPattern;");
        printLine(1, "private Matcher matcher;");
        printLine(0);
    }

    private void printConstructor() {
        printLine(1, "public ", lexerClassName, "(String input) {");
        printLine(2, "this.input = input;");
        printLine(2, "skipPattern = Pattern.compile(\"[ \\n\\r\\t]+\");");
        printLine(2, "initRegex();");
        printLine(2, "matcher = Pattern.compile(\"\").matcher(input);");
        printLine(1, "}");
        printLine(0);
    }

    private void printMethods() {
        printInitRegex();
        printSkip();
        printFindNextToken();
        printNextToken();
        printGetCurToken();
        printGetCurPos();
        printGetCurTokenString();
    }

    private void printInitRegex() {
        printLine(1, "private void initRegex() {");
        printLine(2, "regex = new HashMap<>();");
        for (TermRule termRule : grammar.getTermRules()) {
            StringBuilder builder = new StringBuilder();
            builder.append("regex.put(")
                    .append(tokenClassName).append('.').append(termRule.getName())
                    .append(", Pattern.compile(");

            if (termRule.isRegex())
                builder.append(termRule.getValue());
            else {
                String value = termRule.getValue();
                builder.append('\"');
                for (int i = 1; i < value.length() - 1; i++)
                    if (value.charAt(i) == '^' || value.charAt(i) == '$')
                        builder.append("\\\\").append(value.charAt(i));
                    else if (value.charAt(i) == '\\')
                        builder.append("\\\\\\\\");
                    else
                        builder.append('[').append(value.charAt(i)).append(']');
                builder.append('\"');
            }
            builder.append("));");
            printLine(2, builder.toString());
        }
        printLine(2, "regex.put(", tokenClassName, ".END, Pattern.compile(\"$\"));");
        printLine(1, "}");
        printLine(0);
    }

    private void printSkip() {
        printLine(1, "private void skip() {");
        printLine(2, "String text = input.substring(curPos);");
        printLine(2, "matcher.usePattern(skipPattern);");
        printLine(2, "matcher.region(curPos, input.length());");
        printLine(2, "if (matcher.lookingAt()) {");
        printLine(3, "curPos += matcher.end() - matcher.start();");
        printLine(2, "}");
        printLine(1, "}");
        printLine(0);
    }

    private void printFindNextToken() {
        printLine(1, "private boolean findNextToken() {");
        printLine(2, "for (", tokenClassName, " t : ", tokenClassName, ".values()) {");
        printLine(3, "matcher.usePattern(regex.get(t));");
        printLine(3, "matcher.region(curPos, input.length());");
        printLine(3, "if (matcher.lookingAt()) {");
        printLine(4, "curToken = t;");
        printLine(4, "curPos += matcher.end() - matcher.start();");
        printLine(4, "return true;");
        printLine(3, "}");
        printLine(2, "}");
        printLine(2, "return false;");
        printLine(1, "}");
        printLine(0);
    }

    private void printNextToken() {
        printLine(1, "public void nextToken() throws ParseException {");
        printLine(2, "skip();");
        printLine(2, "if (curPos == input.length()) {");
        printLine(3, "curToken = ", tokenClassName, ".END;");
        printLine(3, "return;");
        printLine(2, "}");
        printLine(2, "if (!findNextToken()) {");
        printLine(3, "throw new ParseException(\"Illegal sequence of characters at : curPos\", curPos);");
        printLine(2, "}");
        printLine(1, "}");
    }

    private void printGetCurToken() {
        printLine(1, "public ", tokenClassName, " getCurToken() {");
        printLine(2, "return curToken;");
        printLine(1, "}");
        printLine(0);
    }

    private void printGetCurPos() {
        printLine(1, "public int getCurPos() {");
        printLine(2, "return curPos;");
        printLine(1, "}");
        printLine(0);
    }

    private void printGetCurTokenString() {
        printLine(1, "public String getCurTokenString() {");
        printLine(2, "return matcher.group();");
        printLine(1, "}");
        printLine(0);
    }
}
