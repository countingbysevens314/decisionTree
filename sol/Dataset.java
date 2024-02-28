package sol;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


    //helper to remove
    // TODO: remove() to remove targetAttribute from a Dataset before creating nodes
    public void remove(String targetAttribute) {
        for (int i = 0; i < this.attributeList.size(); i++) {
            if (targetAttribute.equals(this.attributeList.get(i))) {
                this.attributeList.remove(i);
                // how to alter attributeList and get it applied to the dataset
            }
        /* remove dataObjects
        for (int j = 0; j < this.dataObjects.size(); j++){
            this.dataObjects.get(i).getAttributes();
            for (int k = 0; k < this.dataObjects.size(); k++) {
            }
        }
            if (targetAttribute.equals(this.dataObjects.get(i).getAttributes())) {
                this.dataObjects.remove(i);
            }
         */
        }
    }

    public Dataset subset(String targetAttribute) {
        List<String> copyList = new ArrayList<>(this.attributeList);
        for (int i = 0; i < this.attributeList.size(); i++) {
            if (targetAttribute.equals(this.attributeList.get(i))) {
                copyList.remove(i);
            }
        }
        // returning a copy of the dataset
        return new Dataset(copyList, this.dataObjects, this.selectionType);
        // we return the same dataObjects???
    }
    
    // partition to group rows of the same value together; returns a list of Datasets
    public List<Dataset> partition(String onAttribute) { //say onAttribute=color
        // 1. initialize a list of Dataset to be returned
        // it will contain three Datasets each for e.g. color=green, yellow, orange
        List<Dataset> subsets = new ArrayList<>();

        // 2. initialize a list of String to filter ValueEdges
        // stream() helper removes recurrent values and builds a list of only distinct ones
        // for onAttribute=color, this list will be "yellow, orange, green"
        List<String> distinctValues = (List<String>) this.attributeList.stream();

        // 3. initialize a list of String, copying attributeList, and excluding the attribute we split on
        List<String> newAttributeList = new ArrayList<>(this.attributeList);
        newAttributeList.remove(onAttribute);

        // 4. initialize a list of Row to contain only e.g. Rows with color = yellow
        List<Row> listRow = new ArrayList<>();

        // for every distinct value (e.g. green, yellow...)
        for (String value: distinctValues) {
            // for every row in dataObjects
            for (Row r: this.dataObjects) {
                // if row's onAttribute -> its value = this distinct value we're looking at in this loop
                // (e.g. row's color -> yellow = yellow)
                if (r.getAttributeValue(onAttribute).equals(value)) {
                    //add this row to our listRow in step 4
                    listRow.add(r);
                }
            }
            // after we loop through dataObjects for this distinct value (yellow), create and add
            // to the list of Dataset we want to return
            // so that we have one Dataset with color=yellow
            subsets.add(new Dataset(newAttributeList, listRow, this.selectionType));
            // continue looping through other distinct values (green, orange...) until we don't have anymore
            // distinct values, that way we've completed dividing Dataset based on attribute=color
        }
        return subsets;
    }

    public String getAttributeToSplitOn() {
        switch (this.selectionType) {
            case ASCENDING_ALPHABETICAL -> {
                //deleted AttributeSelection. from starting code
                return this.attributeList.stream().sorted().toList().get(0);
            }
            case DESCENDING_ALPHABETICAL -> {
                return this.attributeList.stream().sorted().toList().get(this.attributeList.size() - 1);
            }
            case RANDOM -> {
                Random random = new Random();
                int upperBound = this.attributeList.size();
                /**
                 * list size = 10 ; max index 9; upperBound = 10
                 * generates a random number between 0 (inclusive) and 10 (exclusive);
                 * the upper bound must be greater than 0
                 */
                int randomNum = random.nextInt(upperBound);
                System.out.println(randomNum);
                return this.attributeList.stream().sorted().toList().get(randomNum);
            }
        }     throw new RuntimeException("Non-Exhaustive Switch Case");
    }
}


