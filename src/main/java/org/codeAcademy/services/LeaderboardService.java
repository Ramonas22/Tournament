package org.codeAcademy.services;

import org.codeAcademy.model.Leaderboard;
import org.codeAcademy.model.Tournament;
import org.hibernate.Session;

import java.util.*;

public class LeaderboardService {

    public void createLeaderboard(Session session){
        Scanner scanner = new Scanner(System.in);
        TournamentService tournamentService = new TournamentService();

        Leaderboard leaderboardTemp = new Leaderboard();
        List<Tournament> tournamentList = tournamentService.getAllRegisteredTournament(session);
        int temp;

        System.out.println("Enter leaderboard title");
        leaderboardTemp.setTitle(scanner.nextLine());

        do {
            tournamentList.removeAll(leaderboardTemp.getTournaments());
            System.out.println("Pick tournaments to be added to leaderboard");
            tournamentService.tournamentPrinter(tournamentList);
            System.out.println("[ " + (tournamentList.size()+1) + " ] Enter to exit");
            temp = scanner.nextInt()-1;

            if(temp >= 0 && temp < tournamentList.size()){

                leaderboardTemp.getTournaments().add(tournamentList.get(temp));

                session.beginTransaction();
                session.save(leaderboardTemp);
                session.getTransaction().commit();

            } else if (temp != tournamentList.size()) {
                System.out.println("Entered wrong command try again");
            }
        }while (temp != tournamentList.size());
    }

    public List<Leaderboard> getLeaderboards(Session session){
        return session.createQuery("from leaderboard").list();
    }

    public void updateLeaderboard(Session session,Leaderboard leaderboard){
        Scanner scanner = new Scanner(System.in);

        TournamentService tournamentService = new TournamentService();
        List<Tournament> tempTournament;

        int temp;

        do{
            System.out.printf("""
                    Enter option from the list
                    [ 1 ] Update leaderboard title %s
                    [ 2 ] Remove tournaments from leaderboard
                    [ 3 ] Add tournaments to leaderboard
                    [ 4 ] Exit
                    """, leaderboard.getTitle());
            temp = scanner.nextInt()-1;

            if(temp == 0){
                System.out.println("Enter new leaderboard title");
                leaderboard.setTitle(scanner.nextLine());
            } else if (temp == 1) {
                while(true) {
                    System.out.println("Pick a tournament to remove from leaderboard");
                    tournamentService.tournamentPrinter(leaderboard.getTournaments());
                    System.out.println("[ " + (leaderboard.getTournaments().size()+1)+" ] to exit");
                    temp = scanner.nextInt()-1;

                    if (temp == leaderboard.getTournaments().size()){
                        break;
                    }else if(temp >= leaderboard.getTournaments().size() && temp < leaderboard.getTournaments().size()){
                        leaderboard.getTournaments().remove(leaderboard.getTournaments().get(temp));
                    } else {
                        System.out.println("Entered wrong command");
                    }
                }
                temp = 1;
            }if(temp == 2){
                while(true) {
                    System.out.println("Pick a tournament to add to leaderboard");
                    tempTournament = tournamentService.getAllRegisteredTournament(session);
                    tempTournament.removeAll(leaderboard.getTournaments());
                    tournamentService.tournamentPrinter(tempTournament);
                    System.out.println("[ " + (tempTournament.size()+1)+" ] to exit");
                    temp = scanner.nextInt()-1;

                    if (temp == tempTournament.size()){
                        break;
                    }else if(temp >= leaderboard.getTournaments().size() && temp < leaderboard.getTournaments().size()){
                        leaderboard.getTournaments().add(tempTournament.get(temp));
                    } else {
                        System.out.println("Entered wrong command");
                    }
                }
                temp = 2;
            }else {
                System.out.println("Entered wrong option");
            }
        }while (temp != 3);
        session.beginTransaction();
        session.update(leaderboard);
        session.beginTransaction().commit();

    }

    public void deleteTournament(Session session){
        Scanner scanner = new Scanner(System.in);

        List<Leaderboard> leaderboardList;
        int temp;

        while (true){
            leaderboardList = getLeaderboards(session);
            System.out.println("Pick which tournament to be removed from leaderboard");
            printLeaderboards(leaderboardList);
            System.out.println("[ " + (leaderboardList.size() + 1) + " ] To exit");
            temp = scanner.nextInt()-1;

            if(temp == leaderboardList.size()){
                break;
            }else if(temp >= 0 && temp < leaderboardList.size()){
                leaderboardList.remove(leaderboardList.get(temp));
            }else {
                System.out.println("Entered wrong command try again");
            }
        }

        session.beginTransaction();
        session.update(leaderboardList);
        session.getTransaction().commit();
    }

    private void printLeaderboards(List<Leaderboard> leaderboardList) {
        for (int i = 0; i < leaderboardList.size(); i++) {
            System.out.println("[ " + i + " ] Leaderboard title " + leaderboardList.get(i).getTitle());
        }
    }

    public void displayLeaderboard(Leaderboard leaderboard){
        System.out.println("Team name   :   points");
        for (int i = 0; i < leaderboard.getTournaments().size();i++) {

            int finalI = i;
            leaderboard.getTournaments().get(i).getTeams_points().forEach( (team_id , points) -> {
                System.out.println(" [ " + finalI + " ] "+
                leaderboard.getTournaments().get(finalI).getTeams().stream().filter(team -> team.getTeamId() == team_id).findFirst().orElse(null).getName()
                + " : " + points);
            } );
            i++;
        }
    }
}
