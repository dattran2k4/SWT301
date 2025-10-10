import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dattq.example.Calculator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

public class CaculatorTest {

    static Calculator calculator;

    @BeforeAll
    static void initAll() {
        calculator = new Calculator();
    }

    @AfterAll
    static void cleanupAll() {
        calculator = null;
    }

    @ParameterizedTest(name = "Test {index} => {0} + {1} = {2}")
    @CsvFileSource(resources = "/dataplus.csv", numLinesToSkip = 1)
    @DisplayName("Test add calculation")
    void testAddition(int a, int b, int expected) {
        int actual = Calculator.add(a, b);
        assertEquals(expected, actual, () -> a + " + " + b + "should be " + expected);
    }

    @ParameterizedTest(name = "Test {index} => {0} / {1} = {2}")
    @CsvFileSource(resources = "/datadivide.csv", numLinesToSkip = 1)
    @DisplayName("Test divide calculation")
    void testDivide(int a, int b, int expected) {
        int actual = Calculator.divide(a, b);
        assertEquals(expected, actual, () -> a + " / " + b + "should be " + expected);
    }

    @Test
    void testDivideByZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Calculator.divide(10, 0);
        });

        assertEquals("Cannot divide by zero", exception.getMessage());
    }

    @ParameterizedTest(name = "Test {index} => {0} * {1} = {2}")
    @CsvFileSource(resources = "/datamulti.csv", numLinesToSkip = 1)
    @DisplayName("Test divide calculation")
    void testMultiplyFromFile(int a, int b, int expected) {
        int result = calculator.multiply(a, b);
        assertEquals(expected, result, () -> a + " * " + b + " should be " + expected);
    }

}
