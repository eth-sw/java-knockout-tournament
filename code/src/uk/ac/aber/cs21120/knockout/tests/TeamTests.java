package uk.ac.aber.cs21120.knockout.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs21120.knockout.interfaces.IPlayer;
import uk.ac.aber.cs21120.knockout.interfaces.ITeam;
import uk.ac.aber.cs21120.knockout.solution.Player;
import uk.ac.aber.cs21120.knockout.solution.Team;

import java.util.List;

public class TeamTests {
    /**
     *  We should be able to get an empty team's name
     */
    void testGetName(){
        ITeam t = new Team("My Test Team");
        Assertions.assertEquals("My Test Team", t);
    }

    /**
     * Different teams should have different names
     */
    @Test
    void testTwoTeamsDifferentNames(){
        ITeam t1 = new Team("Team One");
        ITeam t2 = new Team("Team Two");
        Assertions.assertEquals("Team One", t1.getName());
        Assertions.assertEquals("Team Two", t2.getName());
    }

    /**
     * An empty team should return an empty list of players.
     */
    @Test
    void testEmptyTeamHasNoPlayers() {
        ITeam t = new Team("Test");
        List<IPlayer> players = t.getPlayers();
        Assertions.assertNotNull(players);  // getPlayers must not return null
        Assertions.assertEquals(0,players.size());
    }

    /**
     * An empty team should have no player 1.
     */
    @Test
    void testEmptyTeamHasNoPlayer1() {
        ITeam t = new Team("Test");
        Assertions.assertNull(t.getPlayerInPosition(1));
    }

    /**
     * A team with one member should be able to have that member retrieved
     */
    @Test
    void testSinglePlayerGet() {
        ITeam t = new Team("Test");
        IPlayer p = new Player("Brian", 1);
        t.addPlayer(p);
        List<IPlayer> players = t.getPlayers();
        Assertions.assertNotNull(players);
        Assertions.assertEquals(1, players.size());
        Assertions.assertTrue(players.contains(p));
    }

    /**
     * A team with two members should be able to have both retrieved
     */
    @Test
    void testTwoPlayerGet() {
        ITeam t = new Team("Test");
        IPlayer p1 = new Player("Brian", 1);
        IPlayer p2 = new Player("Keith", 2);
        t.addPlayer(p1);
        t.addPlayer(p2);
        List<IPlayer> players = t.getPlayers();
        Assertions.assertNotNull(players);
        Assertions.assertEquals(2, players.size());
        Assertions.assertTrue(players.contains(p1));
        Assertions.assertTrue(players.contains(p2));
    }

    /**
     * In an empty team, getPlayerInPosition() will return null.
     */

    @Test
    void testEmptyTeamGetPosition() {
        ITeam t = new Team("Test");
        Assertions.assertNull(t.getPlayerInPosition(1));
    }

    /**
     * We can get a player in a given position
     */
    @Test
    void testGetPosition() {
        ITeam t = new Team("Test");
        IPlayer p1 = new Player("Brian", 11);
        IPlayer p2 = new Player("Keith", 12);
        t.addPlayer(p1);
        t.addPlayer(p2);
        Assertions.assertEquals(p1, t.getPlayerInPosition(11));
        Assertions.assertEquals(p2, t.getPlayerInPosition(12));
        Assertions.assertNull(t.getPlayerInPosition(10));
    }

    /**
     * Make sure we can only add each player once
     */
    @Test
    void testAddPlayerTwice(){
        ITeam t = new Team("Test");
        IPlayer p = new Player("Brian", 11);
        t.addPlayer(p);
        t.addPlayer(p);
        Assertions.assertEquals(1, t.getPlayers().size());
        Assertions.assertEquals(p, t.getPlayers().get(0));
    }

    /**
     * Make sure that if we try to add two different players with the same position,
     * that the second add doesn't overwrite the first.
     */
    @Test
    void testAddPlayerPositionTwice(){
        ITeam t = new Team("Test");
        IPlayer p1 = new Player("Brian", 11);
        IPlayer p2 = new Player("Dave", 11);
        t.addPlayer(p1);
        t.addPlayer(p2);
        Assertions.assertEquals(1, t.getPlayers().size());
        IPlayer p = t.getPlayers().get(0);
        Assertions.assertEquals(p1, p);
        Assertions.assertEquals("Brian", p.getName());
    }

    /**
     * Different teams should have different players (if this fails,
     * you have probably used "static" to make teams all share the same player list)
     */
    @Test
    void testTwoTeamsDifferentPlayers(){
        ITeam t1 = new Team("Team One");
        ITeam t2 = new Team("Team Two");

        IPlayer t1p1 = new Player("A", 1);
        IPlayer t1p2 = new Player("B", 2);
        t1.addPlayer(t1p1);
        t1.addPlayer(t1p2);

        IPlayer t2p1 = new Player("C", 3);
        IPlayer t2p2 = new Player("D", 4);
        IPlayer t2p3 = new Player("E", 5);
        t2.addPlayer(t2p1);
        t2.addPlayer(t2p2);
        t2.addPlayer(t2p3);

        Assertions.assertEquals(2, t1.getPlayers().size());
        Assertions.assertEquals(3, t2.getPlayers().size());

        Assertions.assertTrue(t1.getPlayers().contains(t1p1));
        Assertions.assertTrue(t1.getPlayers().contains(t1p2));
        Assertions.assertTrue(t2.getPlayers().contains(t2p1));
        Assertions.assertTrue(t2.getPlayers().contains(t2p2));
        Assertions.assertTrue(t2.getPlayers().contains(t2p3));

        Assertions.assertFalse(t2.getPlayers().contains(t1p1));
        Assertions.assertFalse(t2.getPlayers().contains(t1p2));
        Assertions.assertFalse(t1.getPlayers().contains(t2p1));
        Assertions.assertFalse(t1.getPlayers().contains(t2p2));
        Assertions.assertFalse(t1.getPlayers().contains(t2p3));
    }

}
