package uk.ac.aber.cs21120.knockout.solution;

import uk.ac.aber.cs21120.knockout.interfaces.*;
import uk.ac.aber.cs21120.knockout.tests.TreePrinter;

import java.util.*;

/**
 * This is the Main class
 * Takes user input for team and player details, and scores
 * Divides the number of teams into groups, and runs the group matches
 * Uses the winner from each group in the knockout tournament stage
 * Contains error-checking where necessary
 *
 * @author Ethan Swain
 * @version 9.0, 8th November 2023
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Initialises number of teams
     * Ensures input is valid
     * Generate teams
     * Divides teams into groups
     * Runs the group matches
     * Runs the knockout tournament
     * @param args
     */
    public static void main(String[] args) {
        int numTeams = 0;
        while (true) {
            try {
                System.out.println("Enter number of teams: ");
                numTeams = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.err.println("Number of teams must be an integer. Try again.");
            }
        }
        ITeam[] teams = createTeams(numTeams);
        IGroup[] groups = divideIntoGroups(teams);
        runGroupMatches(groups);
        ITeam[] groupWinners = getGroupWinners(groups);
        runKnockoutTournament(groupWinners);
        scanner.close();
    }

    /**
     * Allows user to input the team name, number of players, player name
     * Ensures input is valid, and that player number is unique
     * Adds players to teams
     * @param numTeams Number of teams
     * @return Teams
     */
    private static ITeam[] createTeams(int numTeams) {
        ITeam[] teams = new ITeam[numTeams];
        ITeam[] randTeams = new ITeam[numTeams];

        // Input team name
        for (int i = 0; i < numTeams; i++) {
            System.out.println("Enter the name of the team " + (i + 1) + ": ");
            String teamName = scanner.nextLine();
            // Input number of players
            int numPlayers = 0;
            while (true) {
                System.out.println("Enter the number of players for " + teamName + ": ");
                String playerNameInput = scanner.nextLine();
                try {
                    numPlayers = Integer.parseInt(playerNameInput);
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Number of players must be an integer. Try again.");
                }
            }
            Team team = new Team(teamName);
            Set<Integer> playerNumbers = new HashSet<>();

            // Input player name
            for (int j = 0; j < numPlayers; j++) {
                System.out.println("Enter the name of player " + (j + 1) + " for " + teamName + ": ");
                String playerName = scanner.nextLine();

                // Input player number
                int playerNum;
                while (true) {
                    System.out.println("Enter the player number for " + playerName + ": ");
                    String playerNumInput = scanner.nextLine();
                    try {
                        playerNum = Integer.parseInt(playerNumInput);
                        if (playerNumbers.contains(playerNum)) {
                            System.err.println("A player with the same number already exists. Try again.");
                            continue;
                        }
                        playerNumbers.add(playerNum);
                        team.addPlayer(new Player(playerName, playerNum));
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Player number must be an integer. Try again");
                    }
                }
            }
            teams[i] = team;
        }

        // Puts teams into random groups
        Random random = new Random();
        for (int i = 0; i < numTeams; i++) {
            int randIndex = random.nextInt(i + 1);
            if (randIndex != i) {
                ITeam temp = teams[i];
                teams[i] = teams[randIndex];
                teams[randIndex] = temp;
            }
        }

        // Copies shuffled array to new array
        for (int i = 0; i < numTeams; i++) {
            randTeams[i] = teams[i];
        }
        return randTeams;
    }

    /**
     * Divides the teams into groups of four
     * @param teams Array of teams
     * @return Groups of teams
     */
    private static IGroup[] divideIntoGroups(ITeam[] teams) {
        int numOfGroups = (teams.length + 3) / 4; // Divides teams into groups of 4
        IGroup[] groups = new IGroup[numOfGroups];

        for (int i = 0; i < numOfGroups; i++) {
            int startIndex = i * 4;
            int endIndex = startIndex + 4;

            if (endIndex > teams.length) { // Adjusts endIndex if it's longer than length of teams array
                endIndex = teams.length;
            }

            int groupSize = endIndex - startIndex;
            ITeam[] groupTeams = new ITeam[groupSize];

            for (int j = 0; j < groupSize; j++) { // Copy teams to current group's array
                groupTeams[j] = teams[startIndex + j];
            }

            groups[i] = new Group(groupTeams);
        }
        return groups;
    }

    /**
     * Runs the group matches
     * Takes user input for score
     * @param groups Array of groups
     */
    private static void runGroupMatches(IGroup[] groups) {
        Scanner scanner = new Scanner(System.in);
        for (IGroup group : groups) {
            IGroupMatch match;
            while ((match = group.getNextMatch()) != null) {
                System.out.println("Group match:");
                System.out.println("Team 1: " + match.getTeam1().getName());
                System.out.println("Team 2: " + match.getTeam2().getName());

                int team1Score, team2Score;
                boolean validInput = false;

                while (!validInput) {
                    try {
                        System.out.println("Enter score for " + match.getTeam1().getName() + ": ");
                        team1Score = scanner.nextInt();
                        System.out.println("Enter score for " + match.getTeam2().getName() + ": ");
                        team2Score = scanner.nextInt();
                        match.setScore(team1Score, team2Score);
                        validInput = true;
                    } catch (InputMismatchException e) {
                        System.err.println("Score must be an integer. Try again.");
                        scanner.next();
                    }
                }
            }
        }
    }

    /**
     * Returns the winning team from each group
     * @param groups Array of groups
     * @return Winner and second place from each group
     */
    private static ITeam[] getGroupWinners(IGroup[] groups) {
        int numOfGroups = groups.length;
        ITeam[] groupWinner = new ITeam[numOfGroups * 2];

        for (int i = 0; i < numOfGroups; i++) {
            ITeam[] table = groups[i].getTable(); // Table is the sorted teams

            if (table.length > 1) {
                groupWinner[i * 2] = table[0]; // Store winner at position i*2
                groupWinner[i * 2 + 1] = table[1]; // Store second place at position i*2+1
            }

        }
        return groupWinner;
    }

    /**
     * Runs the knockout tournament after the winner from each group is retrieved
     * Takes user input for the score for each team
     * @param teams Array of teams
     */
    private static void runKnockoutTournament(ITeam[] teams) {
        IMatchTree matchTree = new MatchTree(teams);
        ITreeNode nextMatch;
        Scanner scanner = new Scanner(System.in);

        // Runs until winner is found
        while ((nextMatch = matchTree.getNextMatch()) != null) {
            System.out.println("Knockout match:");
            TreePrinter.print(matchTree);

            int team1Score, team2Score;
            boolean validInput = false;

            while (!validInput) {
                try {
                    System.out.println(nextMatch.getLeft().getTeam().getName() + " v " + nextMatch.getRight().getTeam().getName());
                    System.out.print("Score for " + nextMatch.getLeft().getTeam().getName() + ": ");
                    team1Score = scanner.nextInt();
                    nextMatch.getLeft().setScore(team1Score);
                    System.out.print("Score for " + nextMatch.getRight().getTeam().getName() + ": ");
                    team2Score = scanner.nextInt();
                    nextMatch.getRight().setScore(team2Score);
                    matchTree.setScore(team1Score, team2Score);
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.err.println("Score must be an integer. Try again.");
                    scanner.next();
                }
            }
        }

        ITreeNode winner = matchTree.getRoot(); // Root is the winner

        // Outputs the tree
        TreePrinter.print(matchTree);
        System.out.println("Winner of the tournament: " + winner.getTeam().getName());
    }
}