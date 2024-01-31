package uk.ac.aber.cs21120.knockout.solution;

import uk.ac.aber.cs21120.knockout.interfaces.IGroupMatch;
import uk.ac.aber.cs21120.knockout.interfaces.ITeam;

/**
 * This is the GroupMatch class
 * Represents a match between two teams
 * Implements the IGroupMatch interface
 *
 * @author Ethan Swain
 * @version 2.0, 2nd November 2023
 */
public class GroupMatch implements IGroupMatch {

    private ITeam team1;
    private ITeam team2;
    private int team1Points;
    private int team2Points;
    private boolean played;

    /**
     * Constructor
     * Takes two ITeam objects as parameters (representing the two teams)
     * Initialises teams and sets 'played' to 'false'
     * @param team1 First team
     * @param team2 Second team
     */
    public GroupMatch(ITeam team1, ITeam team2) {
        this.team1 = team1;
        this.team2 = team2;
        this.played = false;
    }

    /**
     * Returns first team
     * @return First team
     */
    @Override
    public ITeam getTeam1() {
        return team1;
    }

    /**
     * Returns second team
     * @return Second team
     */
    @Override
    public ITeam getTeam2() {
        return team2;
    }

    /**
     * Checks whether match has been played
     * @return True or False
     */
    @Override
    public boolean isPlayed() {
        return played;
    }

    /**
     * Sets score for the match
     * If match has already been played, throw IllegalStateException
     * Otherwise, calculate points earned by boh teams
     * Sets 'played' to 'true'
     * @param team1Score Score for team 1
     * @param team2Score Score for team 2
     */
    @Override
    public void setScore(int team1Score, int team2Score) {
        if (played) {
            throw new IllegalStateException("Match has already been played.");
        }
        team1Points = calculatePoints(team1Score, team2Score);
        team2Points = calculatePoints(team2Score, team1Score);
        played = true;
    }

    /**
     * Returns points earned by first team
     * If match has not been played, throw IllegalStateException
     * @return Team 1 points
     */
    @Override
    public int getTeam1Points() {
        if (!played) {
            throw new IllegalStateException("Match has not been played.");
        }
        return team1Points;
    }

    /**
     * Returns points earned by second team
     * If match has not been played, throw IllegalStateException
     * @return Team 2 points
     */
    @Override
    public int getTeam2Points() {
        if (!played) {
            throw new IllegalStateException("Match has not been played.");
        }
        return team2Points;
    }

    /**
     * Calculates points earned by a team based on its score and opponent's score
     * Win - 3 points
     * Draw - 1 point
     * Loss - 0 points
     * @param teamScore
     * @param opponentScore
     * @return Number of points
     */
    private int calculatePoints(int teamScore, int opponentScore) {
        if (teamScore > opponentScore) {
            return 3;
        } else if (teamScore == opponentScore) {
            return 1;
        } else {
            return 0;
        }
    }
}
