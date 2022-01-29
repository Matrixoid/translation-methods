package generator;

import grammar.*;

import java.util.List;
import java.util.Set;

public class ParserGenerator extends ClassPrinter {
    private final String exceptionString = "throw new ParseException(\"Unexpected token : \" + lexer.getCurToken(), lexer.getCurPos());";

    public ParserGenerator(String name, Grammar grammar, String path) {
        super(name, grammar, path, name + "Parser.java");
    }

    @Override
    protected void printImports() {
        printImport("java.text.ParseException");
        printLine(0);
    }

    @Override
    protected void printClass() {
        printLine(0, "public class ", parserClassName, " {");
        printFields();
        printMethods();
        printNonTermClasses();
        printLine(0, "}");
    }

    private String toUpperFirstLetter(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    private void printFields() {
        printLine(1, lexerClassName, " lexer;");
        printLine(0);
    }

    private void printMethods() {
        printLine(1, "public ", toUpperFirstLetter(grammar.getStartNonTerm()), " parse(String input) throws ParseException {");
        printLine(2, "lexer = new ", lexerClassName, "(input);");
        printLine(2, "lexer.nextToken();");

        String startNonTerm = grammar.getStartNonTerm();
        String startNonTermClass = toUpperFirstLetter(startNonTerm);
        printLine(2, startNonTermClass, " result = ", startNonTerm, "();");
        printLine(2, "if(lexer.getCurToken() != ", tokenClassName, ".END) {");
        printLine(3, exceptionString);
        printLine(2, "}");
        printLine(2, "return result;");
        printLine(1, "}");
        printLine(0);

        for (NonTermRule nonTermRule : grammar.getNonTermRules()) {
            printRuleMethod(nonTermRule);
        }

        printLine(1, "private String check(", tokenClassName, " token) throws ParseException {");
        printLine(2, "if (token != lexer.getCurToken()) {");
        printLine(3, exceptionString);
        printLine(2, "}");
        printLine(2, "String res = \"\";");
        printLine(2, "if (lexer.getCurToken() != ", tokenClassName, ".END) {");
        printLine(3, "res = lexer.getCurTokenString();");
        printLine(3, "lexer.nextToken();");
        printLine(2, "}");
        printLine(2, "return res;");
        printLine(1, "}");
        printLine(0);
    }

    private void printRuleMethod(NonTermRule rule) {
        String returnType = toUpperFirstLetter(rule.getName());
        printLine(1, "private ", returnType, " ", rule.getName(), "(", argumentsToString(rule), ") throws ParseException {");
        printLine(2, returnType, " result = new ", returnType, "();");
        printLine(2, "switch(lexer.getCurToken()) {");
        printCases(rule);
        printLine(2, "}");
        printLine(2, "return result;");
        printLine(1, "}");
        printLine(0);
    }

    private String argumentsToString(NonTermRule rule) {
        StringBuilder builder = new StringBuilder();
        boolean needComma = false;
        for (Argument arg : rule.getArguments()) {
            if (needComma) {
                builder.append(", ");
            }
            needComma = true;
            builder.append(arg.getType()).append(" ").append(arg.getName());
        }
        return builder.toString();
    }

    private void printCases(NonTermRule rule) {
        List<RuleToken> rightPartWithEps = null;

        for (List<RuleToken> rightPart : rule.getRightParts()) {
            Set<String> firstSet = grammar.getFirstSetByRightPart(rightPart);
            if (firstSet.contains("")) {
                rightPartWithEps = rightPart;
                continue;
            }
            for (String firstToken : firstSet) {
                printLine(3, "case ", firstToken, ":");
            }
            printCase(rule.getName(), rightPart);
        }

        if (rightPartWithEps != null) {
            for (String followToken : grammar.getFollowSet(rule.getName())) {
                printLine(3, "case ", followToken, ":");
            }
            printCase(rule.getName(), rightPartWithEps);
        }
        printLine(3, "default:");
        printLine(4, exceptionString);
    }

    private void printCase(String NonTermName, List<RuleToken> rightPart) {
        printLine(3, "{");

        for (RuleToken ruleToken : rightPart) {
            if (ruleToken instanceof Code) {
                printLine(4, normalizeCode(ruleToken.getName()));
            } else if (ruleToken instanceof Term) {
                printLine(4, "String ", ruleToken.getName(), " = check(", tokenClassName, ".", ruleToken.getName(), ");");
            } else {
                printLine(4, toUpperFirstLetter(ruleToken.getName()), " ", ruleToken.getName(), " = ",
                        ruleToken.getName(), "(", parametersToString((NonTerm) ruleToken), ");");
            }
        }

        printLine(4, "break;");
        printLine(3, "}");
    }

    private String normalizeCode(String code) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '$') {
                builder.append("result.");
            } else {
                builder.append(code.charAt(i));
            }
        }
        return builder.toString();
    }

    private String parametersToString(NonTerm nonTerm) {
        StringBuilder builder = new StringBuilder();
        boolean needComma = false;
        for (String parameter : nonTerm.getParameters()) {
            if (needComma) {
                builder.append(", ");
            }
            needComma = true;
            builder.append(parameter);
        }
        return builder.toString();
    }

    private void printNonTermClasses() {
        for (NonTermRule nonTermRule : grammar.getNonTermRules()) {
            printLine(1, "public class ", toUpperFirstLetter(nonTermRule.getName()), " {");
            for (Argument arg : nonTermRule.getReturns()) {
                printLine(2, "public ", arg.getType(), " ", arg.getName(), ";");
            }
            printLine(1, "}");
            printLine(0);
        }
    }
}
