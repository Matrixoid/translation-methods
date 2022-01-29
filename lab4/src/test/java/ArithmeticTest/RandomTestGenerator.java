package ArithmeticTest;

import java.util.Random;

public class RandomTestGenerator {
    private Random random = new Random();

    public String getRandomTest() {
        return getAddSub();
    }

    private int getProbability() {
        return random.nextInt(100);
    }

    private String getAddSub() {
        if (getProbability() < 50) {
            return getAddSub() + (getProbability() < 50 ? "+" : "-") + getMulDiv();
        }
        return getMulDiv();
    }

    private String getMulDiv() {
        if (getProbability() < 50) {
            return getMulDiv() + "*" + getUnary();
        }
        return getUnary();
    }

    private String getUnary() {
        if (getProbability() < 80) {
            return Integer.toString(1 + getProbability());
        }
        return "(" +  getAddSub() + ")";
    }
}
