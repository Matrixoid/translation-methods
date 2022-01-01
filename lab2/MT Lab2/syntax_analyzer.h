#pragma once
#include "Util.h"
#include "normalize_grammar.h"

struct syntax_analyzer {

    syntax_analyzer() = default;
    syntax_analyzer(grammar_type grammar, std::vector<token> token_seq) : token_index(0),
        cur_token(token_seq[token_index]),
        parse_tree(E()),
        token_seq(token_seq) {}

    void next_token() {
        token_index++;
        cur_token = token_seq[token_index];
    }

    Tree E() {
        switch (cur_token.get_token()) {
        case NOT:
        case LPAREN:
        case VAR:
            return Tree("E", { T(), EH() });
        default:
            throw std::exception("Parse error: Tokens expected '(', 'not' or 'v'");
        }
    }

    Tree EH() {

        switch (cur_token.get_token()) {
        case OR:
            next_token();
            return Tree("E'", { Tree("or"), T(), EH() });
        case XOR:
            next_token();
            return Tree("E'", { Tree("xor"), T(), EH() });
        default:
            return Tree("E'", { Tree("eps") });
        }

    }

    Tree T() {

        switch (cur_token.get_token()) {
        case NOT:
        case LPAREN:
        case VAR:
            return Tree("T", { F(), TH() });
        default:
            throw std::exception("Parse error: Tokens expected '(', 'not' or 'v'");
        }

    }

    Tree TH() {

        switch (cur_token.get_token()) {
        case AND:
            next_token();
            return Tree("T'", { Tree("and"), F(), TH() });
        default:
            return Tree("T'", { Tree("eps") });
        }

    }

    Tree F() {

        switch (cur_token.get_token()) {
        case NOT:
            next_token();
            return Tree("F", { Tree("not"), P() });
        case LPAREN:
        case VAR:
            return Tree("F", { P() });
        default:
            throw std::exception("Parse error: Tokens expected '(', 'not' or 'v'");
        }

    }

    Tree P() {

        switch (cur_token.get_token()) {
        case LPAREN:
        case VAR:
            return Tree("P", { A(), PH() });
        default:
            throw std::exception("Parse error: Tokens expected '(', 'not' or 'v'");
        }

    }

    Tree PH() {
        switch (cur_token.get_token()) {
        case BOOLEQUAL:
            next_token();
            return Tree("P'", { Tree("=="), A(), PH() });
        default:
            return Tree("P'", { Tree("eps") });
        }
    }

    Tree A() {

        switch (cur_token.get_token()) {
        case LPAREN: {
            next_token();
            Tree tree = E();
            next_token();
            return Tree("A", { Tree("("), tree, Tree(")") });
        }
        case VAR: {
            std::string s = cur_token.get_value();
            next_token();
            return Tree("A", { Tree(s) });
        }
        default:
            throw std::exception("Parse error: Tokens expected '(' or 'v'");
        }

    }

    Tree get_tree() {
        return parse_tree;
    }

private:
    int token_index;
    token cur_token;
    std::vector<token> token_seq;
    Tree parse_tree;
};

Tree parser(std::string expression, grammar_type grammar) {
    grammar_type new_grammar = normalize_grammar(grammar);
    try {
        std::vector<token> token_seq = lexical_analyzer(expression);
        syntax_analyzer synana(new_grammar, token_seq);
        return synana.get_tree();
    }
    catch (const std::exception& ex) {
        throw ex;
    }
}
