import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStreams;


public class Main {
    public static void main(String[] args) {
        String input = "main.cc";
        String output = "obfuscated_" + input;
        try {
            CPPParser parser = new CPPParser(new CommonTokenStream(new CPPLexer(CharStreams.fromFileName(input, StandardCharsets.UTF_8))));
            ObfuscatingVisitor visitor = new ObfuscatingVisitor();
            String obfuscatedCode = visitor.visitProgram(parser.program());
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(output), StandardCharsets.UTF_8)) {
                writer.write(obfuscatedCode);
            } catch (IOException e) {
                System.err.println("Failed to write obfuscation result: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Failed to read the input file: " + e.getMessage());
        }
    }
}
