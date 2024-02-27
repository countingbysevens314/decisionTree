package sol;

import src.ITreeNode;
import src.Row;

/**
 * A class representing a leaf in the decision tree.
 */
// TODO: Uncomment this once you've implemented the methods in the ITreeNode interface!
public class DecisionLeaf implements ITreeNode {
    String decision;
    // TODO: add fields as needed
    public DecisionLeaf(String decision) {
        this.decision = decision;
    }

    // TODO: Implement the ITreeNode interface
    @Override
    public String getDecision(Row forDatum) {
        return this.decision;
    }
}
