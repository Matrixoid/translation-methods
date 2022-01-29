package ArithmeticTest;

import org.junit.Assert;
import org.junit.Test;
import results.Arithmetic.ArithmeticParser;

import java.text.ParseException;

public class ArithmeticParserTest {
    private void test(String input, boolean shouldFail) {
        
        ArithmeticParser parser = new ArithmeticParser();
        try {
            ArithmeticParser.Run result = parser.parse(input);
            System.out.println(input + " == " + result.v);
        } catch (ParseException e) {
            e.printStackTrace();
            Assert.assertTrue(shouldFail);
            return;
        }
        Assert.assertFalse(shouldFail);
    }

    @Test
    public void testNum() {
        test("5", false);
        test("12345", false);
        test("(10)", false);
    }

    @Test
    public void testAddSub() {
        test("2 + 2", false);
        test("2 - 1", false);
        test("1 + 2 - 4", false);
        test("1 + (2 - 4) - 2", false);
        test("1 - (2 - 4) - 2", false);
    }

    @Test
    public void testMulDiv() {
        test("3 \\ 2 \\ 126", false);
        test("2 \\ (25 \\ 100) \\ 16", false);
        test("21 / 7", false);
        test("2 + 2*2", false);
        test("(2 + 2)*2", false);
    }

    @Test
    public void testIncorrect() {
        test("2++2", true);
        //test("-2", true);
        test("(2 - 3", true);
        test("()", true);
        test("2+)", true);
    }


    /*@Test
    public void randomTests() {
        RandomTestGenerator randomTestGenerator = new RandomTestGenerator();
        for (int i = 0; i < 100; i++) {
            test(randomTestGenerator.getRandomTest(), false);
        }
    }*/
}
