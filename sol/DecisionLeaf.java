package sol;

import src.ITreeNode;
import src.Row;

/**
 * A class representing a leaf in the decision tree.
 */

public class DecisionLeaf implements ITreeNode {
    String decision;

    /**
     * a decisionLeaf object
     *
     * @param decision — the decision returned by a decisionLeaf
     */
    public DecisionLeaf(String decision) {
        this.decision = decision;
    }

    /**
     * gets the decision associated with a row
     *
     * @param forDatum the datum to lookup a decision for
     * @return the decision for the dataum
     */
    @Override
    public String getDecision(Row forDatum) {
        return this.decision;
    }
}
