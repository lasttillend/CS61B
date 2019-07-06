import java.util.*;

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

    private void printInOrder(Node x) {
        if (x == null) {
            return;
        }
        printInOrder(x.left);
        printNode(x);
        printInOrder(x.right);
    }

    private void printNode(Node x) {
        System.out.print(x.key);
        System.out.println(" " + x.value);
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        if (root == null) {
            return null;
        }
        Set<K> keys = new HashSet<K>();
        inOrder(root, keys);
        return keys;
    }

    /* Adds keys rooted in node x to keySet. */
    private void inOrder(Node x, Set keySet) {
        if (x == null) {
            return;
        }
        inOrder(x.left, keySet);
        keySet.add(x.key);
        inOrder(x.right, keySet);
    }

    /* Adds keys rooted in node x to keyList. */
    private void inOrder(Node x, List keyList) {
        if (x == null) {
            return;
        }
        inOrder(x.left, keyList);
        keyList.add(x.key);
        inOrder(x.right, keyList);
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIter();
    }

    /** An iterator that iterates over the keys of the dictionary. */
    private class BSTMapIter implements Iterator<K> {
        private List<K> keys;
        private int cnt;

        public BSTMapIter() {
            cnt = 0;
            keys = new ArrayList<>();
            inOrder(root, keys);
        }

        @Override
        public boolean hasNext() {
            return cnt < keys.size();
        }

        @Override
        public K next() {
            return keys.get(cnt++);
        }
    }

    /** Removes the mapping for the specified key from this map if present. */
    @Override
    public V remove(K key) {
        V ret = get(key);
        root = remove(root, key);
        return ret;
    }

    private Node remove(Node x, K key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = remove(x.left, key);
        } else if (cmp > 0) {
            x.right = remove(x.right, key);
        } else {
            if (x.right == null) {
                return x.left;
            }
            if (x.left == null) {
                return x.right;
            }
            Node t = x;
            x = min(t.right);
            x.right = removeMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;

    }

    /* Returns the smallest key rooted at node x */
    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    /* Removes the smallest key and associated value rooted at node x. */
    private Node removeMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = removeMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /** Removes the entry for the specified key only if it is currently mapped to
     * the specified value.
     */
    @Override
    public V remove(K key, V value) {
        if (get(key) != value) {
            throw new IllegalArgumentException("argument to remove is not currently mapped to the specified value");
        }
        return remove(key);
    }
}
