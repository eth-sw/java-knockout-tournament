package uk.ac.aber.cs21120.knockout.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs21120.knockout.interfaces.ITeam;
import uk.ac.aber.cs21120.knockout.solution.Team;
import uk.ac.aber.cs21120.knockout.solution.TreeNode;

public class TreeNodeTests {
    @Test
    /**
     * Ensure we can create a node, which will have a null team and child nodes, and a zero score.
     */
    public void testCreate(){
        TreeNode n = new TreeNode(null, null, null);
        Assertions.assertNull(n.getTeam());
        Assertions.assertNull(n.getLeft());
        Assertions.assertNull(n.getRight());
        Assertions.assertEquals(0, n.getScore());
    }

    /**
     * Ensure we can get the team set in the constructor
     */
    @Test
    public void testGetTeam(){
        ITeam t = new Team("test");
        TreeNode n = new TreeNode(t, null, null);
        Assertions.assertNotNull(n.getTeam());
        Assertions.assertEquals(t, n.getTeam());
    }

    /**
     * Ensure we can change the team
     */
    @Test
    public void testSetTeam() {
        ITeam t = new Team("test");
        TreeNode n = new TreeNode(null, null, null);
        Assertions.assertNull(n.getTeam());
        n.setTeam(t);
        Assertions.assertEquals(t, n.getTeam());
    }

    /**
     * Ensure we can set two child nodes in the constructor and get them
     */
    @Test
    public void testGetChildNodes() {
        TreeNode left = new TreeNode(null, null, null);
        TreeNode right = new TreeNode(null, null, null);
        TreeNode n = new TreeNode(null, left, right);
        Assertions.assertEquals(left, n.getLeft());
        Assertions.assertEquals(right, n.getRight());
    }

    /**
     * Ensure we can change the score
     */
    @Test
    public void testSetScore() {
        TreeNode n = new TreeNode(null, null, null);
        n.setScore(3);
        Assertions.assertEquals(3, n.getScore());
    }

}
