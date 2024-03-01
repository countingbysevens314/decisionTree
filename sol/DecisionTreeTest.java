package sol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.Before;
import src.AttributeSelection;
import src.DecisionTreeCSVParser;
import src.Row;

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


    //TODO: Write more unit and system tests! Some basic guidelines that we will be looking for:
    //TODO: 1. Small unit tests on the Dataset class testing the IDataset methods

    /**
     * Constructs the decision tree for testing based on the input file and the target attribute.
     */
    @Before
    public void buildTreeForTest() {
        //bird testing
        List<Row> birdDataObjects = DecisionTreeCSVParser.parse("data/bird.csv");
        List<String> birdAttributeList = new ArrayList<>(birdDataObjects.get(0).getAttributes());
        this.bird = new Dataset(birdAttributeList, birdDataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);

        //fruit-and-vegetables
        List<Row> fruitDataObjects = DecisionTreeCSVParser.parse("data/fruits-and-vegetables.csv");
        List<String> fruitAttributeList = new ArrayList<>(fruitDataObjects.get(0).getAttributes());
        this.fruit = new Dataset(fruitAttributeList, fruitDataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);

        this.testGeneratorFruit = new TreeGenerator();
        this.testGeneratorBird = new TreeGenerator();
        this.testGeneratorBird.generateTree(this.bird, "isBird");
        this.testGeneratorFruit.generateTree(this.fruit, "foodType");
    }


    // TODO: 2. Small unit tests on the TreeGenerator class that test the ITreeGenerator methods
    // addValueTesting
    @Test
    public void testAddValue(){
        //this.bird.removeTarget("isBird");
        List<Dataset> birdPartitionResult = this.bird.removeTarget("isBird").partition("canFly");

        List<ValueEdge> birdValue = this.testGeneratorBird.addValue("canFly", birdPartitionResult);
        for (ValueEdge ve : birdValue) {
            System.out.print(ve.getValue());
            System.out.print(", ");
        }
        System.out.println("finished printing valueedge");

        //  this.testGenerator.addValue("color", fruitVegPartitionHighProtein);
        // for (ValueEdge ve: birdValue)
        List<Dataset> fruitVegPartitionHighProtein = this.fruit.partition("highProtein");

        List<Dataset> fruitVegPartitionColor = this.fruit.partition("color");

        List<Dataset> fruitVegPartitionCalories = this.fruit.partition("calories");



    }

    @Test
    public void testGetDecision() {
        List<Row> fruitVegDataObjects = DecisionTreeCSVParser.parse("data/fruits-and-vegetables.csv");
        Row testRow = fruitVegDataObjects.get(0);
        String prediction = this.testGeneratorFruit.getDecision(testRow); // make counters for correct and incorrect
        assertEquals(testRow.getAttributeValue("foodType"), prediction);

        //if (prediction.equals(datum.getAttributeValue(targetAttribute)))
        /**
         * public double getAverageDecisionTreeAccuracy(String trainingDataPath, String testingDataPath,
         *                                                  String targetAttribute, int numIterations)
         */
        // getAttributeValue
        // "feed" the row to the tree XXXXX
        // if the decision is the same as is anticipated for the row, increase the correct counter
        // otherwise increase the incorrect counter

        // Now have the counts for correct and incorrect.
        // "Success" means that these are in a desired range: ~70% for test data, 95% training
    }



    // 3. Tests on your own small dataset (expect 70% accuracy on testing data, 95% on training data)
    // 4. Test on the villains dataset (expect 70% accuracy on testing data, 95% on training data)
    // 5. Tests on the mushrooms dataset (expect 70% accuracy on testing data, 95% on training data)
    // Feel free to write more unit tests for your own helper methods -- more details can be found in the handout!

    //
}
