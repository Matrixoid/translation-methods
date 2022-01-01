#pragma once
#include "Tree.h"
#include <fstream>
#include <iostream>

struct vizualizer {

	vizualizer(const Tree tree, std::ofstream&& output_file) : tree(tree), output_file(std::move(output_file)) {}

	void dfs(Tree tr, int& lvl, std::ofstream& output) {
		int copy = lvl;
		output << "T" << copy << ";\n";
		output << "T" << copy << " [label=\"" << tr.get_elem() << "\"];\n";
		for (auto child : tr.get_children()) {
			output_file << "T" << copy << "--" << "T" << ++lvl << ";\n";
			dfs(child, lvl, output);
		}
	}

	void to_graphviz(Tree tr) {
		output_file << "graph { \n";
		int lvl = 0;
		dfs(tr, lvl, output_file);
		output_file << " }";
	}

private:
	Tree tree;
	std::ofstream output_file;
};