package uk.ac.aber.cs21120.knockout.tests;

import org.junit.jupiter.api.Test;
import uk.ac.aber.cs21120.knockout.interfaces.IGroupMatch;
import uk.ac.aber.cs21120.knockout.interfaces.ITeam;
import uk.ac.aber.cs21120.knockout.solution.Group;
import uk.ac.aber.cs21120.knockout.solution.Team;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The methods in this class further test the Group class.
 * @author Ethan Swain
 * @version 2.0, 19th November 2023
 */
public class FurtherGroupTests {


    /**
     * Checks whether matches are played correctly for all teams
     * Does draw scenarios
     * Ensures that total number of matches played, and points, are the
     * expected values
     */
    @Test
    void testLargeGroup() {
        // Set number of teams and store in array
        int numTeams = 22;
        ITeam[] teams = new ITeam[numTeams];
        for (int i = 0; i < numTeams; i++) {
            teams[i] = new Team("Team " + (i + 1));
        }
        // Create Group with teams
        Group group = new Group(teams);

        int numMatchesPlayed = 0;

        // Iterate through each team to play matches against all other teams
        for (int i = 0; i < numTeams; i++) {
            ITeam team1 = teams[i];

            for (int j = i + 1; j < numTeams; j++) {
                ITeam team2 = teams[j];

                // Get next match from group
                IGroupMatch match = group.getNextMatch();
                assertNotNull(match);

                // Draw scenario
                match.setScore(0, 0);

                numMatchesPlayed++;

                // Ensures correct teams are in each match
                assertEquals(team1, match.getTeam1());
                assertEquals(team2, match.getTeam2());
            }
        }

        // Ensures that total number of matches played is expected
        assertEquals((numTeams * (numTeams - 1)) / 2, numMatchesPlayed);

        // Check points assignment to each team
        for (ITeam team : teams) {
            assertEquals(numTeams - 1, group.getPoints(team));
        }
    }

    /**
     * Draws matches between each team
     * Ensures each team has correct number of points and table is sorted correctly
     */
    @Test
    void testAllDraws() {
        // Initialise number of teams
        int numTeams = 6;
        ITeam[] teams = new ITeam[numTeams];
        for (int i = 0; i < numTeams; i++) {
            teams[i] = new Team("Team " + (i + 1));
        }
        // Create Group with teams
        Group group = new Group(teams);

        // Plays matches between each pair of teams
        while (group.getNextMatch() != null) {
            IGroupMatch match = group.getNextMatch();
            assertNotNull(match);
            match.setScore(1, 1); // Sets score to 1-1
        }

        // Check points assigned to each team
        for (ITeam team : teams) {
            assertEquals(numTeams - 1, group.getPoints(team));
        }

        // Ensure table is sorted correctly
        ITeam[] table = group.getTable();
        for (int i = 0; i < numTeams - 1; i++) {
            assertTrue(group.getPoints(table[i]) >= group.getPoints(table[i + 1]));
        }
    }
}
