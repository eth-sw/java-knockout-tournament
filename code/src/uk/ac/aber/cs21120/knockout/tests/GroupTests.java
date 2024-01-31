package uk.ac.aber.cs21120.knockout.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs21120.knockout.interfaces.ITeam;
import uk.ac.aber.cs21120.knockout.solution.Group;
import uk.ac.aber.cs21120.knockout.solution.Team;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The methods in this class test the Group class.
 */
public class GroupTests {
    /**
     * This method tests that the constructor for the Group class works correctly, or at least
     * doesn't crash.
     */
    @Test
    void testConstructGroup(){
        ITeam t1 = new Team("Team One");
        ITeam t2 = new Team("Team Two");
        ITeam t3 = new Team("Team Three");
        ITeam t4 = new Team("Team Four");
        Group g = new Group(new ITeam[]{t1, t2, t3, t4});
    }

    /**
     * Test that the correct points are given to the correct teams for a win, loss and draw.
     * To do that, we have to play an entire tournament and we can't assume the order of
     * matches (some students may randomize).
     *
     * We also test that the table is correct (that is, the teams are in the correct order).
     */
    @Test
    void testPointsAndTable() {
        // first construct an array of teams
        ITeam t1 = new Team("Team One");
        ITeam t2 = new Team("Team Two");
        ITeam t3 = new Team("Team Three");
        ITeam t4 = new Team("Team Four");
        ITeam[] array = new ITeam[]{t1, t2, t3, t4};
        // convert to a list for later use - it's easier to check a list for containment and get the index of an item
        // with a list than with an array.
        List<ITeam> list = Arrays.asList(array);
        // now make a group out of that array
        Group g = new Group(array);
        // keep a count of the points for each team
        int points[] = new int[4];

        // then get the next match while there are matches remaining. If the number of matches played
        // gets more than 6, we have a problem.
        int matchesPlayed = 0;
        while (g.getNextMatch() != null) {
            // check that the number of matches played is correct
            Assertions.assertTrue(matchesPlayed < 6);
            matchesPlayed++;
            // get the match
            ITeam a = g.getNextMatch().getTeam1();
            ITeam b = g.getNextMatch().getTeam2();
            int idxa = list.indexOf(a);
            int idxb = list.indexOf(b);

            // check that the match is between two different teams
            Assertions.assertNotEquals(a, b);
            // check that the match is between two teams that are in the group
            Assertions.assertTrue(list.contains(a));
            Assertions.assertTrue(list.contains(b));
            // give the match a random score
            int scoreA = ThreadLocalRandom.current().nextInt(4);
            int scoreB = ThreadLocalRandom.current().nextInt(4);
            g.getNextMatch().setScore(scoreA, scoreB);
            // keep track of the points each team should get
            if (scoreA > scoreB) {
                points[idxa] += 3;    // winning team A scores 3 points
            } else if (scoreB > scoreA) {
                points[idxb] += 3;    // winning team B scores 3 points
            } else {
                // a draw scores 1 point for each team
                points[idxa]++;
                points[idxb]++;
            }
        }
        // check the points for each team are correct
        for (int i = 0; i < 4; i++) {
            Assertions.assertEquals(points[i], g.getPoints(array[i]));
        }
        // now check that the four teams are in the table in descending order of points.
        ITeam[] table = g.getTable();
        for (int i = 0; i < 3; i++) {
            Assertions.assertTrue(g.getPoints(table[i]) >= g.getPoints(table[i + 1]));
        }
    }


    /**
     * This method tests that getNextMatch() returns the correct match. This is tricky, because they
     * could be played in any order. But every team should play every other team once.
     */
    @Test
    void testGetNextMatch(){
        // first construct an array of teams
        ITeam t1 = new Team("Team One");
        ITeam t2 = new Team("Team Two");
        ITeam t3 = new Team("Team Three");
        ITeam t4 = new Team("Team Four");
        ITeam[] array = new ITeam[]{t1, t2, t3, t4};
        // convert to a list for later use - it's easier to check a list for containment and get the index of an item
        // with a list than with an array.
        List<ITeam> list = Arrays.asList(array);
        // now make a group out of that array
        Group g = new Group(array);
        // now build a map of how many times each team plays each other team.
        int[][] counts = new int[4][4];

        // then get the next match while there are matches remaining. If the number of matches played
        // gets more than 6, we have a problem.
        int matchesPlayed = 0;
        while(g.getNextMatch()!=null){
            // check that the number of matches played is correct
            Assertions.assertTrue(matchesPlayed<6);
            matchesPlayed++;
            // get the match teams
            ITeam a = g.getNextMatch().getTeam1();
            ITeam b = g.getNextMatch().getTeam2();
            // check that the match is between two different teams
            Assertions.assertNotEquals(a,b);
            // check that the match is between two teams that are in the group
            Assertions.assertTrue(list.contains(a));
            Assertions.assertTrue(list.contains(b));
            // check that the match is between two teams that have not played each other before.
            // We probably don't need to do it both ways, but it doesn't hurt.
            Assertions.assertEquals(0,counts[list.indexOf(a)][list.indexOf(b)]);
            Assertions.assertEquals(0,counts[list.indexOf(b)][list.indexOf(a)]);
            // increment the count of matches between these two teams
            counts[list.indexOf(a)][list.indexOf(b)]++;
            counts[list.indexOf(b)][list.indexOf(a)]++;
            // now set the result of the match to a no-score draw (it doesn't matter what we choose here)
            g.getNextMatch().setScore(0,0);
        }
        Assertions.assertTrue(matchesPlayed == 6);
    }


}
