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

    /**
     * Constructs an AttributeNode object
     *
     * @param attribute - the attribute associated with a particular item in the dataset
     * @param decision - the predicted value for the target attribute, and are labeled with the
     *                   classification of a datum that has traveled down that path of the tree
     * @param outgoingEdges - the possible values of that attribute
     */
    public AttributeNode(String attribute, String decision, List<ValueEdge> outgoingEdges) {
        this.attribute = attribute;
        this.decision = decision;
        this.outgoingEdges = outgoingEdges;
    }

    /**
     * Predicts the decision for a given row object. getDecision will loop through the valueEdges
     * and check whether they are equal to the attribute value. If they are equal, getDecision will
     * call getDecision on the child trees.
     *
     * @param forDatum the datum to look up a decision for
     * @return the decision for a given row object
     */
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
