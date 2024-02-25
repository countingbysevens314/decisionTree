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

    String targetAttribute = "foodType"; // TODO: replace with your own target attribute

    List<Row>  dataObjects;
    List<String> attributeList;
    
    Dataset trainingEmpty;

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

        // 1-item csv testing
        /*
        List<Row> dataObjects1 = DecisionTreeCSVParser.parse(this.trainingPath);
        List<String> attributeList1 = new ArrayList<>(dataObjects1.get(0).getAttributes());
        this.training = new Dataset(attributeList1, dataObjects1, AttributeSelection.ASCENDING_ALPHABETICAL);
         */

        //empty.csv
        /*
        List<Row> emptyDataObjects = DecisionTreeCSVParser.parse(this.trainingPathEmpty);
        List<String> attributeListEmpty = new ArrayList<>(emptyDataObjects.get(0).getAttributes());
        this.trainingEmpty = new Dataset(attributeListEmpty, emptyDataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);
         */
//        // builds a TreeGenerator object and generates a tree for "foodType"
        this.testGenerator = new TreeGenerator();
//        TODO: Uncomment this once you've implemented generateTree
//        this.testGenerator.generateTree(training, this.targetAttribute);
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
        //Assert.assertEquals(0, this.trainingEmpty.size());
    }

    /**
     * TO FINISH: testing for empty
     * testing Dataset method size() for an empty dataset
     *      @Test (expected = RuntimeException.class)
     *      public void testEmptyDataSet() { };
     *
     */

    /**
     * testing Dataset method getAttributeToSplitOn()
     */
    @Test
    public void testGetAttributeToSplitOn() {
        Assert.assertEquals("calories", this.fruitAscending.getAttributeToSplitOn());
        Assert.assertEquals("highProtein", this.fruitDescending.getAttributeToSplitOn());
        // how to test random
        // Assert.assertEquals("calories", this.fruitRandom.getAttributeToSplitOn());
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
//        Assert.assertEquals("fruit", this.testGenerator.getDecision(tangerine));
    }

}
