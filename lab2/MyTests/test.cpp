#include "pch.h"
#include "../MT Lab2/Util.h"
#include "../MT Lab2/FirstFollowArrays.h"
#include <iostream>
#include "../MT Lab2/normalize_grammar.h"
#include "../MT Lab2/syntax_analyzer.h"
#include <fstream>
#include "../MT Lab2/visualizer.h"

grammar_type new_grammar = normalize_grammar(my_grammar());

TEST(FirstFollowArr, FirstArray) {
	std::unordered_map<std::string, std::set<std::string>> fst = First(new_grammar);

	std::set<std::string> a = {"not", "v", "("};    // for E
	std::set<std::string> b = { "xor", "or", "" };  // for E'
	std::set<std::string> c = { "not", "v", "(" };  // for T
	std::set<std::string> d = { "and", ""};         // for T'
	std::set<std::string> e = { "not", "v", "(" };  // for F
	std::set<std::string> f = { "v", "(" };         // for P

	EXPECT_EQ(fst["E"], a);
	EXPECT_EQ(fst["E'"], b);
	EXPECT_EQ(fst["T"], c);
	EXPECT_EQ(fst["T'"], d);
	EXPECT_EQ(fst["F"], e);
	EXPECT_EQ(fst["P"], f);

}

TEST(FirstFollowArr, FollowArr) {
	std::unordered_map<std::string, std::set<std::string>> fol = Follow(new_grammar);

	std::set<std::string> a = { ")", "$" };                     // for E
	std::set<std::string> b = { ")", "$" };                     // for E'
	std::set<std::string> c = { "xor", "or", ")", "$"};         // for T
	std::set<std::string> d = { "xor", "or", ")", "$"};         // for T'
	std::set<std::string> e = { "xor", "or", "and", ")", "$"};  // for F
	std::set<std::string> f = { "xor", "or", "and", ")", "$" }; // for P

	EXPECT_EQ(fol["E"], a);
	EXPECT_EQ(fol["E'"], b);
	EXPECT_EQ(fol["T"], c);
	EXPECT_EQ(fol["T'"], d);
	EXPECT_EQ(fol["F"], e);
	EXPECT_EQ(fol["P"], f);
}

bool vec_tok_eq(std::vector<token> vec1, std::vector<token> vec2) {
	for (int i = 0; i < vec1.size(); i++) {
		if ((vec1[i].get_token() != vec2[i].get_token()) || (vec1[i].get_value() != vec2[i].get_value())) {
			return false;
		}
	}
	return true;
}

TEST(lexical_analyzerTest, laT1) {
	std::string expression = "a";

	std::vector<token> token_seq = lexical_analyzer(expression);

	std::vector<token> expect = { token("a", VAR), token("$", END)};

	EXPECT_TRUE(vec_tok_eq(token_seq, expect));
}

TEST(lexical_analyzerTest, laT2) {
	std::string expression = "not";

	/*try {
		std::vector<token> token_seq = lexical_analyzer(expression);
	}
	catch (const std::exception& ex) {
		std::cout << "Parse error: " << ex.what() << std::endl;
	}*/

	EXPECT_THROW(lexical_analyzer(expression), std::exception);

	try {
		std::vector<token> token_seq = lexical_analyzer(expression);
	}
	catch (const std::exception& ex) {
		std::string err = ex.what();
		EXPECT_EQ(err, "Wrong token! It should be VAR");
	}

}

TEST(lexical_analyzerTest, laT3) {
	std::string expression = "a and b";

	std::vector<token> token_seq = lexical_analyzer(expression);

	std::vector<token> expect = { token("a", VAR), token("and", AND), token("b", VAR), token("$", END)};

	EXPECT_TRUE(vec_tok_eq(token_seq, expect));
}

TEST(lexical_analyzerTest, laT4) {
	std::string expression = "a and b and (";

	EXPECT_THROW(lexical_analyzer(expression), std::exception);

	try {
		std::vector<token> token_seq = lexical_analyzer(expression);
	}
	catch (const std::exception& ex) {
		std::string err = ex.what();
		EXPECT_EQ(err, "The expression contains an invalid parenthesis sequence");
		//std::cout << ex.what();
	}
}

TEST(lexical_analyzerTest, laT5) {
	std::string expression = "not x and not (y or not z) or t";

	std::vector<token> token_seq = lexical_analyzer(expression);

	std::vector<token> expect = { token("not", NOT), token("x", VAR), token("and", AND), token("not", NOT),
		                          token("(", LPAREN), token("y", VAR), token("or", OR), token("not", NOT),
		                          token("z", VAR), token(")", RPAREN), token("or", OR), token("t", VAR), token("$", END) };

	EXPECT_TRUE(vec_tok_eq(token_seq, expect));
}

TEST(lexical_analyzerTest, laT6) {
	std::string expression = "not x and not (y or not z) or";

	EXPECT_THROW(lexical_analyzer(expression), std::exception);

	try {
		std::vector<token> token_seq = lexical_analyzer(expression);
	}
	catch (const std::exception& ex) {
		std::string err = ex.what();
		EXPECT_EQ(err, "VAR was expected here");
	}
}

