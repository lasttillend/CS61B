import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int INITIAL_SIZE = 16;
    private static final double LOAD_FACTOR = 0.75;

    private int n;                          // number of key-value pairs
    private int m;                          // number of buckets
    private double loadFactor;              // threshold for resizing
    private BucketEntity<K, V>[] buckets;   // array of linked-list symbol tables

    private class BucketEntity<K, V> {
        private K key;
        private V value;
        private BucketEntity<K, V> next;

        public BucketEntity(int hashCode, K key, V value, BucketEntity<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public BucketEntity<K, V> getNext() {
            return next;
        }

        public void setNext(BucketEntity<K, V> next) {
            this.next = next;
        }
    }

    public MyHashMap() {
        buckets = new BucketEntity[INITIAL_SIZE];
        n = 0;
        m = INITIAL_SIZE;
        loadFactor = LOAD_FACTOR;
    }

    public MyHashMap(int initialSize) {
        buckets = new BucketEntity[initialSize];
        n = 0;
        m = initialSize;
        loadFactor = LOAD_FACTOR;
    }

    public MyHashMap(int initialSize, double loadFactor) {
        buckets = new BucketEntity[initialSize];
        n = 0;
        m = initialSize;
        this.loadFactor = loadFactor;
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        n = 0;
        buckets = new BucketEntity[m];
    }

    /**
     * Returns true if and only if this dictionary contains KEY as the key
     * of some key-value pair.
     */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns the value corresponding to KEY or null if no such value exists.
     */
    @Override
    public V get(K key) {
        int bucketNum = hash(key, m);
        BucketEntity<K, V> entity = get(bucketNum, key);
        return (entity == null) ? null : entity.getValue();
    }

    // Returns the index of key in buckets
    private int hash(K key, int numOfBuckets) {
        return (key.hashCode() & 0x7fffffff) % numOfBuckets;
    }

    private BucketEntity<K, V> get(int bucketNum, K key) {
        BucketEntity<K, V> entity = buckets[bucketNum];
        while (entity != null) {
            if (entity.getKey().equals(key)) {
                return entity;
            }
            entity = entity.getNext();
        }
        return null;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return n;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        int bucketNum = hash(key, m);
        BucketEntity<K, V> entity = buckets[bucketNum];
        while (entity != null) {
            if (entity.getKey().equals(key)) {
                entity.setValue(value);
                return;
            }
            entity = entity.getNext();
        }
        // if key does not exist before
        put(bucketNum, key, value);
    }

    private void put(int bucketNum, K key, V value) {
        BucketEntity<K, V> entity = new BucketEntity<>(bucketNum, key, value, buckets[bucketNum]);
        buckets[bucketNum] = entity;
        n += 1;
        if (n / m >= loadFactor) {
            resize(m * 2);
        }
    }

    private void resize(int capacity) {
        BucketEntity<K, V>[] newBuckets = new BucketEntity[capacity];
        for (int i = 0; i < m; i++) {
            BucketEntity<K, V> entity = buckets[i];
            while (entity != null) {
                BucketEntity<K, V> oldNext = entity.getNext();
                int newBucketNum = hash(entity.getKey(), capacity);
                entity.setNext(newBuckets[newBucketNum]);
                newBuckets[newBucketNum] = entity;
                entity = oldNext;
            }
        }
        buckets = newBuckets;
        m = capacity;
    }

    /** Returns a set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> allKeys = new HashSet<>();
        for (int i = 0; i < m; i++) {
            BucketEntity<K, V> entity = buckets[i];
            while (entity != null) {
                allKeys.add(entity.getKey());
                entity = entity.getNext();
            }
        }
        return allKeys;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    /**
     * Removes the mapping for the specified key from this map if presents.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        int bucketNum = hash(key, m);
        return remove(bucketNum, key);
    }

    private V remove(int bucketNum, K key) {
        BucketEntity<K, V> entity = buckets[bucketNum];
        BucketEntity<K, V> nextEntity = entity.getNext();
        if (entity.getKey().equals(key)) {
            V toRemove = entity.getValue();
            buckets[bucketNum] = nextEntity;
            n -= 1;
            return toRemove;
        } else {
            while (!nextEntity.getKey().equals(key)) {
                entity = entity.getNext();
                nextEntity = nextEntity.getNext();
            }
            V toRemove = nextEntity.getValue();
            entity.setNext(nextEntity.getNext());
            n -= 1;
            return toRemove;
        }
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        if (get(key) != value) {
            throw new IllegalArgumentException("argument to remove is not currently mapped to the specified value");
        }
        return remove(key);
    }
}
