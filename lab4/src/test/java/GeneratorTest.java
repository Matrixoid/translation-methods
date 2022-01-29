
import generator.LexerGenerator;
import generator.ParserGenerator;
import generator.TokenGenerator;
import grammar.Grammar;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.GrammarLexer;
import parser.GrammarParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GeneratorTest {
    public static void main(String[] args) throws IOException {
        generateParser("Arithmetic");
        generateParser("PythonLogic");
    }

    private static void generateParser(String name) throws IOException {
        String inFile = "grammars/" + name;
        Path outFile = Paths.get("src/main/java/results", name);

        String outPath;
        if (Files.notExists(outFile)) {
            outPath = Files.createDirectory(outFile).toString();
        } else {
            outPath = outFile.toString();
        }

        GrammarLexer lexer = new GrammarLexer(CharStreams.fromFileName(inFile));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GrammarParser parser = new GrammarParser(tokens);

        Grammar grammar = parser.start().v;
        grammar.buildFirstAndFollowSets();

        if (grammar.isLL1()) {
            new TokenGenerator(name, grammar, outPath).printFile();
            new LexerGenerator(name, grammar, outPath).printFile();
            new ParserGenerator(name, grammar, outPath).printFile();
        } else {
            System.out.println("Input grammar is not LL(1)-Grammar");
        }
    }
}
