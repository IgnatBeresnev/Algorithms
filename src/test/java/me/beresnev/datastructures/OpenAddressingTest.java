package me.beresnev.datastructures;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 03.03.17.
 */
public class OpenAddressingTest {
    private OpenAddressing<String, String> map = new OpenAddressing<>();

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
    public void sizeTest() {
        OpenAddressing<Integer, String> map = new OpenAddressing<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, "hi");
        }
        Assert.assertEquals(10, map.size());
    }

    @Test
    public void oneResizeTest() {
        OpenAddressing<Integer, String> map = new OpenAddressing<>();
        for (int i = 0; i < 20; i++) {
            map.put(i, "hi" + i);
        }
        Assert.assertEquals(map.size(), 20);
        Assert.assertEquals("hi19", map.get(19));
    }

    @Test
    public void manyResizeTest() {
        OpenAddressing<String, String> map = new OpenAddressing<>();

        for (int i = 0; i < 30000; i++) {
            map.put("Hi" + i, "hi" + i);
        }
        Assert.assertEquals(map.size(), 30000);
        Assert.assertEquals("hi5000", map.get("Hi5000"));
        Assert.assertEquals(30000, map.size());
    }

}