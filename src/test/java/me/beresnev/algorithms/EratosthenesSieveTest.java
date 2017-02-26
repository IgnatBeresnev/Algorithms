package me.beresnev.algorithms;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 26.02.17.
 */
public class EratosthenesSieveTest {

    @SuppressFBWarnings("DM_DEFAULT_ENCODING")
    @Test
    public void primesTest() throws IOException {
        File file = new File("src/test/testResources/PrimeNumbers"); // primes from 1 to 4000
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        String[] numbers;
        List<Integer> primes = new LinkedList<>();
        while ((line = reader.readLine()) != null) {
            numbers = line.split("\\s+");
            for (String number : numbers) {
                primes.add(Integer.parseInt(number));
            }
        }
        reader.close();

        Integer[] primesArrayFromFile = primes.toArray(new Integer[primes.size()]);
        Integer[] eratosthenesSievePrimes = EratosthenesSieve.getPrimes(4000);
        Assert.assertArrayEquals(primesArrayFromFile, eratosthenesSievePrimes);
    }

    @Test
    public void negativeParam() {
        Integer[] eratosthenesSievePrimes = EratosthenesSieve.getPrimes(-300);
        Assert.assertEquals(eratosthenesSievePrimes, null);
    }
}