package sol;

import java.util.List;

import src.AttributeSelection;
import src.ITreeNode;
import src.Row;

/**
 * A class representing an inner node in the decision tree.
 */
// TODO: Uncomment this once you've implemented the methods in the ITreeNode interface!
public class AttributeNode implements ITreeNode {

    private String decision;

    private String attribute;

    private List<ValueEdge> outgoingEdges;

    public AttributeNode(String attribute, List<ValueEdge> outgoingEdges) {
        this.attribute = attribute;
        this.outgoingEdges = outgoingEdges;
    }

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

    @Override
    public String getDecision(Row forDatum) {
        // to be implemented
        return this.decision;
    }
}
