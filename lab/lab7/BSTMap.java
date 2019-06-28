import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;  // root of BST

    private class Node {
        private K key;
        private V value;
        private Node left, right;
        private int size;

        public Node(K k, V v) {
            key = k;
            value = v;
            size = 1;
        }
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
    }

    /** Returns true if and only if this dictionary contains KEY as the
     *  key of some key-value pair. */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /** Returns the value corresponding to KEY or null if no such value exists. */
    @Override
    public V get(K key) {
        return get(root, key);
    }

    /* Get helper function */
    private V get(Node x, K key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x.value;
        }

    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size(root);
    }

    /* size helper function */
    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }


    /** Inserts the key-value pair of KEY and VALUE into this dictionary,
     *  replacing the previous value associated to KEY, if any. */
    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    /* Put helper function */
    private Node put(Node x, K key, V value) {
        if (x == null) {
            return new Node(key, value);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, value);
        } else if (cmp > 0) {
            x.right = put(x.right, key, value);
        } else {
            x.value = value;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /** Prints out the keys of this map in increasing order */
    public void printInOrder() {
        printInOrder(root);
    }

    /* PrintInOrder helper function */
    private void printInOrder(Node x) {
        if (x == null) {
            return;
        }
        printInOrder(x.left);
        printNode(x);
        printInOrder(x.right);
    }

    /* Another PrintInOrder helper function */
    private void printNode(Node x) {
        System.out.print(x.key);
        System.out.println(" " + x.value);
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
