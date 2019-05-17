import org.junit.Test;
import static org.junit.Assert.*;

public class FlikTest {

    /** Test of isSameNumber method in the Flik library. */
    @Test
    public void testIsSameNumber() {
        assertTrue(Flik.isSameNumber(127, 127));
        assertTrue(Flik.isSameNumber(128, 128));
        assertTrue(Flik.isSameNumber(-1, -1));
        assertFalse(Flik.isSameNumber(0, 1));
        assertFalse(Flik.isSameNumber(0, -1));
        assertFalse(Flik.isSameNumber(1, -1));
    }

}
