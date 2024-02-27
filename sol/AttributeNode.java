package sol;

import java.util.List;

import src.AttributeSelection;
import src.ITreeNode;
import src.Row;

/**
 * A class representing an inner node in the decision tree.
 */
public class AttributeNode implements ITreeNode {

    private String decision;
    private String attribute;
    private List<ValueEdge> outgoingEdges;

    public AttributeNode(String attribute, List<ValueEdge> outgoingEdges) {
        this.attribute = attribute;
        this.outgoingEdges = outgoingEdges;
    }

    @Override
    public String getDecision(Row forDatum) {
        // to be implemented
        return this.decision;
    }
}
