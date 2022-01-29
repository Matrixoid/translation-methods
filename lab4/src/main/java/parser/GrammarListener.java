package parser;// Generated from Grammar.g4 by ANTLR 4.7.1

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(GrammarParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(GrammarParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#choose_rule}.
	 * @param ctx the parse tree
	 */
	void enterChoose_rule(GrammarParser.Choose_ruleContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#choose_rule}.
	 * @param ctx the parse tree
	 */
	void exitChoose_rule(GrammarParser.Choose_ruleContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#non_term_rule}.
	 * @param ctx the parse tree
	 */
	void enterNon_term_rule(GrammarParser.Non_term_ruleContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#non_term_rule}.
	 * @param ctx the parse tree
	 */
	void exitNon_term_rule(GrammarParser.Non_term_ruleContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(GrammarParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(GrammarParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#non_term_returns}.
	 * @param ctx the parse tree
	 */
	void enterNon_term_returns(GrammarParser.Non_term_returnsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#non_term_returns}.
	 * @param ctx the parse tree
	 */
	void exitNon_term_returns(GrammarParser.Non_term_returnsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#arg}.
	 * @param ctx the parse tree
	 */
	void enterArg(GrammarParser.ArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#arg}.
	 * @param ctx the parse tree
	 */
	void exitArg(GrammarParser.ArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#var_or_type}.
	 * @param ctx the parse tree
	 */
	void enterVar_or_type(GrammarParser.Var_or_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#var_or_type}.
	 * @param ctx the parse tree
	 */
	void exitVar_or_type(GrammarParser.Var_or_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#rightPart}.
	 * @param ctx the parse tree
	 */
	void enterRightPart(GrammarParser.RightPartContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#rightPart}.
	 * @param ctx the parse tree
	 */
	void exitRightPart(GrammarParser.RightPartContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#ruleToken}.
	 * @param ctx the parse tree
	 */
	void enterRuleToken(GrammarParser.RuleTokenContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#ruleToken}.
	 * @param ctx the parse tree
	 */
	void exitRuleToken(GrammarParser.RuleTokenContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(GrammarParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(GrammarParser.ParamContext ctx);
}