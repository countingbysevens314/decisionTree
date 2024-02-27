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
    
    // partition to group rows of the same value together; returns a list of rows
    public List<Row> partition(String value) {
        // loop through partition to find intended value
    }; 

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


