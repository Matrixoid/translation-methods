package results.PythonLogic;

import java.text.ParseException;

public class PythonLogicParser {
    PythonLogicLexer lexer;

    public Expression parse(String input) throws ParseException {
        lexer = new PythonLogicLexer(input);
        lexer.nextToken();
        Expression result = expression();
        if(lexer.getCurToken() != PythonLogicToken.END) {
            throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private Expression expression() throws ParseException {
        Expression result = new Expression();
        switch(lexer.getCurToken()) {
            case VARIABLE:
            case LPAREN:
            {
                OrXor orXor = orXor();
                result.v = orXor.v;
                String END = check(PythonLogicToken.END);
                break;
            }
            default:
                throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private OrXor orXor() throws ParseException {
        OrXor result = new OrXor();
        switch(lexer.getCurToken()) {
            case VARIABLE:
            case LPAREN:
            {
                And and = and();
                OrXor_ orXor_ = orXor_(and.v);
                result.v = orXor_.v;
                break;
            }
            default:
                throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private OrXor_ orXor_(String left) throws ParseException {
        OrXor_ result = new OrXor_();
        switch(lexer.getCurToken()) {
            case OR:
            {
                String OR = check(PythonLogicToken.OR);
                And and = and();
                String next = left + " or " + and.v;
                OrXor_ orXor_ = orXor_(next);
                result.v = orXor_.v;
                break;
            }
            case XOR:
            {
                String XOR = check(PythonLogicToken.XOR);
                And and = and();
                String next = left + " xor " + and.v;
                OrXor_ orXor_ = orXor_(next);
                result.v = orXor_.v;
                break;
            }
            case END:
            case RPAREN:
            {
                result.v = left;
                break;
            }
            default:
                throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private And and() throws ParseException {
        And result = new And();
        switch(lexer.getCurToken()) {
            case VARIABLE:
            case LPAREN:
            {
                Not not = not();
                And_ and_ = and_(not.v);
                result.v = and_.v;
                break;
            }
            default:
                throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private And_ and_(String left) throws ParseException {
        And_ result = new And_();
        switch(lexer.getCurToken()) {
            case AND:
            {
                String AND = check(PythonLogicToken.AND);
                Not not = not();
                And_ and_ = and_(left + " and " + not.v);
                result.v = and_.v;
                break;
            }
            case OR:
            case XOR:
            case END:
            case RPAREN:
            {
                result.v = left;
                break;
            }
            default:
                throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private Not not() throws ParseException {
        Not result = new Not();
        switch(lexer.getCurToken()) {
            case VARIABLE:
            case LPAREN:
            {
                Unary unary = unary();
                Not_ not_ = not_(unary.v);
                result.v = not_.v;
                break;
            }
            default:
                throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private Not_ not_(String left) throws ParseException {
        Not_ result = new Not_();
        switch(lexer.getCurToken()) {
            case NOT:
            {
                String NOT = check(PythonLogicToken.NOT);
                Not not = not();
                result.v = "not " + left;
                break;
            }
            case OR:
            case AND:
            case XOR:
            case END:
            case RPAREN:
            {
                result.v = left;
                break;
            }
            default:
                throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private Unary unary() throws ParseException {
        Unary result = new Unary();
        switch(lexer.getCurToken()) {
            case LPAREN:
            {
                String LPAREN = check(PythonLogicToken.LPAREN);
                OrXor orXor = orXor();
                String RPAREN = check(PythonLogicToken.RPAREN);
                result.v = "(" + orXor.v + ")";
                break;
            }
            case VARIABLE:
            {
                String VARIABLE = check(PythonLogicToken.VARIABLE);
                result.v = VARIABLE;
                break;
            }
            default:
                throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private String check(PythonLogicToken token) throws ParseException {
        if (token != lexer.getCurToken()) {
            throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        String res = "";
        if (lexer.getCurToken() != PythonLogicToken.END) {
            res = lexer.getCurTokenString();
            lexer.nextToken();
        }
        return res;
    }

    public class Expression {
        public String v;
    }

    public class OrXor {
        public String v;
    }

    public class OrXor_ {
        public String v;
    }

    public class And {
        public String v;
    }

    public class And_ {
        public String v;
    }

    public class Not {
        public String v;
    }

    public class Not_ {
        public String v;
    }

    public class Unary {
        public String v;
    }

}
