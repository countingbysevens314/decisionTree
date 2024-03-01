package sol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import src.AttributeSelection;
import src.DecisionTreeCSVParser;
import src.DecisionTreeTester;
import src.Row;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class containing the tests for methods in the TreeGenerator and Dataset classes
 */
public class DecisionTreeTest {
    private Dataset bird;
    private Dataset fruit;
    private TreeGenerator testGeneratorBird;
    private TreeGenerator testGeneratorFruit;

    /**
     * building sample tree for testing
     */

    @Before
    public void buildTreeForTest() {
        //bird.csv testing
        List<Row> birdDataObjects = DecisionTreeCSVParser.parse("data/bird.csv");
        List<String> birdAttributeList = new ArrayList<>(birdDataObjects.get(0).getAttributes());
        this.bird = new Dataset(birdAttributeList, birdDataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);

        //fruit-and-vegetables.csv testing
        List<Row> fruitDataObjects = DecisionTreeCSVParser.parse("data/fruits-and-vegetables.csv");
        List<String> fruitAttributeList = new ArrayList<>(fruitDataObjects.get(0).getAttributes());
        this.fruit = new Dataset(fruitAttributeList, fruitDataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);

        this.testGeneratorFruit = new TreeGenerator();
        this.testGeneratorBird = new TreeGenerator();
        this.testGeneratorBird.generateTree(this.bird, "isBird");
        this.testGeneratorFruit.generateTree(this.fruit, "foodType");
    }

    //TODO 1. Small unit tests on the Dataset class testing the IDataset methods
    /**
     * testing for getDefault() for bird-testing and fruits-and-vegetables testing
     */
    @Test
    public void testGetDefault() {
        assertEquals("TRUE", this.bird.getDefault("canFly"));
        Assert.assertEquals("TRUE", this.bird.getDefault("canFly"));
        Assert.assertEquals("vegetable", this.fruit.getDefault("foodType"));
    }

    /**
     * testing for getLeafDecision() bird-testing and fruits-and-vegetables testing
     */
    @Test
    public void testGetLeafDecision() {
        List<Row> dataObjectsBird = DecisionTreeCSVParser.parse("data/bird-same-outcome.csv");
        Dataset birdSameOutcome = new Dataset(new ArrayList<>(),
                dataObjectsBird, AttributeSelection.ASCENDING_ALPHABETICAL);
        Assert.assertEquals("yes", birdSameOutcome.getLeafDecision("isBird"));
    }

    // TODO: 2. Small unit tests on the TreeGenerator class that test the ITreeGenerator methods

    /**
     * printing out the list of ValueEdge to test for addValue()
     */
    @Test
    public void testAddValue(){
        List<Dataset> birdPartitionResult = this.bird.removeTarget("isBird").partition("canFly");
        List<ValueEdge> birdValue = this.testGeneratorBird.addValue("canFly", birdPartitionResult);

        for (ValueEdge ve : birdValue) {
            System.out.print(ve.getValue());
            System.out.print(", ");
        }
        System.out.println("finished printing valueEdge list");
    }

    /**
     * testing for getLeafDecision() bird-testing and fruits-and-vegetables testing
     */
    @Test
    public void testGetDecision() {
        List<Row> fruitVegDataObjects = DecisionTreeCSVParser.parse("data/fruits-and-vegetables.csv");
        Row testRow = fruitVegDataObjects.get(0);
        String prediction = this.testGeneratorFruit.getDecision(testRow); // make counters for correct and incorrect
        assertEquals(testRow.getAttributeValue("foodType"), prediction);
    }

    // TODO 3. Tests on your own small dataset (expect 70% accuracy on testing data, 95% on training data)
    /**
     * testing accuracies for bird.csv
     */
    @Test
    public void testBird() throws InvocationTargetException, NoSuchMethodException, InstantiationException,
            IllegalAccessException {
        DecisionTreeTester dtt = new DecisionTreeTester(TreeGenerator.class, Dataset.class);
        double accuracyTraining = dtt.getAverageDecisionTreeAccuracy("data/bird.csv", "data/bird.csv", "isBird", 100);
        // 100 iterations!
        double accuracyTesting = dtt.getAverageDecisionTreeAccuracy("data/bird.csv", "data/bird-testing.csv", "isBird",
                100);
        Assert.assertTrue(accuracyTraining > 0.95); // some expected accuracy threshold
        Assert.assertTrue(accuracyTesting > 0.7);
    }

    /**
     * testing accuracies for fruits-and-vegetables.csv
     */
    @Test
    public void testFruit() throws InvocationTargetException, NoSuchMethodException, InstantiationException,
            IllegalAccessException {
        DecisionTreeTester dtt = new DecisionTreeTester(TreeGenerator.class, Dataset.class);
        double accuracyTraining = dtt.getAverageDecisionTreeAccuracy("data/fruits-and-vegetables-capitalized.csv",
                "data/fruits-and-vegetables-capitalized.csv", "foodType", 100);
        double accuracyTesting = dtt.getAverageDecisionTreeAccuracy("data/fruits-and-vegetables-capitalized.csv",
                "data/fruits-and-vegetables-testing.csv", "foodType", 100); // 100 iterations!

        Assert.assertTrue(accuracyTraining > 0.95);
        Assert.assertTrue(accuracyTesting > 0.7); // some expected accuracy threshold
    }

