#pragma once
#include <unordered_set>
#include <iostream>
#include <functional>
#include <set>
#include "Tokens.h"
#include "Tree.h"
#include "lexical_analyzer.h"

using grammar_type = std::unordered_map<std::string, std::unordered_set<std::string>>;

grammar_type my_grammar() {
    grammar_type grammar;
    grammar["E"].insert("E or T");
    grammar["E"].insert("E xor T");
    grammar["E"].insert("T");
    grammar["T"].insert("T and F");
    grammar["T"].insert("F");
    grammar["F"].insert("not P");
    grammar["F"].insert("P");
    grammar["P"].insert("P == A");
    grammar["P"].insert("A");
    grammar["A"].insert("( E )");
    grammar["A"].insert("v");

    return grammar;
}

void print_Tokens(std::vector<token> tokens) {
    for (auto i : tokens) {
        switch (i.get_token()) {
            case LPAREN:
                std::cout << "LPAREN ";
                break;
            case RPAREN:
                std::cout << "RPAREN ";
                break;
            case NOT:
                std::cout << "NOT ";
                break;
            case AND:
                std::cout << "AND ";
                break;
            case OR:
                std::cout << "OR ";
                break;
            case XOR:
                std::cout << "XOR ";
                break;
            case VAR:
                std::cout << "VAR ";
                break;
            case BOOLEQUAL:
                std::cout << "BOOLEQUAL ";
                break;
            case END:
                std::cout << "END";
        }
    }
}

void print_grammar(grammar_type grammar) {
    for (auto p : grammar) {
        for (auto s : p.second) {
            std::cout << p.first << " -> " << s << std::endl;
        }
    }
}

void print_grammar(std::unordered_map<std::string, std::set<std::string>> grammar) {
    for (auto p : grammar) {
        for (auto s : p.second) {
            std::cout << p.first << " -> " << s << std::endl;
        }
    }
}