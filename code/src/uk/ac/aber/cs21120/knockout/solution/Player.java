package uk.ac.aber.cs21120.knockout.solution;

import uk.ac.aber.cs21120.knockout.interfaces.IPlayer;

/**
 * This is the Player class
 * The objects of this class represent an individual player on a team
 * Holds the player's name and position number
 * Implements the IPlayer interface
 *
 * @author Ethan Swain
 * @version 2.0, 31st October 2023
 */

public class Player implements IPlayer {
    private String name;
    private int position;

    /**
     * Constructor
     * Initialises player with the user specified name and position
     * If position is less the 1, throw RuntimeException
     * @param name Name of the player
     * @param position Position of the player
     */
    public Player(String name, int position) {
        if(position < 1) {
            throw new RuntimeException("Position should be greater than or equal to 1.");
        }
        this.name = name;
        this.position = position;
    }

    /**
     * Returns name of player
     * @return Name of player
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns position of player
     * @return Position of player
     */
    @Override
    public int getPosition() {
        return this.position;
    }
}
