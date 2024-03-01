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
    List<Row>  dataObjects;
    List<String> attributeList;
    
    Dataset empty;

    // bird.csv
    Dataset birdAscending;
    Dataset birdDescending;
    Dataset birdRandom;

    // fruits-and-vegetables.csv
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
        this.dataObjects = DecisionTreeCSVParser.parse( "data/fruits-and-vegetables.csv");
        this.attributeList = new ArrayList<>(dataObjects.get(0).getAttributes());
        this.fruitAscending = new Dataset(attributeList, dataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);

        // fruits-and-vegetables testing (selection type: descending alphabetical)
        this.fruitDescending = new Dataset(attributeList, dataObjects, AttributeSelection.DESCENDING_ALPHABETICAL);

        // fruits-and-vegetables testing (selection type: random)
        this.fruitRandom = new Dataset(attributeList, dataObjects, AttributeSelection.RANDOM);

        // bird testing
        List<Row> birdDataObjects = DecisionTreeCSVParser.parse("data/bird.csv");
        List<String> birdAttributeList = new ArrayList<>(birdDataObjects.get(0).getAttributes());
        this.birdAscending = new Dataset(birdAttributeList, birdDataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);
        this.birdRandom = new Dataset(birdAttributeList, birdDataObjects, AttributeSelection.RANDOM);
        this.birdDescending = new Dataset(birdAttributeList, birdDataObjects, AttributeSelection.DESCENDING_ALPHABETICAL);

        //empty case
        this.empty = new Dataset(new ArrayList<>(), new ArrayList<>(), AttributeSelection.ASCENDING_ALPHABETICAL);

        // builds a TreeGenerator object and generates a tree for "foodType"
        this.testGenerator = new TreeGenerator();
        this.testGenerator.generateTree(this.fruitAscending, "foodType");
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
        Row kiwi = new Row("test row (kiwi)");
        kiwi.setAttributeValue("color", "green");
        kiwi.setAttributeValue("highProtein", "true");
        kiwi.setAttributeValue("calories", "medium");

        Assert.assertEquals("fruit", this.testGenerator.getDecision(tangerine));
        Assert.assertEquals("vegetable", this.testGenerator.getDecision(kiwi));
    }
}
