package sol;

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
    // TODO: document this field
    private ITreeNode root;

    private Dataset targetRemoved;

    private String targetAttribute;

    // constructor? without parameter?

    @Override
    public void generateTree(Dataset trainingData, String targetAttribute) {
        this.targetAttribute = targetAttribute;
        // monday, changed D to Dataset
        //this.mainDataset = trainingData;
        // remove target attribute
        this.targetRemoved = trainingData.removeTarget(targetAttribute); //call remove in subset?
        // build root tree, how to initialize root
        this.root = this.buildTree(this.targetRemoved);
    }

    public ITreeNode buildTree(Dataset trainingData) {
        String decision = trainingData.getLeafDecision(this.targetAttribute);
        if (decision != null || trainingData.getAttributeList().isEmpty()) {
            return new DecisionLeaf(decision);
        }
        else {
            String onAttribute = trainingData.getAttributeToSplitOn();
            String defaultDecision = trainingData.getDefault(this.targetAttribute);
            System.out.println("splitting on " + onAttribute);
            List<Dataset> subsets = trainingData.partition(onAttribute);
            /* moved to addValue
            for (Dataset subset: subsets) {
                buildTree(subset);
            } */

            // compute default for attributeNode using getDefault()
            return new AttributeNode(onAttribute, defaultDecision, addValue(onAttribute, subsets));
        }

        // case where neither attributeNode nor decisionLeaf is created
    }

    @Override
    public String getDecision(Row datum) {
        // Call get decision on the root (first attribute value that we split on)
        return this.root.getDecision(datum);
        /**
         * from buildTree:
         *   String decision = trainingData.getLeafDecision(this.targetAttribute);
         *         if (decision != null || trainingData.getAttributeList().isEmpty()) {
         *             return new DecisionLeaf(decision);
         *         }
         *         return null;
         */
    }

    /**
     * finds the size of the dataset (number of rows)
     *
     * @return the number of rows in the dataset
     */
    public List<ValueEdge> addValue(String onAttribute, List<Dataset> subsets) {
        List<ValueEdge> edgeList = new ArrayList<>();
        for (Dataset subset : subsets){
            String value = subset.getDataObjects().get(0).getAttributeValue(onAttribute);
            edgeList.add(new ValueEdge(value, buildTree(subset)));
        }
        return edgeList;
    }
}
