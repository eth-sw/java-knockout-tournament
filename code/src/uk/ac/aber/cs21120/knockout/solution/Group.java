package uk.ac.aber.cs21120.knockout.solution;

import uk.ac.aber.cs21120.knockout.interfaces.IGroup;
import uk.ac.aber.cs21120.knockout.interfaces.IGroupMatch;
import uk.ac.aber.cs21120.knockout.interfaces.ITeam;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Group class
 * Represents a group of teams in the tournament
 * Implements the IGroup interface
 *
 * @author Ethan Swain
 * @version 3.0, 2nd November 2023
 */
public class Group implements IGroup {

    private List<ITeam> teams;
    private List<IGroupMatch> matches;

    /**
     * Constructor
     * Takes array of 'ITeam' objects as a parameter and initialises the 'teams' and 'matches' lists
     * Calls the 'generateMatches()' method to create all possible matches between teams
     * @param teams Array of teams
     */
    public Group(ITeam[] teams) {
        this.teams = new ArrayList<>(List.of(teams));
        this.matches = new ArrayList<>();
        generateMatches();
    }

    /**
     * Generates all possible matches between teams in the group
     * Matches are created and added to the 'matches' list
     */
    private void generateMatches() {
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                matches.add(new GroupMatch(teams.get(i), teams.get(j)));
            }
        }
    }

    /**
     * Returns the next match that has not been played yet
     * Iterates through the list of matches and checks whether each match has been played
     * If a match has not been played, it's returned, otherwise it continues until an
     * unplayed match is found
     * @return Next match to be played
     */
    @Override
    public IGroupMatch getNextMatch() {
        for (IGroupMatch match : matches) {
            if (!match.isPlayed()) {
                return match;
            }
        }
        return null;
    }

    /**
     * Calculates the total points earned by a team
     * Iterates through matches and checks if each match has been played
     * If a match has been played, it determines which team won the match and adds
     * the points to the total points of the team
     * @param team Array of teams
     * @return Teams' points
     */
    @Override
    public int getPoints(ITeam team) {
        int points = 0;
        for (IGroupMatch match : matches) {
            if (match.isPlayed()) {
                if (match.getTeam1() == team) {
                    points = points + match.getTeam1Points();
                }
                else if (match.getTeam2() == team) {
                    points = points + match.getTeam2Points();
                }
            }
        }
        return points;
    }

    /**
     * Returns array of teams sorted based on their points in the group
     * Uses sort method to sort in descending order
     * Sorted teams then returned as array
     * @return Array of sorted teams
     */
    @Override
    public ITeam[] getTable() {
        // REFERENCE - https://stackoverflow.com/questions/21970719/java-arrays-sort-with-lambda-expression
        teams.sort((team1, team2) -> getPoints(team2) - getPoints(team1));
        return teams.toArray(new ITeam[0]);
    }
}
