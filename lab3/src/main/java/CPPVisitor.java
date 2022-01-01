// Generated from C:/MPPHW/MTlab3/src/main/java\CPP.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CPPParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CPPVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CPPParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(CPPParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#include}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInclude(CPPParser.IncludeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(CPPParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters(CPPParser.ParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#defaultParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultParameterList(CPPParser.DefaultParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#defaultParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultParameter(CPPParser.DefaultParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#parametrList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametrList(CPPParser.ParametrListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(CPPParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#variables}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariables(CPPParser.VariablesContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(CPPParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#variableId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableId(CPPParser.VariableIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#variableInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableInitializer(CPPParser.VariableInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(CPPParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varBlockStatement}
	 * labeled alternative in {@link CPPParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarBlockStatement(CPPParser.VarBlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statementBlockStatement}
	 * labeled alternative in {@link CPPParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementBlockStatement(CPPParser.StatementBlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStatement}
	 * labeled alternative in {@link CPPParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(CPPParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link CPPParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(CPPParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionStatement}
	 * labeled alternative in {@link CPPParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(CPPParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(CPPParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(CPPParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryExpression}
	 * labeled alternative in {@link CPPParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpression(CPPParser.PrimaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link CPPParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallExpression(CPPParser.FunctionCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code opExpression}
	 * labeled alternative in {@link CPPParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpExpression(CPPParser.OpExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenthesisPrimary}
	 * labeled alternative in {@link CPPParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesisPrimary(CPPParser.ParenthesisPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literalPrimary}
	 * labeled alternative in {@link CPPParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralPrimary(CPPParser.LiteralPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idPrimary}
	 * labeled alternative in {@link CPPParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdPrimary(CPPParser.IdPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(CPPParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#constType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstType(CPPParser.ConstTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(CPPParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link CPPParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(CPPParser.PrimitiveTypeContext ctx);
}