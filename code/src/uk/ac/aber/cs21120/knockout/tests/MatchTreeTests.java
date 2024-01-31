package uk.ac.aber.cs21120.knockout.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs21120.knockout.interfaces.IMatchTree;
import uk.ac.aber.cs21120.knockout.interfaces.ITeam;
import uk.ac.aber.cs21120.knockout.interfaces.ITreeNode;
import uk.ac.aber.cs21120.knockout.solution.MatchTree;
import uk.ac.aber.cs21120.knockout.solution.Team;

import java.util.ArrayList;

public class MatchTreeTests {
    /**
     * Helper method to generate some teams. I'm aware this method already exists in
     * another class, but I wanted to make very sure all the test classes had as few
     * dependencies as possible.
     * @param n team count
     * @return array of teams
     */
    private static ITeam[] generateN(int n){
        ArrayList<ITeam> teams = new ArrayList<>();
        for(int i=0;i<n;i++){
            // generate team name
            String name = "team"+Integer.toString(i);
            teams.add(new Team(name));
        }
        // this is very strange, but it's apparently how
        // to convert a list to an array in Java!
        return teams.toArray(new ITeam[0]);
    }

    /**
     * If there are no teams, there can be no matches.
     */
    @Test
    void testNoTeamsNoMatches(){
        ITeam teams[] = new ITeam[0];
        IMatchTree t = new MatchTree(teams);
        Assertions.assertNull(t.getNextMatch());
    }

    /**
     * If there is one team there can be no matches
     */
    @Test
    void testOneTeamNoMatches(){
        ITeam teams[] = generateN(1);
        IMatchTree t = new MatchTree(teams);
        Assertions.assertNull(t.getNextMatch());
    }

    /**
     * Test that setScore with no match played throws a runtime exception
     */
    @Test
    void testNoMatchesSetScoreException(){
        ITeam teams[] = generateN(1);
        IMatchTree t = new MatchTree(teams);
        Assertions.assertThrows(RuntimeException.class,() ->
        {
           t.setScore(0,0);
        });
    }

    /**
     * Test that two teams results in the root node being found, and the root
     * should have no team
     */
    @Test
    void testTwoTeams(){
        ITeam teams[] = generateN(2);
        IMatchTree t = new MatchTree(teams);
        Assertions.assertEquals(t.getRoot(), t.getNextMatch());
        Assertions.assertNull(t.getRoot().getTeam());
    }

    /**
     * Test that setting the score on a two-team match works as expected
     */
    @Test
    void testTwoTeamsSetScore(){
        ITeam teams[] = generateN(2);
        IMatchTree t = new MatchTree(teams);
        Assertions.assertEquals(t.getRoot(), t.getNextMatch());
        Assertions.assertNull(t.getRoot().getTeam());
        t.setScore(1,3);
        // we don't actually know which team is on the left and which is on the right, so we need to
        // inspect the tree
        ITeam tLeft = t.getRoot().getLeft().getTeam();
        ITeam tRight = t.getRoot().getRight().getTeam();
        // make sure the right-hand team is the winner
        Assertions.assertEquals(tRight, t.getRoot().getTeam());
        // and check the scores
        Assertions.assertEquals(1, t.getRoot().getLeft().getScore());
        Assertions.assertEquals(3, t.getRoot().getRight().getScore());

    }

    /**
     * Test three teams. The left hand team in the subtree should win, then the right hand team of the root.
     * So if the tree were like this:
     *
     *                           (not played)
     *                 ┌───────────────┴───────────────┐
     *             team0: 0                      (not played)
     *                                         ┌───────┴───────┐
     *                                     team1: 0        team2: 0
     *
     * team1 would in the first match, leading to a final between team0 and team1, which team1 would win.
     */
    @Test
    void testThreeTeamsRightSubtree(){
        ITeam teams[] = generateN(3);
        IMatchTree t = new MatchTree(teams);

        // get a match - this should return the match between two teams in the subtree.
        ITreeNode n = t.getNextMatch();
        Assertions.assertNotNull(n);
        Assertions.assertNull(n.getTeam()); // which should have a null team, since it's unplayed.
        Assertions.assertNotEquals(t.getRoot(), n); // and it mustn't be the root node.

        // set the left hand node to win
        ITreeNode winner = n.getLeft();
        t.setScore(3,1);
        Assertions.assertEquals(n.getTeam(), winner.getTeam());
        Assertions.assertEquals(3, n.getLeft().getScore());
        Assertions.assertEquals(1, n.getRight().getScore());

        // now find the next match. This should be the root node.
        n = t.getNextMatch();
        Assertions.assertEquals(t.getRoot(), n);
        // this time, make the right node win.
        winner = n.getRight();
        t.setScore(1,3);
        Assertions.assertEquals(n.getTeam(), winner.getTeam());
        Assertions.assertEquals(1, n.getLeft().getScore());
        Assertions.assertEquals(3, n.getRight().getScore());
    }

