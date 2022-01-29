package results.Arithmetic;

import java.text.ParseException;

public class ArithmeticParser {
    ArithmeticLexer lexer;

    public Run parse(String input) throws ParseException {
        lexer = new ArithmeticLexer(input);
        lexer.nextToken();
        Run result = run();
        if(lexer.getCurToken() != ArithmeticToken.END) {
            throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private Run run() throws ParseException {
        Run result = new Run();
        switch(lexer.getCurToken()) {
            case SUB:
            case OP:
            case NUM:
            {
                AddSub addSub = addSub();
                result.v = addSub.v;
                String END = check(ArithmeticToken.END);
                break;
            }
            default:
                throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private AddSub addSub() throws ParseException {
        AddSub result = new AddSub();
        switch(lexer.getCurToken()) {
            case SUB:
            case OP:
            case NUM:
            {
                MulDiv mulDiv = mulDiv();
                AddSub_ addSub_ = addSub_(mulDiv.v);
                result.v = addSub_.v;
                break;
            }
            default:
                throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private AddSub_ addSub_(int left) throws ParseException {
        AddSub_ result = new AddSub_();
        switch(lexer.getCurToken()) {
            case ADD:
            {
                String ADD = check(ArithmeticToken.ADD);
                MulDiv mulDiv = mulDiv();
                int next = left + mulDiv.v;
                AddSub_ addSub_ = addSub_(next);
                result.v = addSub_.v;
                break;
            }
            case SUB:
            {
                String SUB = check(ArithmeticToken.SUB);
                MulDiv mulDiv = mulDiv();
                int next = left - mulDiv.v;
                AddSub_ addSub_ = addSub_(next);
                result.v = addSub_.v;
                break;
            }
            case END:
            case CP:
            {
                result.v = left;
                break;
            }
            default:
                throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private MulDiv mulDiv() throws ParseException {
        MulDiv result = new MulDiv();
        switch(lexer.getCurToken()) {
            case SUB:
            case OP:
            case NUM:
            {
                RevDiv revDiv = revDiv();
                MulDiv_ mulDiv_ = mulDiv_(revDiv.v);
                result.v = mulDiv_.v;
                break;
            }
            default:
                throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private MulDiv_ mulDiv_(int left) throws ParseException {
        MulDiv_ result = new MulDiv_();
        switch(lexer.getCurToken()) {
            case MUL:
            {
                String MUL = check(ArithmeticToken.MUL);
                RevDiv revDiv = revDiv();
                MulDiv_ mulDiv_ = mulDiv_(left * revDiv.v);
                result.v = mulDiv_.v;
                break;
            }
            case DIV:
            {
                String DIV = check(ArithmeticToken.DIV);
                RevDiv revDiv = revDiv();
                MulDiv_ mulDiv_ = mulDiv_(left / revDiv.v);
                result.v = mulDiv_.v;
                break;
            }
            case ADD:
            case SUB:
            case END:
            case CP:
            {
                result.v = left;
                break;
            }
            default:
                throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private RevDiv revDiv() throws ParseException {
        RevDiv result = new RevDiv();
        switch(lexer.getCurToken()) {
            case SUB:
            case OP:
            case NUM:
            {
                Unary unary = unary();
                RevDiv_ revDiv_ = revDiv_(unary.v);
                result.v = revDiv_.v;
                break;
            }
            default:
                throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private RevDiv_ revDiv_(int left) throws ParseException {
        RevDiv_ result = new RevDiv_();
        switch(lexer.getCurToken()) {
            case REVERSEDIV:
            {
                String REVERSEDIV = check(ArithmeticToken.REVERSEDIV);
                RevDiv revDiv = revDiv();
                result.v = (int) (revDiv.v/left);
                break;
            }
            case DIV:
            case ADD:
            case SUB:
            case MUL:
            case END:
            case CP:
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
            case SUB:
            {
                String SUB = check(ArithmeticToken.SUB);
                Unary unary = unary();
                result.v = -unary.v;
                break;
            }
            case OP:
            {
                String OP = check(ArithmeticToken.OP);
                AddSub addSub = addSub();
                String CP = check(ArithmeticToken.CP);
                result.v = addSub.v;
                break;
            }
            case NUM:
            {
                String NUM = check(ArithmeticToken.NUM);
                result.v = Integer.valueOf(NUM);
                break;
            }
            default:
                throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        return result;
    }

    private String check(ArithmeticToken token) throws ParseException {
        if (token != lexer.getCurToken()) {
            throw new ParseException("Unexpected token : " + lexer.getCurToken(), lexer.getCurPos());
        }
        String res = "";
        if (lexer.getCurToken() != ArithmeticToken.END) {
            res = lexer.getCurTokenString();
            lexer.nextToken();
        }
        return res;
    }

    public class Run {
        public int v;
    }

    public class AddSub {
        public int v;
    }

    public class AddSub_ {
        public int v;
    }

    public class MulDiv {
        public int v;
    }

    public class MulDiv_ {
        public int v;
    }

    public class RevDiv {
        public int v;
    }

    public class RevDiv_ {
        public int v;
    }

    public class Unary {
        public int v;
    }

}
