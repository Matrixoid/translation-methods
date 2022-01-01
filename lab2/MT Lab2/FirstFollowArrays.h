#pragma once
#include "Util.h"
#include <set>

/*
    E  -> not, (, v
    E' -> or, xor, eps
    T  -> not, (, v
    T' -> and, eps
    F  -> not, (, v
    P  -> (, v
*/
std::unordered_map<std::string, std::set<std::string>> First(grammar_type grammar) {

    std::unordered_map<std::string, int> F;
    for (auto t : grammar) {
        F[t.first] = 0;
    }
    std::unordered_map<std::string, std::set<std::string>> FIRST;

    std::function<std::unordered_set<std::string>(std::string)> fst = [&FIRST, &fst](std::string rule) {

        std::function <std::string()> skip_whitespaces = [rule]() {
            std::string res = "";
            for (char ch : rule) {
                if (ch != ' ' && ch != '\n' && ch != '\r' && ch != '\t') {
                    res += ch;
                }
            }
            return res;
        };

        rule = skip_whitespaces();

        std::unordered_set<std::string> ret;

        if (rule == "") {
            ret.insert("");
            return ret;
        }
        if (rule[0] >= 'a' && rule[0] <= 'z' || rule[0] == '(') {
            int i = 1;
            while (rule[i] >= 'a' && rule[i] <= 'z') {
                i++;
            }
            rule.erase(i);
            ret.insert(rule);
            return ret;
        }
        if (rule[0] >= 'A' && rule[0] <= 'Z') {
            int i = 1;
            while (rule.length() > 0 && rule[i] == '\'') {
                i++;
            }
            std::string rule_p = rule;
            rule.erase(i);
            rule_p.erase(0, i);
            ret.insert(FIRST[rule].begin(), FIRST[rule].end());
            if (ret.find("") != ret.end()) {
                ret.erase("");
                std::unordered_set<std::string> retb = fst(rule_p);
                ret.insert(retb.begin(), retb.end());
            }
            return ret;
        }
        return ret;
    };

    std::function<bool()> was_changed = [grammar, &FIRST, &F]() {

        for (auto t : grammar) {
            if (FIRST[t.first].size() != F[t.first]) {
                return true;
            }
        }
        return false;
    };

    do {
        for (auto t : grammar) {
            F[t.first] = FIRST[t.first].size();
        }
        for (auto t : grammar) {
            for (auto s : t.second) {
                std::unordered_set<std::string> fstr = fst(s);
                FIRST[t.first].insert(fstr.begin(), fstr.end());
            }

        }
    } while (was_changed());

    return FIRST;
}

//Исправить (out of range)
/*
    E  -> ), $
    E' -> ), $
    T  -> xor, or, ), $
    T' -> xor, or, ), $
    F  -> xor, or, and, ), $
    P  -> xor, or, and, ), $
*/
std::unordered_map<std::string, std::set<std::string>> Follow(grammar_type grammar) {

    std::unordered_map<std::string, int> F;
    for (auto t : grammar) {
        F[t.first] = 0;
    }
    std::unordered_map<std::string, std::set<std::string>> FOLLOW;
    FOLLOW["E"].insert("$");

    std::unordered_map<std::string, std::set<std::string>> FIRST = First(grammar);

    std::function<std::vector<std::string>(std::string, char)> split = [](std::string rule, char sep) {

        std::vector<std::string> res;

        std::string str = "";
        for (int i = 0; i < rule.length(); i++) {
            if (rule[i] != sep) {
                str += rule[i];
                if (i == rule.length() - 1) {
                    res.push_back(str);
                    break;
                }
                continue;
            }
            if (str != "")
                res.push_back(str);
            str = "";
        }

        return res;
    };

    std::function<bool()> was_changed = [grammar, &FIRST, &F]() {

        for (auto t : grammar) {
            if (FIRST[t.first].size() != F[t.first]) {
                for (auto t : grammar) {
                    F[t.first] = FIRST[t.first].size();
                }
                return true;
            }
        }
        return false;
    };

    do {
        for (auto t : grammar) {
            for (auto r : t.second) {
                std::vector<std::string> spl = split(r, ' ');
                for (int i = 0; i < spl.size(); i++) {
                    if (spl[i][0] >= 'A' && spl[i][0] <= 'Z') {
                        if (i != spl.size() - 1) {
                            if (FIRST[spl[i + 1]].size() > 0) {
                                FOLLOW[spl[i]].insert(FIRST[spl[i + 1]].begin(), FIRST[spl[i + 1]].end());
                                FOLLOW[spl[i]].erase("");
                            }
                            else {
                                FOLLOW[spl[i]].insert(spl[i + 1]);
                            }
                            if (FIRST[spl[i + 1]].find("") != FIRST[spl[i + 1]].end()) {
                                FOLLOW[spl[i]].insert(FOLLOW[t.first].begin(), FOLLOW[t.first].end());
                            }
                            continue;
                        }
                        FOLLOW[spl[i]].insert(FOLLOW[t.first].begin(), FOLLOW[t.first].end());
                    }
                }
            }
        }
    } while (was_changed());

    return FOLLOW;
}