    /**
     * Test four teams - this is a balanced tree and there should be three matches.
     */

    @Test
    void testFourTeams(){
        ITeam teams[] = generateN(4);
        IMatchTree t = new MatchTree(teams);

        // get a match and check it's OK.
        ITreeNode n1 = t.getNextMatch();
        Assertions.assertNotNull(n1);
        Assertions.assertNull(n1.getTeam()); // which should have a null team, since it's unplayed.
        Assertions.assertNotEquals(t.getRoot(), n1); // and it mustn't be the root node.

        // set the left hand node to win, and check the results
        ITreeNode winner1 = n1.getLeft();
        t.setScore(3,1);
        Assertions.assertEquals(n1.getTeam(), winner1.getTeam());
        Assertions.assertEquals(3, n1.getLeft().getScore());
        Assertions.assertEquals(1, n1.getRight().getScore());

        // now get the next match similarly, which should be the right subtree
        ITreeNode n2 = t.getNextMatch();
        Assertions.assertNotNull(n2);
        Assertions.assertNull(n2.getTeam()); // which should have a null team, since it's unplayed.
        Assertions.assertNotEquals(n2, n1);  // not the same node as the previous node
        Assertions.assertNotEquals(t.getRoot(), n2); // and it mustn't be the root node.

        // set the right hand node to win, and check the results
        ITreeNode winner2 = n2.getRight();
        t.setScore(2,5);
        Assertions.assertEquals(n2.getTeam(), winner2.getTeam());
        Assertions.assertEquals(2, n2.getLeft().getScore());
        Assertions.assertEquals(5, n2.getRight().getScore());
        Assertions.assertEquals(winner2.getTeam(), n2.getRight().getTeam());
        Assertions.assertNotEquals(winner2.getTeam(), n2.getLeft().getTeam());

        // now get the next match similarly, which should be the root node
        ITreeNode n3 = t.getNextMatch();
        Assertions.assertNotNull(n3);
        Assertions.assertEquals(t.getRoot(), n3); // and it must be the root node.
        Assertions.assertNotEquals(n3, n1);  // not the same node as the previous node
        Assertions.assertNotEquals(n3, n2);  // not the same node as the previous node

        // set the left hand node to win, and check the results
        ITreeNode winner3 = n3.getLeft();
        t.setScore(6,2);
        Assertions.assertEquals(n3.getTeam(), winner3.getTeam());
        Assertions.assertEquals(6, n3.getLeft().getScore());
        Assertions.assertEquals(2, n3.getRight().getScore());
        Assertions.assertEquals(winner3.getTeam(), n3.getLeft().getTeam());
        Assertions.assertNotEquals(winner3.getTeam(), n3.getRight().getTeam());
    }

    private int getDepth(ITreeNode n, ITreeNode current, int depth){
        if (current == n)return depth;
        if (current.getLeft() != null){
            int d = getDepth(n, current.getLeft(), depth+1);
            if (d != -1)return d;
        }
        if (current.getRight() != null){
            int d = getDepth(n, current.getRight(), depth+1);
            if (d != -1)return d;
        }
        return -1;
    }

    /**
     * Find the depth of a node in the tree by performing a depth-first search for that node,
     * or -1 if the node is not in the tree.
     */
    private int getDepth(IMatchTree t, ITreeNode n){
        return getDepth(n, t.getRoot(), 0);
    }

    /**
     * Test that a tree for 8 teams runs the games in the correct order.
     */
    @Test
    void testEightTeams(){
        ITeam teams[] = generateN(8);
        IMatchTree t = new MatchTree(teams);
        // play the first match and check the depth of the match node is correct
        ITreeNode n = t.getNextMatch();
        t.setScore(3,1);
        Assertions.assertEquals(2, getDepth(t, n));
        // Do the same for the next match
        n = t.getNextMatch();
        t.setScore(1,3);
        Assertions.assertEquals(2, getDepth(t, n));
        // Do the same for the next match
        n = t.getNextMatch();
        t.setScore(1,0);
        Assertions.assertEquals(2, getDepth(t, n));
        // And again
        n = t.getNextMatch();
        t.setScore(6,5);
        Assertions.assertEquals(2, getDepth(t, n));
        // And again, but now all quarter-finals are complete
        n = t.getNextMatch();
        t.setScore(3,4);
        Assertions.assertEquals(1, getDepth(t, n));
        // And again
        n = t.getNextMatch();
        t.setScore(4,2);
        Assertions.assertEquals(1, getDepth(t, n));
        // And again - now the semi-finals are complete, so this is the final and root node.
        n = t.getNextMatch();
        t.setScore(1,2);
        Assertions.assertEquals(0, getDepth(t, n));
    }

}
