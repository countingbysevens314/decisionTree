package sol;

import src.AttributeSelection;
import src.ITreeGenerator;
import src.ITreeNode;
import src.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that implements the ITreeGenerator interface used to generate a decision tree
 */
// TODO: Uncomment this once you've implemented the methods in the ITreeGenerator interface!
public class TreeGenerator implements ITreeGenerator<Dataset> {
    private ITreeNode root;
    private Dataset targetRemoved;

    private String targetAttribute;

    /**
     * Generates a tree object
     *
     * @param trainingData    the dataset to train on
     * @param targetAttribute the string attribute to predict
     */
    @Override
    public void generateTree(Dataset trainingData, String targetAttribute) {
        if (trainingData.size() == 0) {
            throw new RuntimeException("can't build tree on an empty dataset");
        }
        this.targetAttribute = targetAttribute;
        this.targetRemoved = trainingData.removeTarget(targetAttribute);
        this.root = this.buildTree(this.targetRemoved);
    }

    /**
     * Builds a tree based on a given dataset. If we are at a decision leaf, we will return the outcome. Otherwise, we
     * will split on the attribute and compute the default decision for the attributeNode, returning a new attributeNode
     * with its appropriate value edges.
     *
     * @param trainingData — the dataset from which we construct the tree
     * @return an ITreeNode, representing the tree generated from the dataset
     */
    public ITreeNode buildTree(Dataset trainingData) {
        String decision = trainingData.getLeafDecision(this.targetAttribute);
        if (decision != null || trainingData.getAttributeList().isEmpty()) {
            return new DecisionLeaf(decision);
        }
        else {
            String onAttribute = trainingData.getAttributeToSplitOn();
            String defaultDecision = trainingData.getDefault(this.targetAttribute);
            List<Dataset> subsets = trainingData.partition(onAttribute);
            return new AttributeNode(onAttribute, defaultDecision, this.addValue(onAttribute, subsets));
        }
    }

    /**
     * gets a decision for a given row of datum
     *
     * @param datum the datum to lookup a decision for
     * @return a string, representing the decision for given row of datum
     */
    @Override
    public String getDecision(Row datum) {
        return this.root.getDecision(datum);
    }

    /**
     * creates and fills in the list of ValueEdge for an AttributeNode
     *
     * @param onAttribute the attribute we're splitting on
     * @param subsets the partitioned list of Dataset
     * @return a list of ValueEdge representing every possible value
     */
    public List<ValueEdge> addValue(String onAttribute, List<Dataset> subsets) {
        List<ValueEdge> edgeList = new ArrayList<>();
        for (Dataset subset : subsets){
            String value = subset.getDataObjects().get(0).getAttributeValue(onAttribute);
            edgeList.add(new ValueEdge(value, this.buildTree(subset)));
        }
        return edgeList;
    }
}
