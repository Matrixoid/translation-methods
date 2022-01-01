#pragma once
#include "Util.h";
#include <string>

grammar_type elimination_of_left_recursion(grammar_type grammar) {

    grammar_type grammar1 = grammar;
    grammar_type new_grammar;

    std::function<bool(std::string, std::string)> isPrefix = [](std::string lhs, std::string rhs) {
        for (int i = 0; i < lhs.length(); i++) {
            if (lhs[i] != rhs[i]) {
                return false;
            }
        }
        return true;
    };

    std::function<std::string(std::string, std::string)> ret_alpha = [isPrefix](std::string lhs, std::string rhs) {
        if (isPrefix(lhs, rhs)) {
            return rhs.erase(0, lhs.length());
        }
        return rhs;
    };

    std::function<std::string(std::string)> skip_useless_whitespaces = [](std::string str) {
        int i = 0;
        while (str[i] == ' ') {
            i++;
        }
        str = str.erase(0, i);

        i = str.length();
        while (str[i - 1] == ' ') {
            i--;
        }
        str = str.erase(i);

        i = 0;
        int k = 0;
        for (int j = 0; j < str.length(); j++) {
            if (str[j] == ' ') {
                k = j;
                i = 0;
                while (str[k] == ' ') {
                    i++;
                    k++;
                }

                if (i >= 2) {
                    str = str.erase(k - i + 1, i - 1);
                }
            }
        }

        return str;
    };

    for (auto p : grammar) {
        std::vector<std::string> beta;
        for (auto s : p.second) {
            if (!isPrefix(p.first, s)) {
                beta.push_back(s);
            }
        }

        std::string new_noterm = p.first;
        while (grammar1.find(new_noterm) != grammar1.end()) {
            new_noterm += "'";
        }
        for (auto s : p.second) {
            if (isPrefix(p.first, s)) {
                for (std::string b : beta) {
                    grammar1[p.first].erase(s);
                    grammar1[p.first].erase(b);
                    new_grammar[p.first].insert(skip_useless_whitespaces(b + " " + new_noterm));
                    new_grammar[new_noterm].insert(skip_useless_whitespaces(ret_alpha(p.first, s) + " " + new_noterm));
                    new_grammar[new_noterm].insert("");
                }
            }
        }

    }

    for (auto p : grammar1) {
        for (auto s : p.second) {
            new_grammar[p.first].insert(s);
        }
    }

    return new_grammar;
}

grammar_type elimination_of_right_branch(grammar_type grammar) {

    grammar_type grammar1 = grammar;
    grammar_type new_grammar;

    std::function<std::string(std::string, std::string)> common_prefix = [](std::string fst, std::string snd) {
        int max_len = std::min(fst.length(), snd.length());
        std::string max_common_prefix = "";

        for (int i = 0; i < max_len; i++) {
            if (fst[i] == snd[i]) {
                max_common_prefix += fst[i];
                continue;
            }
            break;
        }

        return max_common_prefix;
    };

    std::function<std::string(std::string)> skip_useless_whitespaces = [](std::string str) {
        int i = 0;
        while (str[i] == ' ') {
            i++;
        }
        str = str.erase(0, i);

        i = str.length();
        while (str[i - 1] == ' ') {
            i--;
        }
        str = str.erase(i);

        i = 0;
        int k = 0;
        for (int j = 0; j < str.length(); j++) {
            if (str[j] == ' ') {
                k = j;
                i = 0;
                while (str[k] == ' ') {
                    i++;
                    k++;
                }

                if (i >= 2) {
                    str = str.erase(k - i + 1, i - 1);
                }
            }
        }

        return str;
    };

    for (auto p1 : grammar) {

        std::string new_noterm = p1.first;
        while (grammar1.find(new_noterm) != grammar1.end()) {
            new_noterm += "'";
        }
        for (auto s1 : p1.second) {
            for (auto s2 : p1.second) {
                if (s1 == s2 || common_prefix(s1, s2) == "") {
                    continue;
                }
                grammar1[p1.first].erase(s1);
                grammar1[p1.first].erase(s2);
                std::string cmn_prfx = common_prefix(s1, s2);
                new_grammar[p1.first].insert(skip_useless_whitespaces(cmn_prfx + " " + new_noterm));
                new_grammar[new_noterm].insert(skip_useless_whitespaces(s1.erase(0, cmn_prfx.length())));
                new_grammar[new_noterm].insert(skip_useless_whitespaces(s2.erase(0, cmn_prfx.length())));
            }
        }
    }

    for (auto p : grammar1) {
        for (auto s : p.second) {
            new_grammar[p.first].insert(s);
        }
    }

    return new_grammar;
}

/*
    E  -> T E'
    E' -> eps
    E' -> xor T E'
    E' -> or T E'
    T  -> F T'
    T' -> eps
    T' -> and F T'
    F  -> not P
    F  -> P
    P  -> ( E )
    P  -> v
*/
grammar_type normalize_grammar(grammar_type grammar) {
    grammar_type new_grammar = elimination_of_left_recursion(grammar);
    new_grammar = elimination_of_right_branch(new_grammar);
    while (new_grammar != grammar) {
        grammar = new_grammar;
        new_grammar = normalize_grammar(new_grammar);
    }
    return new_grammar;
}