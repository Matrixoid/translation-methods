#include <unordered_map>
#include <vector>
#include "Util.h"
#include "Tokens.h"
#include "Tree.h"
#include "visualizer.h"
#include "normalize_grammar.h"
#include "lexical_analyzer.h"
#include "syntax_analyzer.h"
#include "FirstFollowArrays.h"

std::string run_file(std::string test_number, std::string tree_file, std::string graphviz_file) {
	return "dot -Tsvg " + tree_file + " > " + graphviz_file;
}

void run(std::string test_number, Tree tr) {
	std::ofstream tree_file;
	std::string tree_filename = "tree" + test_number + ".dot";
	tree_file.open("tree" + test_number + ".dot");
	vizualizer vz(tr, std::move(tree_file));
	vz.to_graphviz(tr);
	tree_file.close();

	std::ofstream run_f;
	run_f.open("run" + test_number + ".bat");
	run_f << run_file(test_number, tree_filename, "graph" + test_number + ".svg");
	run_f.close();

}

int main(int argc, char** argv)
{
    std::string str = "a and (b == c) and d";
    grammar_type new_grammar = normalize_grammar(my_grammar());
	//print_grammar(new_grammar);
	std::vector<token> tok_seq = lexical_analyzer(str);
	//print_Tokens(tok_seq);
	try {
		Tree tr = parser(str, new_grammar);
		run("11", tr);
	}
	catch (std::exception ex) {
		std::cout << ex.what();
	}

    //std::unordered_map<std::string, std::set<std::string>> fol = Follow(normalize_grammar(my_grammar()));

}
