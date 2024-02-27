package sol;

import src.ITreeGenerator;
import src.ITreeNode;
import src.Row;

import java.util.List;

/**
 * A class that implements the ITreeGenerator interface used to generate a decision tree
 */
// TODO: Uncomment this once you've implemented the methods in the ITreeGenerator interface!
public class TreeGenerator implements ITreeGenerator<Dataset> {
    // TODO: document this field
    private ITreeNode root;
    private Dataset mainDataset;

    // constructor? without parameter?

    @Override
    public void generateTree(Dataset trainingData, String targetAttribute) {
        // monday, changed D to Dataset
        this.mainDataset = trainingData;
        // remove target attribute
        Dataset subset = trainingData.subset(targetAttribute); //call remove in subset?

        // build root tree
        this.buildDecisionTree();

        // primeNumbers.remove(3);
        //  remove method: search through list until

        // getattributetosliton
    }

    public ITreeNode buildDecisionTree(String attribute, Dataset trainingData) {
        //buildDecisionTree(next attribute to split on, smaller subsets);
        return null;
    }

    @Override
    public String getDecision(Row datum) {
        return null;
    }
    // TODO: Implement methods declared in ITreeGenerator interface!

    // TODO: remove() to remove targetAttribute from a Dataset before creating nodes
    public void remove(String targetAttribute) {
        this.dataset.remove(targetAttribute);
    }

    // TODO: implement addValue() when creating nodes?
    /**
     * finds the size of the dataset (number of rows)
     *
     * @return the number of rows in the dataset
     */
    public List<ValueEdge> addValue() {
        //List<String> attributeList =
        // doesn't know about the data
        return null;
    }
    // list = addValue();
    // AttributeNote node1 = new AttributeNode(name, list);
}
