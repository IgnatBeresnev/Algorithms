package me.beresnev.algorithms;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * @author Ignat Beresnev
 * @version 1.1
 * @since 26.02.17.
 */
public class DocumentDistanceTest {

    private File firstFile = new File("src/test/testResources/DD/FirstFile");
    private File secondFile = new File("src/test/testResources/DD/SecondFile");
    private File thirdFile = new File("src/test/testResources/DD/ThirdFile");
    private File fourthFile = new File("src/test/testResources/DD/FourthFile");
    private File fifthFile = new File("src/test/testResources/DD/fifthFile");

    @Test(expected = IllegalArgumentException.class)
    public void fileDoesntExist() {
        DocumentDistance.getAngle(firstFile, fifthFile);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sameFile() {
        DocumentDistance.getAngle(firstFile, firstFile);
    }

    @Test
    public void equalFiles() {
        double distance = DocumentDistance.getAngle(firstFile, secondFile);
        Assert.assertTrue(distance > 0 && distance < 0.005);
    }

    @Test
    public void unequalButSimilarFiles() { // 50/50 equal files
        double distance = DocumentDistance.getAngle(firstFile, thirdFile);
        Assert.assertTrue(distance > 0.5);
    }

    @Test
    public void completelyDifferentFiles() {
        double distance = DocumentDistance.getAngle(firstFile, fourthFile);
        Assert.assertTrue(distance > 1);
    }
}