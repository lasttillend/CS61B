package bearmaps.proj2ab;

import java.util.LinkedList;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private static final int R = 128;    // ASCII
    private Node root;                   // root of trie

    private class Node {
        private boolean isKey;
        private Node[] next;

        Node(boolean isKey) {
            this.isKey = isKey;
            next = new Node[R];
        }

        void setKey(boolean b) {
            isKey = b;
        }

        boolean isKey() {
            return isKey;
        }
    }

    /**
     * Initializes an empty trie set.
     */
    public MyTrieSet() {
        root = new Node(false);
    }

    /**
     * Inserts string KEY into trie.
     */
    @Override
    public void add(String key) {
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) {
            x = new Node(false);
        }
        if (d == key.length()) {
            x.setKey(true);
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = add(x.next[c], key, d + 1);
        return x;
    }

    /**
     * Returns true if the Trie contains KEY, false otherwise.
     */
    @Override
    public boolean contains(String key) {
        Node x = find(root, key, 0);
        return x != null && x.isKey();
    }

    private Node find(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            return x;
        }
        char c = key.charAt(d);
        return find(x.next[c], key, d + 1);
    }

    /**
     * Clears all items out of Trie.
     */
    @Override
    public void clear() {
        root = new Node(false);
    }

    /**
     * Returns a list of all words that start with PREFIX.
     */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> results = new LinkedList<>();
        Node x = find(root, prefix, 0);  // find the end node of the prefix
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, List<String> results) {
        if (x == null) {
            return;
        }
        if (x.isKey()) {
            results.add(prefix.toString());
        }
        for (char c = 0; c < R; c++) {
            prefix.append(c);
            collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
        int length = longestPrefixOf(root, key, 0, -1);
        if (length == -1) {
            return null;
        }
        return key.substring(0, length);
    }

    private int longestPrefixOf(Node x, String key, int d, int length) {
        if (x == null) {
            return length;
        }
        if (x.isKey()) {
            length = d;
        }
        if (d == key.length()) {
            return length;
        }
        char c = key.charAt(d);
        return longestPrefixOf(x.next[c], key, d + 1, length);
    }
}
