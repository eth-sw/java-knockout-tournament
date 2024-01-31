package uk.ac.aber.cs21120.knockout.solution;

import uk.ac.aber.cs21120.knockout.interfaces.ITeam;
import uk.ac.aber.cs21120.knockout.interfaces.ITreeNode;

/**
 * This is the TreeNode class
 * Contains methods to set and get the data
 * Implements the ITreeNode interface
 *
 * @author Ethan Swain
 * @version 1.0, 31st October 2023
 */
public class TreeNode implements ITreeNode {
    private ITeam team;
    private final ITreeNode left;
    private final ITreeNode right;
    private int score;

    /**
     * Constructor
     * Initialises node with a team, and left and right child nodes
     * Sets score of node to 0
     * @param team Name of the team
     * @param left Left child node
     * @param right Right child node
     */
    public TreeNode(ITeam team, ITreeNode left, ITreeNode right) {
        this.team = team;
        this.left = left;
        this.right = right;
        this.score = 0;
    }

    /**
     * Returns left child node of current node
     * @return Left node
     */
    @Override
    public ITreeNode getLeft() {
        return this.left;
    }

    /**
     * Returns right child node of current node
     * @return Right node
     */
    @Override
    public ITreeNode getRight() {
        return this.right;
    }

    /**
     * Sets team associated with current node to provided team
     * @param team Team
     */
    @Override
    public void setTeam(ITeam team) {
        this.team = team;
    }

    /**
     * Returns team associated with current node
     * @return Team
     */
    @Override
    public ITeam getTeam() {
        return this.team;
    }

    /**
     * Sets score of current node
     * @param s Score
     */
    @Override
    public void setScore(int s) {
        this.score = s;
    }

    /**
     * Returns score associated with current node
     * @return Score
     */
    @Override
    public int getScore() {
        return this.score;
    }

}
