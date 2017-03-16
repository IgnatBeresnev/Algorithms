package me.beresnev.algorithms;

import me.beresnev.algorithms.math.BinaryPow;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 26.02.17.
 */
public class BinaryPowTest {
    @Test
    public void pow() {
        Random random = new Random();
        for (int i = 2; i < 50; i++) {
            int pow = random.nextInt(5);
            long myPow = BinaryPow.pow(i, pow);
            double mathPow = Math.pow((double) i, (double) pow);
            Assert.assertEquals(myPow, (int) mathPow);
        }
    }

    @Test
    public void negativePow() {
        long pow = BinaryPow.pow(5, -3);
        Assert.assertEquals(pow, 0);
    }

    @Test
    public void negativeNumber() {
        long pow = BinaryPow.pow(-5, 3);
        Assert.assertEquals(pow, -125);
    }

    @Test
    public void powEqualsZero() {
        long pow = BinaryPow.pow(5, 0);
        Assert.assertEquals(pow, 1);
    }
}