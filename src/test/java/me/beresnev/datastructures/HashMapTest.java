package me.beresnev.datastructures;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 28.02.17.
 */
public class HashMapTest {

    // TODO: More tests
    private HashMap<String, String> map = new HashMap<>();

    @Test
    public void addTest() {
        map.put("Hello", "Map");
        Assert.assertEquals(map.get("Hello"), "Map");
    }

    @Test
    public void overriteTest() {
        map.put("World", "is my oyster");
        map.put("World", "How are you");
        Assert.assertEquals(map.get("World"), "How are you");
    }

    @Test
    public void returnOverride() {
        map.put("World", "is my oyster");
        String oldValue = map.put("World", "How are you");
        Assert.assertEquals(oldValue, "is my oyster");
    }

    @Test
    public void removeTest() {
        map.put("My oyster", "is the world");
        Assert.assertEquals(map.get("My oyster"), "is the world");
        Assert.assertEquals(map.size(), 1);
        Assert.assertEquals(map.get("My oyster"), "is the world");
        map.remove("My oyster");
        Assert.assertEquals(map.get("My oyster"), null);
        Assert.assertEquals(map.size(), 0);
    }

    @Test
    public void containsTest() {
        map.put("unique1", "hi");
        Assert.assertTrue(map.contains("unique1"));
        Assert.assertFalse(map.contains("unique4"));
    }

    @Test
    public void sizeTest() {
        HashMap<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, "hi");
        }
        Assert.assertEquals(10, map.size());
    }

    @Test
    public void oneResizeTest() {
        HashMap<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            map.put(i, "hi" + i);
        }
        Assert.assertEquals(map.size(), 20);
        Assert.assertEquals("hi19", map.get(19));
    }

    @Test
    public void manyResizeTest() {
        HashMap<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 90; i++) {
            map.put(i, "hi" + i);
        }
        Assert.assertEquals(map.size(), 90);
        Assert.assertEquals("hi89", map.get(89));
    }
}