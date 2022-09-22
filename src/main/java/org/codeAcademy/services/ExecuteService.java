package org.codeAcademy.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.text.ParseException;
import java.util.Scanner;

public class ExecuteService {

    public static void run() throws ParseException {
        SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        do{
            printMainMenu();
        }while (navigateMainMenu(pickOption(), session));
    }

    private static boolean navigateMainMenu(int pickOptionFromMainMenu, Session session) throws ParseException {
        switch (pickOptionFromMainMenu) {
            case 1 -> navigateToPlayerMenu(session);
            case 2 -> navigateToTeamMenu(session);
            case 3 -> navigateToMatchMenu(session);
            case 4 -> navigateToTournamentMenu(session);
            case 5 -> navigateToLeaderboardMenu(session);
            case 6 -> {
                return false;
            }
            default -> {
                System.out.println("Entered wrong command try again");
                pickOption();
            }
        }
        return true;
    }

    private static void navigateToLeaderboardMenu(Session session) {
        do{
            printLeaderboardOptions();
        }while (leaderboardMenu(pickOption(), session));
    }

    private static boolean leaderboardMenu(int pickOption, Session session) {
        LeaderboardService leaderboardService = new LeaderboardService();

        switch (pickOption){

            case 1 -> leaderboardService.createLeaderboard(session);
            case 2 -> leaderboardService.updateLeaderboard(session);
            case 3 -> leaderboardService.deleteTournament(session);
            case 4 -> leaderboardService.printLeaderboards(leaderboardService.getLeaderboards(session));
            case 5 -> leaderboardService.displayLeaderboard(session);
            case 6 -> { return false;}
            default -> System.out.println("Entered wrong command");
        }
        return true;
    }

    private static void printLeaderboardOptions() {
        System.out.println("""
                [ 1 ] Create Leaderboard
                [ 2 ] Update Leaderboard
                [ 3 ] Delete Leaderboard
                [ 4 ] Print all Leaderboards
                [ 5 ] Display Leaderboard
                [ 6 ] To Exit
                """);
    }

    private static void navigateToTournamentMenu(Session session) {
        do{
            printTournamentOptions();
        }while(tournamentMenu(pickOption(),session));
    }

    private static boolean tournamentMenu(int pickOption, Session session) {
        TournamentService tournamentService = new TournamentService();

        switch (pickOption){
            case 1 -> tournamentService.registerTournament(session);
            case 2 -> tournamentService.updateTournamentInfo(session);
            case 3 -> tournamentService.tournamentPrinter(tournamentService.getAllRegisteredTournament(session));
            case 4 -> tournamentService.finishTournament(session);
            case 5 -> tournamentService.singleTournamentServices(session);
            case 6 -> {
                return false;
            }
            default -> System.out.println("Entered wrong command");
        }
        return true;
    }

    private static void printTournamentOptions() {
        System.out.println("""
                [ 1 ] Register new tournament
                [ 2 ] Update tournament info
                [ 3 ] Print all ongoing tournaments
                [ 4 ] Finish tournament
                [ 5 ] Pick tournament for more options
                [ 6 ] To exit
                """);
    }

    private static void navigateToMatchMenu(Session session) {
        do{
            printMatchMenu();
        }while (matchMenu(pickOption(),session));
    }

    private static boolean matchMenu(int pickOption, Session session) {
        MatchServices matchServices = new MatchServices();

        switch (pickOption){
            case 1 -> matchServices.playMatch(session);
            case 2 -> matchServices.showMatchesPlayed(matchServices.getMatchesPlayed(session));
            case 3 -> matchServices.eraseMatchHistory(session,matchServices.getMatchesPlayed(session));
            case 4 -> {
                return false;
            }
            default -> System.out.println("Entered wrong command");
        }
        return true;
    }

    private static void printMatchMenu() {
        System.out.println("""
                [ 1 ] Play friendly match
                [ 2 ] Show all matches that was played so far
                [ 3 ] Erase all matches from the history
                [ 4 ] Exit
                """);
    }

    private static void navigateToTeamMenu(Session session) {
        do{
            printTeamMenu();
        }while (teamMenu(pickOption(),session));
    }

    private static void printTeamMenu() {
        System.out.println("""
                [ 1 ] Create team
                [ 2 ] Update team
                [ 3 ] Print all teams
                [ 4 ] Remove team from the pool
                [ 5 ] Pick players for the team
                [ 6 ] Exit
                """);
    }

    private static boolean teamMenu(int pickOption, Session session) {
        TeamService teamService = new TeamService();

        switch (pickOption){
            case 1 -> teamService.createTeam(session);
            case 2 -> teamService.updateTeam(session);
            case 3 -> teamService.printTeams(teamService.getTeams(session));
            case 4 -> teamService.removeTeamFromPool(session);
            case 5 -> teamService.pickPlayersForTeam(session);
            case 6 -> { return false;}
            default -> {
                System.out.println("Entered wrong command");
            }
        }
        return true;
    }

    private static void navigateToPlayerMenu(Session session) throws ParseException {
        do{
            printPlayerMenu();
        }
        while (playerMenu(pickOption(), session));
    }

    private static boolean playerMenu(int pickOption, Session session) throws ParseException {
        PlayerServices playerServices = new PlayerServices();

        switch (pickOption) {
            case 1 -> playerServices.addPlayerToThePool(session);
            case 2 -> playerServices.updatePlayerInfoFo(session);
            case 3 -> playerServices.printPlayerPool(playerServices.getPlayerPool(session));
            case 4 -> playerServices.removePlayerFromThePool(session);
            case 5 -> {
                return false;
            }
            default -> {
                System.out.println("Entered wrong command");
            }
        }
        return true;
    }

    private static void printPlayerMenu() {
        System.out.println("""
                [ 1 ] Add player to player pool
                [ 2 ] Update player from player pool
                [ 3 ] Print player pool
                [ 4 ] Remove player from player pool
                [ 5 ] Exit
                """);
    }

    private static int pickOption() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private static void printMainMenu() {
        System.out.println("""
                [ 1 ] Player menu
                [ 2 ] Team menu
                [ 3 ] Match menu
                [ 4 ] Tournament menu
                [ 5 ] Leaderboard menu
                [ 6 ] Exit
                """);
    }
}
