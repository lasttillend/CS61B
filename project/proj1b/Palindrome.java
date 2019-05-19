public class Palindrome {

    /** Returns a deque where the characters appear in the same order
     * as in the string.
     */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordDeque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    /** Returns true if the word is a palindrome. */
    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindromeHelper(wordDeque);

    }

    private boolean isPalindromeHelper(Deque<Character> d) {
        if (d.size() == 0 || d.size() == 1) {
            return true;
        }
        char front = d.removeFirst();
        char back = d.removeLast();
        if (front != back) {
            return false;
        }
        return isPalindromeHelper(d);
    }

    /** A generalized palindrome checker.
     * @param word a string to be tested
     * @param cc a character compactor
     * @return true if word is a palindrome according to the character comparison
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindromeHelper(wordDeque, cc);
    }

    private boolean isPalindromeHelper(Deque<Character> d, CharacterComparator cc) {
        if (d.size() == 0 || d.size() == 1) {
            return true;
        }
        char front = d.removeFirst();
        char back = d.removeLast();
        if (!cc.equalChars(front, back)) {
            return false;
        }
        return isPalindromeHelper(d, cc);
    }
}
