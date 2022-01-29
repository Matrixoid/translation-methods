package results.PythonLogic;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

public class PythonLogicLexer{
    private String input;
    private int curPos;
    private PythonLogicToken curToken;
    private Map<PythonLogicToken, Pattern> regex;
    private Pattern skipPattern;
    private Matcher matcher;

    public PythonLogicLexer(String input) {
        this.input = input;
        skipPattern = Pattern.compile("[ \n\r\t]+");
        initRegex();
        matcher = Pattern.compile("").matcher(input);
    }

    private void initRegex() {
        regex = new HashMap<>();
        regex.put(PythonLogicToken.OR, Pattern.compile("[o][r]"));
        regex.put(PythonLogicToken.XOR, Pattern.compile("[x][o][r]"));
        regex.put(PythonLogicToken.AND, Pattern.compile("[a][n][d]"));
        regex.put(PythonLogicToken.NOT, Pattern.compile("[n][o][t]"));
        regex.put(PythonLogicToken.LPAREN, Pattern.compile("[(]"));
        regex.put(PythonLogicToken.RPAREN, Pattern.compile("[)]"));
        regex.put(PythonLogicToken.VARIABLE, Pattern.compile("[a-z]"));
        regex.put(PythonLogicToken.END, Pattern.compile("\\$"));
        regex.put(PythonLogicToken.END, Pattern.compile("$"));
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
        for (PythonLogicToken t : PythonLogicToken.values()) {
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
            curToken = PythonLogicToken.END;
            return;
        }
        if (!findNextToken()) {
            throw new ParseException("Illegal sequence of characters at : curPos", curPos);
        }
    }
    public PythonLogicToken getCurToken() {
        return curToken;
    }

    public int getCurPos() {
        return curPos;
    }

    public String getCurTokenString() {
        return matcher.group();
    }

}