TEST(lexical_analyzerTest, laT7) {
	std::string expression = "ara and x or y";

	EXPECT_THROW(lexical_analyzer(expression), std::exception);

	try {
		std::vector<token> token_seq = lexical_analyzer(expression);
	}
	catch (const std::exception& ex) {
		std::string err = ex.what();
		EXPECT_EQ(err, "Variable name is too long");
	}
}

TEST(syntax_analyzerTest, saT0) {
	std::string expression = "()";

	EXPECT_THROW(parser(expression, new_grammar), std::exception);

	try {
		Tree tr = parser(expression, new_grammar);
	}
	catch (const std::exception& ex) {
		std::string err = ex.what();
		EXPECT_EQ(err, "Parse error: Tokens expected '(', 'not' or 'v'");
	}
}

bool tr_eq(Tree tr1, Tree tr2) {
	if (tr1.get_elem() != tr2.get_elem()) {
		return false;
	}
	
	std::vector<Tree> ch1 = tr1.get_children();
	std::vector<Tree> ch2 = tr2.get_children();
	if (ch1.size() != ch2.size()) {
		return false;
	}
	for (int i = 0; i < ch1.size(); i++) {
		if (!tr_eq(ch1[i], ch2[i])) {
			return false;
		}
	}

	return true;
}

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

TEST(syntax_analyzerTest, saT1) {
	std::string expression = "a";

	Tree tr = parser(expression, new_grammar);

	Tree expect("E", { Tree("T", {Tree("F", {Tree("P", {Tree("a")})}), Tree("T'", {Tree("eps")})}), Tree("E'", {Tree("eps")}) });

	EXPECT_TRUE(tr_eq(tr, expect));

	run("1", tr);
}

TEST(syntax_analyzerTest, saT2) {
	std::string expression = "not a and b";

	Tree tr = parser(expression, new_grammar);

	Tree expect("E", { Tree("T", {Tree("F", {Tree("not"), Tree("P", {Tree("a")})}), Tree("T'", {Tree("and"), Tree("F", {Tree("P", {Tree("b")})}), Tree("T'", {Tree("eps")})})}), Tree("E'", {Tree("eps")})});

	EXPECT_TRUE(tr_eq(tr, expect));

	run("2", tr);
}

TEST(syntax_analyzerTest, saT3) {
	std::string expression = "a and b or c and not t";

	Tree tr = parser(expression, new_grammar);

	Tree expect("E", { Tree("T", {Tree("F", {Tree("P", {Tree("a")})}), Tree("T'", {Tree("and"), Tree("F", {Tree("P", {Tree("b")})}), Tree("T'", {Tree("eps")})})}),
		Tree("E'", {Tree("or"), Tree("T", {Tree("F", {Tree("P", {Tree("c")})}), Tree("T'", {Tree("and"), Tree("F", {Tree("not"), Tree("P", {Tree("t")})}), Tree("T'", {Tree("eps")})})}), Tree("E'", {Tree("eps")})}) });

	EXPECT_TRUE(tr_eq(tr, expect));

	run("3", tr);
}

TEST(syntax_analyzerTest, saT4) {
	std::string expression = "a and (b or c) and not t";

	Tree tr = parser(expression, new_grammar);

	Tree expect("E", { Tree("T", {Tree("F", {Tree("P", {Tree("a")})}), Tree("T'", {Tree("and"), Tree("F", {Tree("P", 
		{Tree("("), Tree("E", {Tree("T", {Tree("F", {Tree("P", {Tree("b")})}), Tree("T'", {Tree("eps")})}), Tree("E'", 
			{Tree("or"), Tree("T", {Tree("F", {Tree("P", {Tree("c")})}), Tree("T'", {Tree("eps")})}), Tree("E'", {Tree("eps")})})}), Tree(")")})}), Tree("T'", {Tree("and"), Tree("F", {Tree("not"), Tree("P", {Tree("t")})}), Tree("T'", {Tree("eps")})})})}), Tree("E'", {Tree("eps")})});

	EXPECT_TRUE(tr_eq(tr, expect));

	run("4", tr);
}

TEST(syntax_analyzerTest, saT5) {
	std::string expression = "not (x and not (y or not z)) xor t";

	Tree tr = parser(expression, new_grammar);

	Tree expect("E", { Tree("T", {Tree("F", {Tree("not"), Tree("P", {Tree("("), Tree("E", {Tree("T", 
		{Tree("F", {Tree("P", {Tree("x")})}), Tree("T'", {Tree("and"), Tree("F", {Tree("not"), Tree("P", 
			{Tree("("), Tree("E", {Tree("T", {Tree("F", {Tree("P", {Tree("y")})}), Tree("T'", {Tree("eps")})}), Tree("E'",
				{Tree("or"), Tree("T", {Tree("F", {Tree("not"), Tree("P", {Tree("z")})}), Tree("T'", {Tree("eps")})}), Tree("E'", {Tree("eps")})})}), Tree(")")})}), Tree("T'", {Tree("eps")})})}), Tree("E'", {Tree("eps")})}), Tree(")")})}), Tree("T'", {Tree("eps")})}), Tree("E'", {Tree("xor"), Tree("T", {Tree("F", {Tree("P", {Tree("t")})}), Tree("T'", {Tree("eps")})}), Tree("E'", {Tree("eps")})})});

	EXPECT_TRUE(tr_eq(tr, expect));

	run("5", tr);
}