package uk.ac.aber.cs21120.knockout.solution;

import uk.ac.aber.cs21120.knockout.interfaces.IPlayer;
import uk.ac.aber.cs21120.knockout.interfaces.ITeam;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Team class
 * The objects of this class represent a team of players
 * Implements the ITeam interface
 *
 * @author Ethan Swain
 * @version 1.0, 30th October 2023
 */

public class Team implements ITeam {
    private String name;
    private List<IPlayer> players;

    /**
     * Initialises a new team with user-defined name
     * Initialises the 'players' list as an empty ArrayList
     * @param name Name of player in the corresponding team
     */
    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();
    }


    /**
     * Adds a player to the list if the player is not already in the list
     * Ensures that there's only player in each position
     * No exception thrown
     * @param p The player to add
     */
    @Override
    public void addPlayer(IPlayer p) {
        if (!players.contains(p) && getPlayerInPosition(p.getPosition()) == null) { // if player array doesn't contain player to be added
            /*for (IPlayer player : players) {
                if (player.getPosition() == p.getPosition()) {
                    return;
                }
            }*/
            players.add(p);
        }
    }

    /**
     * Returns the player in the specified position if it exists
     * If no player is found, then null is returned
     * @param n The player position (integer >=1)
     * @return Player in specified position
     */
    @Override
    public IPlayer getPlayerInPosition(int n) {
        for (IPlayer player : players) {
            if (player.getPosition() == n) {
                return player;
            }
        }
        return null;
    }

    /**
     * Returns a list of all players in the team
     * @return ArrayList of all players in the team
     */
    @Override
    public List<IPlayer> getPlayers() {
        return this.players;
    }

    /**
     * Returns team name
     * @return Name of team
     */
    @Override
    public String getName() {
        return this.name;
    }
}
