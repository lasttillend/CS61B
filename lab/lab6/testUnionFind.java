import org.junit.Test;
import static org.junit.Assert.*;

public class testUnionFind {

    @Test
    public void test() {
        UnionFind ds = new UnionFind(9);
        assertFalse(ds.connected(2, 3));
        ds.union(2, 3);
        assertTrue(ds.connected(2, 3));
        ds.union(1, 2);
        ds.union(5, 7);
        ds.union(8, 4);
        ds.union(7, 2);
        assertEquals(3, ds.find(3));
        ds.union(0, 6);
        ds.union(6, 4);
        ds.union(6, 3);
        assertEquals(3, ds.find(8));
        assertEquals(3, ds.find(6));
    }
}
