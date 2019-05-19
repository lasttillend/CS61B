import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);

        Deque d2 = palindrome.wordToDeque("");
        String actual2 = "";
        for (int i = 0; i < "".length(); i++) {
            actual2 += d2.removeFirst();
        }
        assertEquals("", actual2);
    }

    @Test
    public void testIsPalindrome() {
//        assertTrue(palindrome.isPalindrome(""));
//        assertTrue(palindrome.isPalindrome("a"));
//        assertTrue(palindrome.isPalindrome("aa"));
//        assertTrue(palindrome.isPalindrome("noon"));
//        assertTrue(palindrome.isPalindrome("racecar"));
//        assertFalse(palindrome.isPalindrome("raCecar"));
//        assertFalse(palindrome.isPalindrome("ab"));
//        assertFalse(palindrome.isPalindrome("aab"));
//        assertFalse(palindrome.isPalindrome("horse"));

        // Tests for generalized palindrome(off-by-one case)
        CharacterComparator ccOffByOne = new OffByOne();
        assertTrue(palindrome.isPalindrome("", ccOffByOne));
        assertTrue(palindrome.isPalindrome("a", ccOffByOne));
        assertTrue(palindrome.isPalindrome("flake", ccOffByOne));
        assertTrue(palindrome.isPalindrome("%acb&", ccOffByOne));
        assertFalse(palindrome.isPalindrome("bob", ccOffByOne));
        assertFalse(palindrome.isPalindrome("Aa", ccOffByOne));
    }
}