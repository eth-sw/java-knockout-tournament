package uk.ac.aber.cs21120.knockout.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs21120.knockout.interfaces.IGroupMatch;
import uk.ac.aber.cs21120.knockout.solution.GroupMatch;
import uk.ac.aber.cs21120.knockout.solution.Team;

public class GroupMatchTests {
    /**
     * We should be able to construct a group match and retrieve the teams
     */
    @Test
    void testGetTeams() {
        IGroupMatch m = new GroupMatch(new Team("Team One"), new Team("Team Two"));
        Assertions.assertEquals("Team One", m.getTeam1().getName());
        Assertions.assertEquals("Team Two", m.getTeam2().getName());
    }

    /**
     * A match should not be played by default
     */
    @Test
    void testNotPlayed() {
        IGroupMatch m = new GroupMatch(new Team("Team One"), new Team("Team Two"));
        Assertions.assertFalse(m.isPlayed());
    }

    /**
     * A match should be able to be played
     */
    @Test
    void testPlayed() {
        IGroupMatch m = new GroupMatch(new Team("Team One"), new Team("Team Two"));
        m.setScore(1, 0);
        Assertions.assertTrue(m.isPlayed());
    }

    /**
     * Test we can set the scores. Note that there is deliberately no way to retrieve the scores
     * once set. All we can get out is the points each team gains.
     */
    @Test
    void testSetGetScore() {
        IGroupMatch m = new GroupMatch(new Team("Team One"), new Team("Team Two"));
        m.setScore(1, 0);
    }

    /**
     * It should not be possible to set the score of a match twice
     */
    @Test
    void testSetScoreTwice() {
        IGroupMatch m = new GroupMatch(new Team("Team One"), new Team("Team Two"));
        m.setScore(1, 1);
        Assertions.assertThrows(IllegalStateException.class, () -> m.setScore(1, 1));
    }

    /**
     * A won match should return the correct points
     */
    @Test
    void testWonMatchPoints() {
        IGroupMatch m = new GroupMatch(new Team("Team One"), new Team("Team Two"));
        m.setScore(1, 0);
        Assertions.assertEquals(3, m.getTeam1Points());
        Assertions.assertEquals(0, m.getTeam2Points());

        m = new GroupMatch(new Team("Team One"), new Team("Team Two"));
        m.setScore(0, 1);
        Assertions.assertEquals(0, m.getTeam1Points());
        Assertions.assertEquals(3, m.getTeam2Points());
    }

    /**
     * A drawn match should return the correct points
     */
    @Test
    void testDrawnMatchPoints() {
        IGroupMatch m = new GroupMatch(new Team("Team One"), new Team("Team Two"));
        m.setScore(1, 1);
        Assertions.assertEquals(1, m.getTeam1Points());
        Assertions.assertEquals(1, m.getTeam2Points());
    }

    /**
     * It should not be possible to get the points of a match that has not been played
     */
    @Test
    void testGetPointsNotPlayed() {
        IGroupMatch m = new GroupMatch(new Team("Team One"), new Team("Team Two"));
        Assertions.assertThrows(IllegalStateException.class, m::getTeam1Points);
        Assertions.assertThrows(IllegalStateException.class, m::getTeam2Points);
    }

}
