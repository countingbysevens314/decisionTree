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


