package uk.ac.aber.cs21120.knockout.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs21120.knockout.interfaces.IMatchTree;
import uk.ac.aber.cs21120.knockout.interfaces.ITeam;
import uk.ac.aber.cs21120.knockout.solution.MatchTree;
import uk.ac.aber.cs21120.knockout.solution.Team;

import java.util.ArrayList;

public class MatchTreeConstructionTests {
    /**
     * Helper method to generate some teams
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
     * Passing in a zero-length array should return a null tree! But you won't be penalised too harshly
     * for failing this test.
     */
    @Test
    void testNoTeams(){
        ITeam teams[] = new ITeam[0];
        IMatchTree t = new MatchTree(teams);
        Assertions.assertNull(t.getRoot());
    }

    /**
     * If we pass in a single team we should get a root node with a team set in it
     * and no child nodes.
     */
    @Test
    void testOneTeam(){
        ITeam teams[] = generateN(1);
        IMatchTree t = new MatchTree(teams);
        // there is a root node, which has a team, which has a name
        Assertions.assertNotNull(t.getRoot());
        Assertions.assertNotNull(t.getRoot().getTeam());
        Assertions.assertEquals("team0", t.getRoot().getTeam().getName());

        // there are no child nodes
        Assertions.assertNull(t.getRoot().getLeft());
        Assertions.assertNull(t.getRoot().getRight());
    }

    /**
     * If we create a tree from two teams, we should get a "match" node (with no team or score)
     * and two child nodes - one for each team.
     */
    @Test
    void testTwoTeams(){
        ITeam teams[] = generateN(2);
        IMatchTree t = new MatchTree(teams);

        // root node has no team
        Assertions.assertNotNull(t.getRoot());
        Assertions.assertNull(t.getRoot().getTeam());

        // left node is team0, right node is team1
        Assertions.assertNotNull(t.getRoot().getLeft());
        Assertions.assertNotNull(t.getRoot().getLeft().getTeam());
        Assertions.assertEquals("team0", t.getRoot().getLeft().getTeam().getName());
        Assertions.assertNotNull(t.getRoot().getRight());
        Assertions.assertNotNull(t.getRoot().getRight().getTeam());
        Assertions.assertEquals("team1", t.getRoot().getRight().getTeam().getName());
    }

    /**
     * Construct a three-team tree.
     * The tree generated should look like this:
     *                           (not played)
     *                 ┌───────────────┴───────────────┐
     *             team0: 0                      (not played)
     *                                         ┌───────┴───────┐
     *                                     team1: 0        team2: 0
     *
     *  or be flipped horizontally. Any team can appear at the leaf nodes,
     *  but they must all be different.
     */
    @Test
    void testThreeTeams() {
        // There are six possible ways this tree could be created!
        ITeam teams[] = generateN(3);
        // there are going to be three leaf nodes - they must all be different.
        ITeam a=null,b=null,c=null;
        // make the tree
        IMatchTree t = new MatchTree(teams);
        // root node must have no team
        Assertions.assertNull(t.getRoot().getTeam());
        // root must have both children
        Assertions.assertNotNull(t.getRoot().getLeft());
        Assertions.assertNotNull(t.getRoot().getRight());

        // inspect the tree to see what's going on. Is the subtree on the left?
        if(t.getRoot().getLeft().getTeam()==null){ // if the left child is a subtree it will have no team
            // the left node has no team (i.e. "not played"), so it must have two children.
            Assertions.assertNotNull(t.getRoot().getLeft().getLeft());
            Assertions.assertNotNull(t.getRoot().getLeft().getRight());
            // and each of those must be a team.
            a = t.getRoot().getLeft().getLeft().getTeam();
            b = t.getRoot().getLeft().getRight().getTeam();
            // make sure these leaves are correct
            Assertions.assertNotNull(a);
            Assertions.assertNotNull(b);
            Assertions.assertNull(t.getRoot().getLeft().getLeft().getLeft());
            Assertions.assertNull(t.getRoot().getLeft().getLeft().getRight());
            Assertions.assertNull(t.getRoot().getLeft().getRight().getLeft());
            Assertions.assertNull(t.getRoot().getLeft().getRight().getRight());
            // and the right node must be a leaf with team c
            c = t.getRoot().getRight().getTeam();
            Assertions.assertNotNull(c);
            Assertions.assertNull(t.getRoot().getRight().getLeft());
            Assertions.assertNull(t.getRoot().getRight().getRight());

        } else if(t.getRoot().getRight().getTeam()==null){  // is the subtree on the right?
            // the right node has no team, so it must have two children.
            Assertions.assertNotNull(t.getRoot().getRight().getLeft());
            Assertions.assertNotNull(t.getRoot().getRight().getRight());
            // and each of those must be a team.
            a = t.getRoot().getRight().getLeft().getTeam();
            b = t.getRoot().getRight().getRight().getTeam();
            // make sure these leaves are correct
            Assertions.assertNotNull(a);
            Assertions.assertNotNull(b);
            Assertions.assertNull(t.getRoot().getRight().getLeft().getLeft());
            Assertions.assertNull(t.getRoot().getRight().getLeft().getRight());
            Assertions.assertNull(t.getRoot().getRight().getRight().getLeft());
            Assertions.assertNull(t.getRoot().getRight().getRight().getRight());
            // and the left node must be a leaf with team c
            c = t.getRoot().getLeft().getTeam();
            Assertions.assertNotNull(c);
            Assertions.assertNull(t.getRoot().getLeft().getLeft());
            Assertions.assertNull(t.getRoot().getLeft().getRight());
        } else {
            // neither the left nor right children of the root  are "not played",
            // which means this tree is invalid.
            Assertions.fail("Root node has teams set on both children");
        }

        // make sure that the three leaf teams are all different
        Assertions.assertNotEquals(a,b);
        Assertions.assertNotEquals(a,c);
        Assertions.assertNotEquals(c,b);

    }
}
