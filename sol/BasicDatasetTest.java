package sol;

import org.junit.Assert;
import org.junit.Test;
import src.AttributeSelection;
import src.DecisionTreeCSVParser;
import src.Row;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import javax.xml.crypto.Data;

/**
 * A class to test basic decision tree functionality on a basic training dataset
 */
public class BasicDatasetTest {
    // IMPORTANT: for this filepath to work, make sure the project is open as the top-level directory in IntelliJ
    // (See the first yellow information box in the handout testing section for details)
    String trainingPathFruit = "data/fruits-and-vegetables.csv";
    String trainingPathEmpty = "data/empty.csv";
    String trainingPathBird = "data/bird.csv";

    String targetAttribute = "isBird";

    List<Row>  dataObjects;
    List<String> attributeList;
    
    Dataset empty;

    // bird.csv
    Dataset birdAscending;
    Dataset birdDescending;
    Dataset birdRandom;

    Dataset fruitAscending;
    Dataset fruitDescending;
    Dataset fruitRandom;

    TreeGenerator testGenerator;

    /**
     * Constructs the decision tree for testing based on the input file and the target attribute.
     */
    @Before
    public void buildTreeForTest() {
        // fruits-and-vegetables testing (selection type: ascending alphabetical)
        this.dataObjects = DecisionTreeCSVParser.parse(this.trainingPathFruit);
        this.attributeList = new ArrayList<>(dataObjects.get(0).getAttributes());
        this.fruitAscending = new Dataset(attributeList, dataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);

        // fruits-and-vegetables testing (selection type: descending alphabetical)
        this.fruitDescending = new Dataset(attributeList, dataObjects, AttributeSelection.DESCENDING_ALPHABETICAL);

        // fruits-and-vegetables testing (selection type: random)
        this.fruitRandom = new Dataset(attributeList, dataObjects, AttributeSelection.RANDOM);

        // bird testing
        List<Row> birdDataObjects = DecisionTreeCSVParser.parse(this.trainingPathBird);
        List<String> birdAttributeList = new ArrayList<>(birdDataObjects.get(0).getAttributes());
        this.birdAscending = new Dataset(birdAttributeList, birdDataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);
        this.birdRandom = new Dataset(birdAttributeList, birdDataObjects, AttributeSelection.RANDOM);
        this.birdDescending = new Dataset(birdAttributeList, birdDataObjects, AttributeSelection.DESCENDING_ALPHABETICAL);

        //empty case
        this.empty = new Dataset(new ArrayList<>(), new ArrayList<>(), AttributeSelection.ASCENDING_ALPHABETICAL);

        // builds a TreeGenerator object and generates a tree for "foodType"
        this.testGenerator = new TreeGenerator();
        //this.testGenerator.generateTree(this.fruitAscending, "foodType");
        this.testGenerator.generateTree(this.birdAscending, "isBird");
        //this.testGenerator.generateTree(this.fruitDescending, "foodType");
        //this.testGenerator.generateTree(this.fruitRandom, "foodType");
    }

    /**
     * testing for method getAttributeList()
     */
    @Test
    public void testGetAttributeList(){
        Assert.assertEquals(this.attributeList, this.fruitAscending.getAttributeList());
        Assert.assertEquals(this.attributeList, this.fruitDescending.getAttributeList());
        Assert.assertEquals(this.attributeList, this.fruitRandom.getAttributeList());
    }

    /**
     * testing for Dataset method getDataObject()
     */
    @Test
    public void testGetDataObject(){
        Assert.assertEquals(this.dataObjects, this.fruitAscending.getDataObjects());
        Assert.assertEquals(this.dataObjects, this.fruitDescending.getDataObjects());
        Assert.assertEquals(this.dataObjects, this.fruitRandom.getDataObjects());
    }

