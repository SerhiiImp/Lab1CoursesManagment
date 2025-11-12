package ua.university;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class BasicOperatorsTest {

    private final double EPS = 1e-9;

    @DataProvider(name = "sumAndAverageData")
    public Object[][] sumAndAverageData() {
        return new Object[][] {
                {1, 2, 3, new double[]{6, 2}},
                {10, 20, 30, new double[]{60, 20}},
                {-5, 5, 0, new double[]{0, 0}}
        };
    }

    @Test(dataProvider = "sumAndAverageData")
    public void testSumAndAverage(int a, int b, int c, double[] expected) {
        SoftAssert sa = new SoftAssert();
        double[] result = BasicOperators.sumAndAverage(a, b, c);
        sa.assertEquals(result[0], expected[0], "Sum mismatch");
        sa.assertEquals(result[1], expected[1], "Average mismatch");
        sa.assertAll();
    }

    @DataProvider(name = "maxOfThreeData")
    public Object[][] maxOfThreeData() {
        return new Object[][] {
                {1, 2, 3, 3},
                {10, 20, 5, 20},
                {7, 7, 7, 7}
        };
    }

    @Test(dataProvider = "maxOfThreeData")
    public void testMaxOfThree(int a, int b, int c, int expected) {
        int result = BasicOperators.maxOfThree(a, b, c);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, expected, "Max mismatch");
        sa.assertAll();
    }

    @DataProvider(name = "gradeFromScoreData")
    public Object[][] gradeFromScoreData() {
        return new Object[][] {
                {95, 'A'},
                {85, 'B'},
                {76, 'C'},
                {65, 'D'},
                {61, 'D'},
                {60, 'D'},
                {59, 'E'},
                {49, 'F'}
        };
    }

    @Test(dataProvider = "gradeFromScoreData")
    public void testGradeFromScore(int score, char expected) {
        char result = BasicOperators.gradeFromScore(score);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, expected, "Grade mismatch for value " + score);
        sa.assertAll();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGradeFromScoreInvalid() {
        BasicOperators.gradeFromScore(120);
    }

    @DataProvider(name = "dayOfWeekData")
    public Object[][] dayOfWeekData() {
        return new Object[][] {
                {1, "Monday"},
                {5, "Friday"},
                {7, "Sunday"}
        };
    }

    @Test(dataProvider = "dayOfWeekData")
    public void testDayOfWeek(int input, String expected) {
        String result = BasicOperators.dayOfWeek(input);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, expected, "Day mismatch");
        sa.assertAll();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDayOfWeekInvalid() {
        BasicOperators.dayOfWeek(10);
    }

    @DataProvider(name = "countdownData")
    public Object[][] countdownData() {
        return new Object[][] {
                {3, new int[]{3, 2, 1}},
                {5, new int[]{5, 4, 3, 2, 1}}
        };
    }

    @Test(dataProvider = "countdownData")
    public void testCountdown(int n, int[] expected) {
        int[] result = BasicOperators.countdown(n);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, expected, "Countdown mismatch");
        sa.assertAll();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCountdownNegative() {
        BasicOperators.countdown(-5);
    }

    @DataProvider(name = "factorialData")
    public Object[][] factorialData() {
        return new Object[][] {
                {0, 1},
                {5, 120},
                {7, 5040}
        };
    }

    @Test(dataProvider = "factorialData")
    public void testFactorial(int n, long expected) {
        long result = BasicOperators.factorial(n);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, expected, "Factorial mismatch");
        sa.assertAll();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFactorialNegative() {
        BasicOperators.factorial(-3);
    }

    @Test
    public void testReverseArray() {
        int[] arr = {1, 2, 3, 4};
        int[] expected = {4, 3, 2, 1};
        int[] result = BasicOperators.reverseArray(arr);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, expected, "Reverse mismatch");
        sa.assertAll();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testReverseArrayNull() {
        BasicOperators.reverseArray(null);
    }

    @Test
    public void testSumMatrix() {
        int[][] matrix = {{1,2}, {3,4}};
        int expected = 10;
        int result = BasicOperators.sumMatrix(matrix);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, expected, "Matrix sum mismatch");
        sa.assertAll();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSumMatrixNull() {
        BasicOperators.sumMatrix(null);
    }

    @DataProvider(name = "palindromeData")
    public Object[][] palindromeData() {
        return new Object[][] {
                {"madam", true},
                {"racecar", true},
                {"java", false}
        };
    }

    @Test(dataProvider = "palindromeData")
    public void testIsPalindrome(String input, boolean expected) {
        boolean result = BasicOperators.isPalindrome(input);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, expected, "Palindrome mismatch");
        sa.assertAll();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIsPalindromeNull() {
        BasicOperators.isPalindrome(null);
    }

    @Test
    public void testFindMinMax() {
        int[] arr = {5, 2, 9, -1};
        int[] expected = {-1, 9};
        int[] result = BasicOperators.findMinMax(arr);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, expected, "MinMax mismatch");
        sa.assertAll();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindMinMaxEmpty() {
        BasicOperators.findMinMax(new int[]{});
    }

    @Test
    public void testMultiplicationTable() {
        int[][] result = BasicOperators.multiplicationTable(3);
        int[][] expected = {
                {1,2,3},
                {2,4,6},
                {3,6,9}
        };
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, expected, "Table mismatch");
        sa.assertAll();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMultiplicationTableInvalid() {
        BasicOperators.multiplicationTable(0);
    }

    @Test
    public void testEvenNumbersUpToN() {
        int[] result = BasicOperators.evenNumbersUpToN(10);
        int[] expected = {2,4,6,8,10};
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, expected, "Even numbers mismatch");
        sa.assertAll();
    }

    @DataProvider(name = "primeData")
    public Object[][] primeData() {
        return new Object[][] {
                {2, true},
                {17, true},
                {18, false}
        };
    }

    @Test(dataProvider = "primeData")
    public void testIsPrime(int n, boolean expected) {
        boolean result = BasicOperators.isPrime(n);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, expected, "Prime mismatch");
        sa.assertAll();
    }

    @Test
    public void testCountVowels() {
        int result = BasicOperators.countVowels("hello world");
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, 3, "Vowel count mismatch");
        sa.assertAll();
    }

    @Test
    public void testFibonacci() {
        int[] result = BasicOperators.fibonacci(5);
        int[] expected = {0,1,1,2,3};
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, expected, "Fibonacci mismatch");
        sa.assertAll();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFibonacciNegative() {
        BasicOperators.fibonacci(-1);
    }

    @Test
    public void testTranspose() {
        int[][] matrix = {{1,2,3},{4,5,6}};
        int[][] expected = {{1,4},{2,5},{3,6}};
        int[][] result = BasicOperators.transpose(matrix);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, expected, "Transpose mismatch");
        sa.assertAll();
    }

    @Test
    public void testSortArray() {
        int[] arr = {5,3,8,1};
        int[] expected = {1,3,5,8};
        int[] result = BasicOperators.sortArray(arr);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, expected, "Sort mismatch");
        sa.assertNotSame(result, arr, "Should return copy");
        sa.assertAll();
    }
}