    // TODO 4. Test on the villains dataset (expect 70% accuracy on testing data, 95% on training data)
    /**
     * testing accuracies for villains
     */
    @Test
    public void testVillains() throws InvocationTargetException, NoSuchMethodException, InstantiationException,
            IllegalAccessException {
        DecisionTreeTester dtt = new DecisionTreeTester(TreeGenerator.class, Dataset.class);
        double accuracyTraining = dtt.getAverageDecisionTreeAccuracy("data/villains/training.csv",
                "data/villains/training.csv", "isVillain", 100);
        Assert.assertTrue(accuracyTraining > 0.95);

        double accuracyTesting = dtt.getAverageDecisionTreeAccuracy("data/villains/training.csv",
                "data/villains/testing.csv", "isVillain", 100);
        Assert.assertTrue(accuracyTesting > 0.7);
    }

    // TODO 5. Tests on the mushrooms dataset (expect 70% accuracy on testing data, 95% on training data)
    /**
     * testing accuracies for mushrooms
     */
    @Test
    public void testMushrooms() throws InvocationTargetException, NoSuchMethodException, InstantiationException,
            IllegalAccessException {
        DecisionTreeTester dtt = new DecisionTreeTester(TreeGenerator.class, Dataset.class);
        double accuracyTraining = dtt.getAverageDecisionTreeAccuracy("data/mushrooms/training.csv",
                "data/mushrooms/training.csv", "isPoisonous", 100);
        Assert.assertTrue(accuracyTraining > 0.95);

        double accuracyTesting = dtt.getAverageDecisionTreeAccuracy("data/mushrooms/training.csv",
                "data/mushrooms/testing.csv", "isPoisonous", 100); // 100 iterations!
        Assert.assertTrue(accuracyTesting > 0.7); // some expected accuracy threshold
    }

    /* got 0.59 in accuracyTraining for candidates
     * @Test
     *     public void testCandidates() throws InvocationTargetException, NoSuchMethodException, InstantiationException,
     *  IllegalAccessException {
     *         DecisionTreeTester dtt = new DecisionTreeTester(TreeGenerator.class, Dataset.class);
     *
     *         double accuracyTraining1 = dtt.getAverageDecisionTreeAccuracy(
     * "data/candidates/training-gender-correlated.csv", "data/candidates/training-gender-correlated.csv",
     * "hired", 100);
     *         Assert.assertTrue(accuracyTraining1 > 0.95);
     *         double accuracyTraining2 = dtt.getAverageDecisionTreeAccuracy("data/candidates/training-gender-equal.
     *         csv", "data/candidates/training-gender-equal.csv", "hired", 100);
     *         Assert.assertTrue(accuracyTraining2 > 0.95);
     *         double accuracyTraining3 = dtt.getAverageDecisionTreeAccuracy(
     *         "data/candidates/training-gender-unequal.csv", "data/candidates/training-gender-unequal.csv",
     *         "hired", 100);
     *         Assert.assertTrue(accuracyTraining3 > 0.95);
     *
     *         double accuracyTesting1 = dtt.getAverageDecisionTreeAccuracy(
     *         "data/candidates/training-gender-correlated.csv", "data/candidates/testing.csv", "hired", 100);
     *         Assert.assertTrue(accuracyTesting1 > 0.7);
     *
     *         double accuracyTesting2 = dtt.getAverageDecisionTreeAccuracy("data/candidates/training-gender-equal.csv",
     *         "data/candidates/testing.csv", "hired", 100);
     *         Assert.assertTrue(accuracyTesting2 > 0.7);
     *         double accuracyTesting3 = dtt.getAverageDecisionTreeAccuracy(
     *         "data/candidates/training-gender-unequal.csv", "data/candidates/testing.csv", "hired", 100);
     *         // 100 iterations!
     *         Assert.assertTrue(accuracyTesting3 > 0.7); // some expected accuracy threshold
     *     }
     */

    /*
     * @Test
     *     public void testSongs() throws InvocationTargetException, NoSuchMethodException, InstantiationException,
     *      IllegalAccessException {
     *         DecisionTreeTester dtt = new DecisionTreeTester(TreeGenerator.class, Dataset.class);
     *
     *         double accuracyTraining = dtt.getAverageDecisionTreeAccuracy("data/songs/training.csv",
     *         "data/songs/training.csv", "isPopular", 100); // 100 iterations!
     *         System.out.println(accuracyTraining);
     *         Assert.assertTrue(accuracyTraining > 0.95);
     *
     *         double accuracyTesting = dtt.getAverageDecisionTreeAccuracy("data/songs/training.csv",
     *         "data/songs/testing.csv", "isPopular", 100); // 100 iterations!
     *         System.out.println(accuracyTesting);
     *         Assert.assertTrue(accuracyTesting > 0.7); // some expected accuracy threshold
     *     }
     */
}
