package generator;

import grammar.Grammar;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class ClassPrinter {
    private String packageName;

    protected String lexerClassName;
    protected String parserClassName;
    protected String tokenClassName;

    protected Grammar grammar;

    private PrintWriter writer;
    private final String TAB = "    ";

    public ClassPrinter(String name, Grammar grammar, String path, String file) {
        try {
            writer = new PrintWriter(new File(path, file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        packageName = "results." + name;
        lexerClassName = name + "Lexer";
        parserClassName = name + "Parser";
        tokenClassName = name + "Token";
        this.grammar = grammar;
    }

    protected void printLine(int tab, String ... strings) {
        for (int i = 0; i < tab; i++) {
            writer.print(TAB);
        }

        for (String s : strings) {
            writer.print(s);
        }
        writer.println();
    }

    private void printPackage() {
        printLine(0, "package ", packageName, ";");
        writer.println();
    }

    public void printFile() {
        printPackage();
        printImports();
        printClass();
        writer.close();
    }

    protected void printImport(String file) {
        printLine(0, "import ", file, ";");
    }

    protected abstract void printImports();

    protected abstract void printClass();
}
