package uk.ac.aber.cs21120.knockout.solution;

import uk.ac.aber.cs21120.knockout.interfaces.IMatchTree;
import uk.ac.aber.cs21120.knockout.interfaces.ITeam;
import uk.ac.aber.cs21120.knockout.interfaces.ITreeNode;

//import java.util.TreeMap;
//import java.util.LinkedList;
//import java.util.Queue;

/**
 * This is the MatchTree class
 * Contains an implementation of a binary tree structure
 * Used to organise matches
 * Implements the IMatchTree interface
 *
 * @author Ethan Swain
 * @version 12.0, 8th November 2023
 */

public class MatchTree implements IMatchTree {
//    private TreeMap<Integer, ITreeNode> treeMap;
    private ITreeNode nextMatchNode;
    private ITreeNode rootNode;
    boolean traverseRight = true;

    /**
     * Constructor
     * Takes array of teams as parameter
     * Constructs initial tree structure (represents the knockout tournament)
     * @param teams Array of teams - used to create correct size of tree
     */
    public MatchTree(ITeam[] teams) {
        //this.treeMap = new TreeMap<>();
        //this.rootNode = createTree(teams);
        if (teams.length > 0) {
            this.rootNode = createTree(teams, 0, teams.length - 1);
        }

    }

    /**
     * Recursively constructs binary tree structure based on number of teams
     * Divides teams into subgroups until tree is fully constructed
     * @param teams Array of teams
     * @param startIndex First index of the array
     * @param endIndex Last index of the array
     * @return Node of binary tree being constructed
     */
    private ITreeNode createTree(ITeam[] teams, int startIndex, int endIndex) {
        // REFERENCE - https://stackoverflow.com/questions/40455237/where-to-start-with-binary-search-tree
        ITreeNode currentNode;
        ITreeNode leftNode = null, rightNode = null;
        int midIndex = startIndex + (endIndex - startIndex) / 2;

        if (startIndex == endIndex) {
            currentNode = new TreeNode(teams[startIndex], null, null);
            return currentNode;
        }
        else if (endIndex - startIndex > 1) {
            leftNode = createTree(teams, startIndex, midIndex);
            rightNode = createTree(teams, midIndex + 1, endIndex);
        }
        else if (endIndex - startIndex == 1) {
            leftNode = new TreeNode(teams[startIndex], null, null);
            rightNode = new TreeNode(teams[endIndex], null, null);
        }

        currentNode = new TreeNode(null, leftNode, rightNode);
        return currentNode;
    }

    /**
     * Recursively traverses tree to find next match to be played
     * Checks if current node has both child nodes with defined teams
     * and returns node for next match
     * @return Curre
     */
    @Override
    public ITreeNode getNextMatch() {
        // REFERENCE - https://www.youtube.com/watch?v=os0of0znlRk
        // REFERENCE - https://www.geeksforgeeks.org/level-order-tree-traversal/
        if (rootNode == null || rootNode.getRight() == null || rootNode.getLeft() == null || rootNode.getTeam()!= null) {
            return null;
        }

        ITreeNode currentNode = rootNode;
        if (traverseRight) {
            while (currentNode.getRight().getTeam() == null) {
                currentNode = currentNode.getRight();
            }
            while (currentNode.getLeft().getTeam() == null) {
                currentNode = currentNode.getLeft();
            }
        }
        else {
            while (currentNode.getLeft().getTeam() == null) {
                currentNode = currentNode.getLeft();
            }
            while (currentNode.getRight().getTeam() == null) {
                currentNode = currentNode.getRight();
            }
        }
        traverseRight = !traverseRight;
        nextMatchNode = currentNode;
        return nextMatchNode;

        // Attempt at Queue implementation
        /*if (rootNode == null) {
            return null;
        }

        Queue<ITreeNode> queue = new ArrayDeque<>();
        queue.add(rootNode);

        ITreeNode startIndex;

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                front = queue.poll();

                if (startIndex == node) {
                    if (size == 0) {
                        return null;
                    }
                    return queue.peek();
                }
                if (startIndex.getLeft() != null) {
                    queue.add(front.getLeft());
                }
                if (startIndex.getRight() != null) {
                    queue.add(front.getRight());
                }
            }
        }
        return null; */
    }

    /**
     * Sets the score for the current match
     * Updates the team that goes to the next round based on scores
     * Updates 'nextMatch' to represent next match to be played
     * If no remaining unplayed matches, throws runtime exception
     * @param leftScore Score of team in left child node
     * @param rightScore Score of team in right child node
     */
    @Override
    public void setScore(int leftScore, int rightScore) {
        if (nextMatchNode.getTeam() != null) {
            getNextMatch();
        }
        nextMatchNode.getLeft().setScore(leftScore);
        nextMatchNode.getRight().setScore(rightScore);
        if (leftScore > rightScore) {
            nextMatchNode.setTeam(nextMatchNode.getLeft().getTeam());
        }
        else {
            nextMatchNode.setTeam(nextMatchNode.getRight().getTeam());
        }
    }

    /**
     * Returns rootNode node
     * @return Root
     */
    @Override
    public ITreeNode getRoot() {
        return this.rootNode;
    }
}
