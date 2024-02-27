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

    // two getter methods?
    /**
     * getter method to return the value of a valueEdges
     * @return the string representation of a value
     */
    public String getValue() { return this.value; }

    /**
     * getter method for the child that this ValueEdge holds
     * @return an ITreeNode child
     */
    public ITreeNode getChild() { return this.child;}


}
