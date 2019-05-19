import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    @Test
    public void testEqualChars() {
        OffByN cc0 = new OffByN(0);
        assertTrue(cc0.equalChars('a', 'a'));
        assertTrue(cc0.equalChars('&', '&'));
        assertTrue(cc0.equalChars('r', 'r'));
        assertFalse(cc0.equalChars('b', 'a'));
        assertFalse(cc0.equalChars('a', 'A'));

        OffByN cc1 = new OffByN(1);
        assertTrue(cc1.equalChars('b', 'a'));
        assertTrue(cc1.equalChars('&', '%'));
        assertTrue(cc1.equalChars('r', 'q'));
        assertFalse(cc1.equalChars('a', 'z'));
        assertFalse(cc1.equalChars('a', 'B'));


        OffByN cc2 = new OffByN(2);
        assertTrue(cc2.equalChars('a', 'c'));
        assertTrue(cc2.equalChars('%', '\''));
        assertTrue(cc2.equalChars('&', '$'));
        assertFalse(cc2.equalChars('a', 'b'));
        assertFalse(cc2.equalChars('a', 'a'));
        assertFalse(cc2.equalChars('a', 'A'));
        assertFalse(cc2.equalChars('a', 'z'));

        OffByN cc5 = new OffByN(5);
        assertTrue(cc5.equalChars('a', 'f'));
        assertTrue(cc5.equalChars('f', 'a'));
        assertFalse(cc5.equalChars('f', 'h'));
    }
}
