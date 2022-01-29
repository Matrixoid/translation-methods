package results.Arithmetic;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

public class ArithmeticLexer{
    private String input;
    private int curPos;
    private ArithmeticToken curToken;
    private Map<ArithmeticToken, Pattern> regex;
    private Pattern skipPattern;
    private Matcher matcher;

    public ArithmeticLexer(String input) {
        this.input = input;
        skipPattern = Pattern.compile("[ \n\r\t]+");
        initRegex();
        matcher = Pattern.compile("").matcher(input);
    }

    private void initRegex() {
        regex = new HashMap<>();
        regex.put(ArithmeticToken.ADD, Pattern.compile("[+]"));
        regex.put(ArithmeticToken.SUB, Pattern.compile("[-]"));
        regex.put(ArithmeticToken.MUL, Pattern.compile("[*]"));
        regex.put(ArithmeticToken.DIV, Pattern.compile("[/]"));
        regex.put(ArithmeticToken.REVERSEDIV, Pattern.compile("\\\\"));
        regex.put(ArithmeticToken.OP, Pattern.compile("[(]"));
        regex.put(ArithmeticToken.CP, Pattern.compile("[)]"));
        regex.put(ArithmeticToken.NUM, Pattern.compile("([1-9][0-9]*)|(0)"));
        regex.put(ArithmeticToken.END, Pattern.compile("\\$"));
        regex.put(ArithmeticToken.END, Pattern.compile("$"));
    }

    private void skip() {
        String text = input.substring(curPos);
        matcher.usePattern(skipPattern);
        matcher.region(curPos, input.length());
        if (matcher.lookingAt()) {
            curPos += matcher.end() - matcher.start();
        }
    }

    private boolean findNextToken() {
        for (ArithmeticToken t : ArithmeticToken.values()) {
            matcher.usePattern(regex.get(t));
            matcher.region(curPos, input.length());
            if (matcher.lookingAt()) {
                curToken = t;
                curPos += matcher.end() - matcher.start();
                return true;
            }
        }
        return false;
    }

    public void nextToken() throws ParseException {
        skip();
        if (curPos == input.length()) {
            curToken = ArithmeticToken.END;
            return;
        }
        if (!findNextToken()) {
            throw new ParseException("Illegal sequence of characters at : curPos", curPos);
        }
    }
    public ArithmeticToken getCurToken() {
        return curToken;
    }

    public int getCurPos() {
        return curPos;
    }

    public String getCurTokenString() {
        return matcher.group();
    }

}
