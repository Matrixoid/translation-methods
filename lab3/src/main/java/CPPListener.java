// Generated from C:/MPPHW/MTlab3/src/main/java\CPP.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CPPParser}.
 */
public interface CPPListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CPPParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(CPPParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(CPPParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#include}.
	 * @param ctx the parse tree
	 */
	void enterInclude(CPPParser.IncludeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#include}.
	 * @param ctx the parse tree
	 */
	void exitInclude(CPPParser.IncludeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(CPPParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(CPPParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(CPPParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(CPPParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#defaultParameterList}.
	 * @param ctx the parse tree
	 */
	void enterDefaultParameterList(CPPParser.DefaultParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#defaultParameterList}.
	 * @param ctx the parse tree
	 */
	void exitDefaultParameterList(CPPParser.DefaultParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#defaultParameter}.
	 * @param ctx the parse tree
	 */
	void enterDefaultParameter(CPPParser.DefaultParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#defaultParameter}.
	 * @param ctx the parse tree
	 */
	void exitDefaultParameter(CPPParser.DefaultParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#parametrList}.
	 * @param ctx the parse tree
	 */
	void enterParametrList(CPPParser.ParametrListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#parametrList}.
	 * @param ctx the parse tree
	 */
	void exitParametrList(CPPParser.ParametrListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(CPPParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(CPPParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#variables}.
	 * @param ctx the parse tree
	 */
	void enterVariables(CPPParser.VariablesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#variables}.
	 * @param ctx the parse tree
	 */
	void exitVariables(CPPParser.VariablesContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(CPPParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(CPPParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#variableId}.
	 * @param ctx the parse tree
	 */
	void enterVariableId(CPPParser.VariableIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#variableId}.
	 * @param ctx the parse tree
	 */
	void exitVariableId(CPPParser.VariableIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void enterVariableInitializer(CPPParser.VariableInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void exitVariableInitializer(CPPParser.VariableInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(CPPParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(CPPParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varBlockStatement}
	 * labeled alternative in {@link CPPParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterVarBlockStatement(CPPParser.VarBlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varBlockStatement}
	 * labeled alternative in {@link CPPParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitVarBlockStatement(CPPParser.VarBlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statementBlockStatement}
	 * labeled alternative in {@link CPPParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterStatementBlockStatement(CPPParser.StatementBlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statementBlockStatement}
	 * labeled alternative in {@link CPPParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitStatementBlockStatement(CPPParser.StatementBlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStatement}
	 * labeled alternative in {@link CPPParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(CPPParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStatement}
	 * labeled alternative in {@link CPPParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(CPPParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link CPPParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(CPPParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link CPPParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(CPPParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionStatement}
	 * labeled alternative in {@link CPPParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(CPPParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionStatement}
	 * labeled alternative in {@link CPPParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(CPPParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(CPPParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(CPPParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(CPPParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(CPPParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primaryExpression}
	 * labeled alternative in {@link CPPParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpression(CPPParser.PrimaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primaryExpression}
	 * labeled alternative in {@link CPPParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpression(CPPParser.PrimaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link CPPParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpression(CPPParser.FunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link CPPParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpression(CPPParser.FunctionCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code opExpression}
	 * labeled alternative in {@link CPPParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOpExpression(CPPParser.OpExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code opExpression}
	 * labeled alternative in {@link CPPParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOpExpression(CPPParser.OpExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenthesisPrimary}
	 * labeled alternative in {@link CPPParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterParenthesisPrimary(CPPParser.ParenthesisPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenthesisPrimary}
	 * labeled alternative in {@link CPPParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitParenthesisPrimary(CPPParser.ParenthesisPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code literalPrimary}
	 * labeled alternative in {@link CPPParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterLiteralPrimary(CPPParser.LiteralPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code literalPrimary}
	 * labeled alternative in {@link CPPParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitLiteralPrimary(CPPParser.LiteralPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idPrimary}
	 * labeled alternative in {@link CPPParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterIdPrimary(CPPParser.IdPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idPrimary}
	 * labeled alternative in {@link CPPParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitIdPrimary(CPPParser.IdPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(CPPParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(CPPParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#constType}.
	 * @param ctx the parse tree
	 */
	void enterConstType(CPPParser.ConstTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#constType}.
	 * @param ctx the parse tree
	 */
	void exitConstType(CPPParser.ConstTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(CPPParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(CPPParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link CPPParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(CPPParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CPPParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(CPPParser.PrimitiveTypeContext ctx);
}