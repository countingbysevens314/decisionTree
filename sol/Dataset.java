package sol;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import src.AttributeSelection;
import src.IDataset;
import src.Row;

/**
 * A class representing a training dataset for the decision tree
 */
public class Dataset implements IDataset {

    private List<String> attributeList;
    private List<Row> dataObjects;
    private AttributeSelection selectionType;

    /**
     * Constructor for a Dataset object
     *
     * @param attributeList - a list of attributes
     * @param dataObjects -  a list of rows
     * @param attributeSelection - an enum for which way to select attributes
     */
    public Dataset(List<String> attributeList, List<Row> dataObjects, AttributeSelection attributeSelection) {
        this.attributeList = attributeList;
        this.dataObjects = dataObjects;
        this.selectionType = attributeSelection;
    }

    /**
     * Gets list of attributes in the dataset
     *
     * @return a list of strings
     */
    @Override
    public List<String> getAttributeList() {
        return this.attributeList;
    }

    /**
     * Gets list of data objects (row) in the dataset
     *
     * @return a list of Rows
     */
    @Override
    public List<Row> getDataObjects() {
        return this.dataObjects;
    }

    /**
     * Returns the attribute selection type (alphabetical, reverse alphabetical, random) for this Dataset
     *
     * @return the attribute selection type
     */
    @Override
    public AttributeSelection getSelectionType() {
        return this.selectionType;
    }

    /**
     * finds the size of the dataset (number of rows)
     *
     * @return the number of rows in the dataset
     */
    @Override
    public int size() {
        return this.dataObjects.size();
    }

    /**
     * removes the targetAttribute from the original dataset
     *
     * @param targetAttribute, the attribute for which we would like return an outcome
     * @return a Dataset excluding the targetAttribute in its attributeList
     */
    public Dataset removeTarget(String targetAttribute) {
        List<String> copyList = new ArrayList<>(this.attributeList);
        for (int i = 0; i < this.attributeList.size(); i++) {
            if (targetAttribute.equals(this.attributeList.get(i))) {
                copyList.remove(i);
            }
        }
        // returning a copy of the dataset
        return new Dataset(copyList, this.dataObjects, this.selectionType);
    }

    /**
     * groups rows of the same value together
     *
     * @param onAttribute — the attribute that we're currently splitting on
     * @return a list of Dataset, representing the partitioned datasets
     */
    // partition to group rows of the same value together; returns a list of Datasets
    public List<Dataset> partition(String onAttribute) {
        // 1. initialize a list of Dataset to be returned
        // it will contain three Datasets each for e.g. color=green, yellow, orange
        List<Dataset> subsets = new ArrayList<>();

        // 2. initialize a list of String to filter ValueEdges
        // stream().distinct() helper removes recurrent values and builds a list of only distinct ones
        List<String> distinctValues = new ArrayList<>();
        for (Row r : this.dataObjects) {
            distinctValues.add(r.getAttributeValue(onAttribute));
        }
        distinctValues = distinctValues.stream().distinct().toList();

        // 3. initialize a list of String, copying attributeList, and excluding the attribute we split on
        List<String> newAttributeList = new ArrayList<>(this.attributeList);
        newAttributeList.remove(onAttribute);

        // for every distinct value (e.g. green, yellow...)
        for (String value: distinctValues) {
            // 4. initialize a list of Row to contain only e.g. Rows with color = yellow
            List<Row> listRow = new ArrayList<>();
            // for every row in dataObjects
            for (Row r: this.dataObjects) {
                // if row's onAttribute -> its value = this distinct value we're looking at in this loop
                if (r.getAttributeValue(onAttribute).equals(value)) {
                    // add this row to our listRow in step 4
                    listRow.add(r);
                }
            }
            // after we loop through dataObjects for this distinct value (yellow), create and add
            // to the list of Dataset we want to return
            subsets.add(new Dataset(newAttributeList, listRow, this.selectionType));
            // continue looping through other distinct values (green, orange...) until we don't have anymore
            // distinct values, that way we've completed dividing Dataset based on attribute=color
        }
        return subsets;
    }

    /**
     * computes the default decision based on the targetAttribute
     *
     * @param targetAttribute, the attribute for which we would like return an outcome
     * @return a String representing the default value for a given attributeNode
     */
    public String getDefault(String targetAttribute) {
        List<Dataset> subsets = this.partition(targetAttribute);
        int maxSize = subsets.get(0).size();
        int maxIndex = 0;
        for (int i = 0; i < subsets.size() - 1; i++) {
            if (maxSize <= subsets.get(i+1).size()) {
                maxSize = subsets.get(i+1).size();
                maxIndex = i+1;
            }
        }
        return subsets.get(maxIndex).getDataObjects().get(0).getAttributeValue(targetAttribute);
    }

    /**
     * returns the decision for a decisionLeaf if every row has the same outcome
     *
     * @param targetAttribute, the attribute for which we would like return an outcome
     * @return the highest incidence of outcome
     */
    public String getLeafDecision(String targetAttribute) {
        for (int i = 0; i < this.dataObjects.size() - 1; i++) {
            if (!this.dataObjects.get(i).getAttributeValue(targetAttribute).
                    equals(this.dataObjects.get(i+1).getAttributeValue(targetAttribute))) {
               return null;
            }
        }
        return this.dataObjects.get(0).getAttributeValue(targetAttribute);
    }

    /**
     * gets an attribute to split the data on, either by ascending alphabetical order, descending alphabetical order,
     * or a random order
     *
     * @return a String representing the attribute to be split on
     */
    public String getAttributeToSplitOn() {
        switch (this.selectionType) {
            case ASCENDING_ALPHABETICAL -> {
                return this.attributeList.stream().sorted().toList().get(0);
            }
            case DESCENDING_ALPHABETICAL -> {
                return this.attributeList.stream().sorted().toList().get(this.attributeList.size() - 1);
            }
            case RANDOM -> {
                Random random = new Random();
                int upperBound = this.attributeList.size();
                /*
                 * e.g. list size = 10 ; max index 9; upperBound = 10
                 * generates a random number between 0 (inclusive) and 10 (exclusive);
                 * the upper bound must be greater than 0
                 */
                int randomNum = random.nextInt(upperBound);
                return this.attributeList.stream().sorted().toList().get(randomNum);
            }
        }     throw new RuntimeException("Non-Exhaustive Switch Case");
    }
}


