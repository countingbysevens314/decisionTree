package sol;

import src.ITreeNode;

/**
 * A class that represents the edge of an attribute node in the decision tree
 */
public class ValueEdge {
    // TODO: add more fields if needed
    private String value;
    private ITreeNode child;

    public ValueEdge(String value, ITreeNode child) {
        this.value = value;
        this.child = child;
    }

}