    /**
     * testing for Dataset method getSelectionType()
     */
    @Test
    public void testGetSelectionType(){
        Assert.assertEquals(AttributeSelection.ASCENDING_ALPHABETICAL, this.fruitAscending.getSelectionType());
        Assert.assertEquals(AttributeSelection.DESCENDING_ALPHABETICAL, this.fruitDescending.getSelectionType());
        Assert.assertEquals(AttributeSelection.RANDOM, this.fruitRandom.getSelectionType());
    }

    /**
     * testing Dataset method size();
     */
    @Test
    public void testDatasetSize() {
        Assert.assertEquals(7, this.fruitAscending.size());
        Assert.assertEquals(0, this.empty.size());
    }

    /**
     * testing Dataset method getAttributeToSplitOn()
     */
    @Test
    public void testGetAttributeToSplitOn() {
        Assert.assertEquals("calories", this.fruitAscending.getAttributeToSplitOn());
        Assert.assertEquals("highProtein", this.fruitDescending.getAttributeToSplitOn());
        // random case: test if returned attribute is in the list
        Assert.assertTrue(this.attributeList.contains(this.fruitRandom.getAttributeToSplitOn()));
    }

    /**
     * testing for partition();
     */
    @Test
    public void testPartition(){
        List<Dataset> birdByCanFly = new ArrayList<>();
        List<Row> birdTrue = DecisionTreeCSVParser.parse("data/bird-true.csv");
        List<Row> birdFalse = DecisionTreeCSVParser.parse("data/bird-false.csv");

        birdByCanFly.add(new Dataset(new ArrayList<>(),birdTrue,AttributeSelection.ASCENDING_ALPHABETICAL));
        birdByCanFly.add(new Dataset(new ArrayList<>(),birdFalse,AttributeSelection.ASCENDING_ALPHABETICAL));

        //Assert.assertEquals(birdByCanFly, this.birdAscending.partition("canFly"));
    }

    /**
     * testing for getLeafDecision();
     */
    @Test
    public void testGetDefault() {
        Assert.assertEquals("TRUE", this.birdAscending.getDefault("canFly"));
        Assert.assertEquals("TRUE", this.birdDescending.getDefault("canFly"));
        Assert.assertEquals("vegetable", this.fruitAscending.getDefault("foodType"));
    }

    /**
     * testing for getLeafDecision();
     */
    @Test
    public void testGetLeafDecision() {
        List<Row> dataObjectsBird = DecisionTreeCSVParser.parse("data/bird-same-outcome.csv");
        Dataset birdSameOutcome = new Dataset(new ArrayList<>(),
                dataObjectsBird, AttributeSelection.ASCENDING_ALPHABETICAL);
        Assert.assertEquals("yes", birdSameOutcome.getLeafDecision("isBird"));
    }

    /**
     * testing for removeTarget()
     */
    @Test
    public void testRemoveTarget() {
        // check to see it works
        List<String> birdRemovedAttribute = new ArrayList<>();
        birdRemovedAttribute.add("canFly");
        Dataset birdRemoved = new Dataset(birdRemovedAttribute, this.birdAscending.getDataObjects(),
                AttributeSelection.ASCENDING_ALPHABETICAL);
        //System.out.println(this.birdAscending.removeTarget("isBird"));
        //Assert.assertEquals(birdRemoved,this.birdAscending.removeTarget("isBird"));
    }

    /**
     * Tests the expected classification of the "tangerine" row is a fruit
     */
    @Test
    public void testClassification() {
        // makes a new (partial) Row representing the tangerine from the example
        // TODO: make your own rows based on your dataset
        Row tangerine = new Row("test row (tangerine)");
        tangerine.setAttributeValue("color", "orange");
        tangerine.setAttributeValue("highProtein", "false");
        tangerine.setAttributeValue("calories", "high");
        // TODO: make your own assertions based on the expected classifications
        // TODO: Uncomment this once you've implemented getDecision
        Assert.assertEquals("fruit", this.testGenerator.getDecision(tangerine));
    }

}
