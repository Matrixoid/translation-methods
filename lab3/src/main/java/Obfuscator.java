import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

public class Obfuscator {
    private final Map<String, String> nameReplacements = new HashMap<>();

    public String nextObfusName() {
        Random rnd = new Random();
        String[] arr = {"I", "O", "1", "0"};

        int fakeNameLen = 7;
        StringBuilder newObfusName;
        do {
            newObfusName = new StringBuilder();
            newObfusName.append(arr[rnd.nextInt(2)]);

            for (int i = 0; i < fakeNameLen; i++) {
                newObfusName.append(arr[rnd.nextInt(4)]);
            }
        } while (nameReplacements.containsValue(newObfusName.toString()));

        return newObfusName.toString();
    }

    public String replaceName(TerminalNode identifier) {
        String name = identifier.getText();

        if (nameReplacements.containsKey(name)) {
            return nameReplacements.get(name);
        } else {
            String replacement = nextObfusName();
            nameReplacements.put(name, replacement);
            return replacement;
        }
    }
}
