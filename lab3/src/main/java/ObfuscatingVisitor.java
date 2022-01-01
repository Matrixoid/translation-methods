import org.antlr.v4.runtime.tree.*;

import java.util.*;
import java.util.stream.Collectors;

public class ObfuscatingVisitor extends AbstractParseTreeVisitor<String> implements CPPVisitor<String> {

    private int tabulation = 0;

    Obfuscator obf = new Obfuscator();

    @Override
    public String visitProgram(CPPParser.ProgramContext ctx) {
        String includes = ctx.include().stream().map(this::visit).collect(Collectors.joining("\n"));
        String functions = ctx.function().stream().map(this::visit).collect(Collectors.joining("\n"));
        return includes + '\n' + functions;
    }

    @Override
    public String visitFunction(CPPParser.FunctionContext ctx) {
        String type = ctx.type() != null ? visit(ctx.type()) : "void";
        String params = visit(ctx.parameters());
        String contents = ctx.block() != null ? " " + visit(ctx.block()) : ";";
        return type + " " + ctx.IDENTIFIER().getText() + params + contents;
    }

    @Override
    public String visitInclude(CPPParser.IncludeContext ctx) {
        return String.format("#include <%s>", ctx.IDENTIFIER().getText());
    }

    @Override
    public String visitVariables(CPPParser.VariablesContext ctx) {
        return ctx.variable().stream().map(this::visit).collect(Collectors.joining(", "));
    }

    @Override
    public String visitVariable(CPPParser.VariableContext ctx) {
        return visit(ctx.variableId()) + (ctx.variableInitializer() == null ? "" : (" = " + visit(ctx.variableInitializer())));
    }

    @Override
    public String visitVariableId(CPPParser.VariableIdContext ctx) {
        return obf.replaceName(ctx.IDENTIFIER());
    }

    @Override
    public String visitVariableInitializer(CPPParser.VariableInitializerContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public String visitParameters(CPPParser.ParametersContext ctx) {
        return "(" + visitIfNotNull(ctx.parametrList()) + ", " + visitIfNotNull(ctx.defaultParameterList()) + ")";
    }

    @Override
    public String visitDefaultParameterList(CPPParser.DefaultParameterListContext ctx) {
        return ctx.defaultParameter().stream().map(this::visitDefaultParameter).collect(Collectors.joining(", "));
    }

    @Override
    public String visitDefaultParameter(CPPParser.DefaultParameterContext ctx) {
        return String.format("%s %s = %s", visit(ctx.type()), visit(ctx.variableId()), visit(ctx.expression()));
    }

    @Override
    public String visitParametrList(CPPParser.ParametrListContext ctx) {
        return ctx.parameter().stream().map(this::visitParameter).collect(Collectors.joining(", "));
    }

    @Override
    public String visitParameter(CPPParser.ParameterContext ctx) {
        return String.format("%s %s", visit(ctx.type()), visit(ctx.variableId()));
    }

    @Override
    public String visitBlock(CPPParser.BlockContext ctx) {
        tabulation++;
        List<String> contents = new ArrayList<>();
        for (CPPParser.BlockStatementContext c : ctx.blockStatement()) {
            String line = tabulate() + visit(c);
            contents.add(line);
        }
        tabulation--;
        return String.format("{\n%s\n%s}", String.join("\n", contents), tabulate());
    }

    @Override
    public String visitVarBlockStatement(CPPParser.VarBlockStatementContext ctx) {
        return String.format("%s %s;", visit(ctx.type()), visit(ctx.variables()));
    }

    @Override
    public String visitStatementBlockStatement(CPPParser.StatementBlockStatementContext ctx) {
        return visit(ctx.statement());
    }

    @Override
    public String visitIfStatement(CPPParser.IfStatementContext ctx) {
        String failBranch = ctx.failBranch == null ? "" : "else " + visit(ctx.failBranch);
        return String.format("if (%s) %s%s", visit(ctx.expression()), visit(ctx.succBranch), failBranch);
    }

    @Override
    public String visitWhileStatement(CPPParser.WhileStatementContext ctx) {
        return String.format("while (%s) %s", visit(ctx.expression()), visit(ctx.statement()));
    }

    @Override
    public String visitExpressionStatement(CPPParser.ExpressionStatementContext ctx) {
        String ret = ctx.RETURN() == null ? "" : "return";
        String exp = visitIfNotNull(ctx.expression());
        return ret + (!ret.isEmpty() && !exp.isEmpty() ? " " : "") + exp + ";";
    }

    @Override
    public String visitExpressionList(CPPParser.ExpressionListContext ctx) {
        return ctx.expression().stream().map(this::visit).collect(Collectors.joining(", "));
    }

    @Override
    public String visitFunctionCall(CPPParser.FunctionCallContext ctx) {
        return String.format("%s(%s)", ctx.IDENTIFIER().getText(), visitIfNotNull(ctx.expressionList()));
    }

    @Override
    public String visitPrimaryExpression(CPPParser.PrimaryExpressionContext ctx) {
        return visit(ctx.primary());
    }

    @Override
    public String visitFunctionCallExpression(CPPParser.FunctionCallExpressionContext ctx) {
        return visit(ctx.functionCall());
    }

    @Override
    public String visitOpExpression(CPPParser.OpExpressionContext ctx) {
        if (ctx.bop != null) {
            return String.format("%s %s %s", visit(ctx.expression(0)), ctx.bop.getText(), visit(ctx.expression(1)));
        } else {
            return ctx.prefix.getText() + visit(ctx.expression(0));
        }
    }

    @Override
    public String visitParenthesisPrimary(CPPParser.ParenthesisPrimaryContext ctx) {
        return "(" + visit(ctx.expression()) + ")";
    }

    @Override
    public String visitLiteralPrimary(CPPParser.LiteralPrimaryContext ctx) {
        return ctx.literal().getText();
    }

    @Override
    public String visitIdPrimary(CPPParser.IdPrimaryContext ctx) {
        return obf.replaceName(ctx.IDENTIFIER());
    }

    @Override
    public String visitType(CPPParser.TypeContext ctx) {
        String res = "";
        res = visitIfNotNull(ctx.primitiveType());
        if (Objects.equals(res, ""))
            res = visitIfNotNull(ctx.constType());
        return res;
    }

    @Override
    public String visitConstType(CPPParser.ConstTypeContext ctx) {
        return "const " + visitIfNotNull(ctx.primitiveType());
    }

    @Override
    public String visitLiteral(CPPParser.LiteralContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitPrimitiveType(CPPParser.PrimitiveTypeContext ctx) {
        return ctx.getText();
    }

    private String visitIfNotNull(ParseTree a) {
        return a == null ? "" : visit(a);
    }

    private String tabulate() {
        return new String(new char[4 * tabulation]).replace("\0", " ");
    }
}
