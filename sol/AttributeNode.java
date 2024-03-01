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

    public AttributeNode(String attribute, String decision, List<ValueEdge> outgoingEdges) {
        this.attribute = attribute;
        this.decision = decision;
        this.outgoingEdges = outgoingEdges;
    }

    @Override
    public String getDecision(Row forDatum) {
        for (ValueEdge edge: this.outgoingEdges) {
            if (edge.getValue().equals(forDatum.getAttributeValue(this.attribute))) {
                return edge.getChild().getDecision(forDatum);
            }
        }
        return this.decision;
    }
}
