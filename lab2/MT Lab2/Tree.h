#pragma once
#include <string>
#include <vector>

class Tree {

public:
    Tree() = default;
    Tree(std::string e, std::vector<Tree> children) : elem(e), children(children) {}
    Tree(std::string e) : elem(e) {}

    std::string get_elem() {
        return elem;
    }
    std::vector<Tree> get_children() {
        return children;
    }

    void set_elem(std::string new_elem) {
        elem = new_elem;
    }
    void set_child(int i, Tree new_child) {
        if (i > children.size() - 1) {
            children.push_back(new_child);
            return;
        }
        children[i] = new_child;
    }

private:
    std::string elem;
    std::vector<Tree> children;
};