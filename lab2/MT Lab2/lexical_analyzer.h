#pragma once
#include <vector>
#include <iostream>
#include <functional>
#include "Tokens.h"

std::vector<token> lexical_analyzer(std::string parse_string) {

    std::vector<token> res;

    std::function<std::string()> next_word = [&parse_string]() {

        int i = 0;
        while (i != parse_string.size() && parse_string[i] == ' ') {
            i++;
        }
        std::string res = "";
        if (i != parse_string.size() && (parse_string[i] == '(' || parse_string[i] == ')')) {
            res += parse_string[i];
            parse_string.erase(0, i + 1);
            return res;
        }
        while (i != parse_string.size() && parse_string[i] != ' ' && parse_string[i] != '(' && parse_string[i] != ')') {
            res += parse_string[i];
            i++;
        }
        parse_string.erase(0, i);
        return res;

    };

    std::function<bool(std::vector<token>)> check_balance = [](std::vector<token> vec) {

        int balance = 0;
        for (int i = 0; i < vec.size(); i++) {
            if (vec[i].get_token() == LPAREN)
                balance++;
            else if (vec[i].get_token() == RPAREN)
                balance--;

            if (balance < 0)
                return false;
        }

        if (balance > 0)
            return false;

        return true;

    };

    std::function<token()> next_token = [&parse_string, next_word]() {

        std::string word = next_word();
        if (word == "(") {
            return token("(", LPAREN);
        }
        else if (word == ")") {
            return token(")", RPAREN);
        }
        else if (word == "and") {
            return token("and", AND);
        }
        else if (word == "or") {
            return token("or", OR);
        }
        else if (word == "xor") {
            return token("xor", XOR);
        }
        else if (word == "not") {
            return token("not", NOT);
        }
        else if (word == "") {
            return token("$", END);
        }
        else if (word == "==") {
            return token("==", BOOLEQUAL);
        }
        else {
            if (word.size() != 1) {
                throw std::exception("Variable name is too long");
            }
            return token(word, VAR);
        }

    };

    std::function<void(std::vector<token>)> check_legal = [check_balance](std::vector<token> r) {

        if (!check_balance(r)) {
            throw std::exception("The expression contains an invalid parenthesis sequence");
        }

        if (r.size() == 1) {
            throw std::exception("Input is empty");
        }

        if (r.size() == 2 && r[0].get_token() != VAR) {
            throw std::exception("Wrong token! It should be VAR");
        }

        int i = 0;
        Tokens tk1 = r[i].get_token();
        Tokens tk2 = r[i + 1].get_token();

        while (tk2 != END) {
            if (tk1 == NOT && (tk2 == AND || tk2 == OR || tk2 == XOR || tk2 == RPAREN)) {
                throw std::exception("A binary operation or ')' cannot follow the NOT");
            }

            if (tk1 == AND && (tk2 == AND || tk2 == OR || tk2 == XOR || tk2 == RPAREN)) {
                throw std::exception("A binary operation or ')' cannot follow the AND");
            }

            if (tk1 == OR && (tk2 == AND || tk2 == OR || tk2 == XOR || tk2 == RPAREN)) {
                throw std::exception("A binary operation or RPAREN cannot follow the OR");
            }

            if (tk1 == XOR && (tk2 == AND || tk2 == OR || tk2 == XOR || tk2 == RPAREN)) {
                throw std::exception("A binary operation or RPAREN cannot follow the XOR");
            }

            if (tk1 == LPAREN && (tk2 == AND || tk2 == OR || tk2 == XOR)) {
                throw std::exception("A binary operation cannot follow the LPAREN");
            }

            if (tk1 == VAR && (tk2 == NOT || tk2 == VAR || tk2 == LPAREN)) {
                throw std::exception("After VAR a binary operation or END is expected");
            }

            if (tk1 == RPAREN && (tk2 == NOT || tk2 == VAR || tk2 == LPAREN)) {
                throw std::exception("After RPAREN a binary operation or END is expected");
            }

            tk1 = tk2;
            tk2 = r[++i + 1].get_token();

        }

        if (tk1 == AND || tk1 == OR || tk1 == XOR || tk1 == NOT || tk1 == LPAREN) {
            throw std::exception("VAR was expected here");
        }

    };

    token curToken;
    Tokens tk = curToken.get_token();
    do {
        try {
            curToken = next_token();
            tk = curToken.get_token();
        }
        catch (const std::exception& ex) {
            throw ex;
            break;
        }
        res.push_back(curToken);
    } while (tk != END);

    try {
        check_legal(res);
    }
    catch (const std::exception& ex) {
        throw ex;
    }

    return res;
}
