/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
        Palindrome palindrome = new Palindrome();

        for (int i = 0; i < 26; i++) {
            In in = new In("data/words.txt");
            System.out.printf("Total number of palindromes(off by %d): ", i);
            System.out.println(PalindromeOffByN(in, minLength, palindrome, i));
        }
    }

    /** Returns the number of words in the file which are palindromes according to the off-by-N rule.
     * @param file word text file
     * @param minLength the minimum length of words
     * @param palindrome a Palindrome instance
     * @param offByN off by number
     * @return total number of palindromes
     */
    public static int PalindromeOffByN(In file, int minLength, Palindrome palindrome, int offByN) {
        int totalPalindromes = 0;
        OffByN cc = new OffByN(offByN);
        while (!file.isEmpty()) {
            String word = file.readString();
            if (word.length() >= minLength && palindrome.isPalindrome(word, cc)) {
                totalPalindromes += 1;
            }
        }
        return totalPalindromes;
    }
}