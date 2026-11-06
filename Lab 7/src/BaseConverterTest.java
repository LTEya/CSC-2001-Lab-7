import org.junit.jupiter.api.Test;

import java.util.BitSet;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for BaseConverter.
 *
 * These starter tests (with these exact names) should all pass by the
 * time you're done. They are NOT enough on their own - add your own
 * tests (see the TODO list at the bottom and the lab handout).
 */
public class BaseConverterTest {

    // ---- starter tests ----

    @Test
    void toDecimalLectureExamples() {
        assertEquals(45, BaseConverter.toDecimal("101101", 2));
        assertEquals(45, BaseConverter.toDecimal("55", 8));
        assertEquals(45, BaseConverter.toDecimal("2D", 16));
        assertEquals(45, BaseConverter.toDecimal("1200", 3));
        assertEquals(4725, BaseConverter.toDecimal("4725", 10));
    }

    @Test
    void toBaseLectureExamples() {
        assertEquals("101101", BaseConverter.toBase(45, 2));
        assertEquals("55", BaseConverter.toBase(45, 8));
        assertEquals("2D", BaseConverter.toBase(45, 16));
        assertEquals("1200", BaseConverter.toBase(45, 3));
        assertEquals("45", BaseConverter.toBase(45, 10));
    }

    @Test
    void countDigitsLectureExamples() {
        assertEquals(4, BaseConverter.countDigits(4725, 10));
        assertEquals(6, BaseConverter.countDigits(45, 2));
        assertEquals(2, BaseConverter.countDigits(45, 16));
    }

    // TODO: add your own tests. At a minimum, cover:
    //   - digitValue: digits, upper- and lowercase letters, and characters that
    //     are not digits (including the ones just before/after '0'-'9', 'A'-'Z', 'a'-'z')
    //   - toDecimal: lowercase letters, "0" and leading zeros, base 2 and base 36,
    //     Integer.MAX_VALUE in a few bases
    //   - toDecimal throws IllegalArgumentException for: a digit too big for the
    //     base ("129" in base 8), a non-digit character ("-45", "4 5"), null,
    //     "", a bad base (1 or 37), and a value larger than Integer.MAX_VALUE
    //   - digitChar: 0, 9, 10, 35, and -1 / 36 throwing
    //   - toBase and toBaseRec: 0, single digits, powers of the base,
    //     Integer.MAX_VALUE, uppercase letters, negative n and bad bases throwing
    //   - countDigits: 0, boundaries like 9/10 and 99/100, 1024 in base 2,
    //     negative n and bad bases throwing
    //   - a loop over many values of n and every base 2-36 checking that
    //     toDecimal(toBase(n, base), base) == n, and that your answers match
    //     Integer.toString(n, base).toUpperCase()

    @Test
    void digitValueTest() {
        assertEquals(5, BaseConverter.digitValue('5'));
        assertEquals(10, BaseConverter.digitValue('a'));
        assertEquals(19, BaseConverter.digitValue('J'));
        assertEquals(-1,BaseConverter.digitValue('/'));
        assertEquals(-1,BaseConverter.digitValue('#'));
        assertEquals(-1,BaseConverter.digitValue('('));
    }

    @Test
    void toDecimalTest(){
        assertEquals(226, BaseConverter.toDecimal("6a", 36));
        assertEquals(5, BaseConverter.toDecimal("101", 2));
        assertEquals(0, BaseConverter.toDecimal("0", 10));
        assertEquals(21, BaseConverter.toDecimal("0010101",2));
        assertEquals(226, BaseConverter.toDecimal("6A",36));
        assertEquals(Integer.MAX_VALUE, BaseConverter.toDecimal("2147483647", 10));
        assertEquals(Integer.MAX_VALUE, BaseConverter.toDecimal("7FFFFFFF", 16));
        assertEquals(Integer.MAX_VALUE, BaseConverter.toDecimal("17777777777",8));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("", 1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal(null,36));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("80000000", 16));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("129", 8));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("-45", 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("4 5", 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal(null, 2));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("100", 1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("100", 37));
    }

    @Test
    void digitCharTest(){
        assertEquals('0', BaseConverter.digitChar(0));
        assertEquals('Z', BaseConverter.digitChar(35));
        assertEquals('A', BaseConverter.digitChar(10));
        assertEquals('9', BaseConverter.digitChar(9));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.digitChar(-1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.digitChar(36));
    }

    @Test
    void toBaseTest(){
        assertEquals("0", BaseConverter.toBase(0, 3));
        assertEquals("5", BaseConverter.toBase(5,10));
        assertEquals("100", BaseConverter.toBase(100,10));
        assertEquals("1A", BaseConverter.toBase(26, 16));
        assertEquals("7FFFFFFF", BaseConverter.toBase(Integer.MAX_VALUE, 16));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBase(-1,10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBase(10,1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBase(10, 37));
    }

    @Test
    void toBaseRecTest(){
        assertEquals("0", BaseConverter.toBaseRec(0, 2));
        assertEquals("5", BaseConverter.toBaseRec(5, 10));
        assertEquals("100", BaseConverter.toBaseRec(256, 16));
        assertEquals("1A", BaseConverter.toBaseRec(26, 16));
        assertEquals("2147483647", BaseConverter.toBaseRec(Integer.MAX_VALUE, 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBaseRec(-1,10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBaseRec(10,1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBaseRec(10,1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBaseRec(10,37));
    }

    @Test
    void countDigitsTest(){
        assertEquals(1, BaseConverter.countDigits(0,10));
        assertEquals(2, BaseConverter.countDigits(10, 10));
        assertEquals(11, BaseConverter.countDigits(1024, 2));
        assertEquals(1, BaseConverter.countDigits(9, 10));
        assertEquals(2, BaseConverter.countDigits(10, 10));
        assertEquals(2, BaseConverter.countDigits(99, 10));
        assertEquals(3, BaseConverter.countDigits(100, 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.countDigits(-1, 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.countDigits(10, 1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.countDigits(10, 37));
    }

    @Test
    void conversionLoopTest(){
        for (int base = 2; base <= 36; base++){
            for (int n = 0; n <= 999; n++){
                String expected = Integer.toString(n,base).toUpperCase();
                String converted = BaseConverter.toBase(n,base);
                assertEquals(n, BaseConverter.toDecimal(converted,base));
                assertEquals(expected, converted);
                assertEquals(expected, BaseConverter.toBaseRec(n, base));
            }
        }
    }
}
