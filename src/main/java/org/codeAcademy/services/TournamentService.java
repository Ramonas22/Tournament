package org.codeAcademy.services;

import org.codeAcademy.model.Match;
import org.codeAcademy.model.Team;
import org.codeAcademy.model.Tournament;
import org.hibernate.Session;

import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;

public class TournamentService {

    public void registerTournament(Session session){
        Tournament tempTournament = new Tournament();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter tournament name");
        tempTournament.setName(scanner.nextLine());

        System.out.println("Enter tournament prize fund ");
        tempTournament.setPrizeFund(scanner.nextBigDecimal().setScale(2, RoundingMode.DOWN));

        session.beginTransaction();
        session.save(tempTournament);
        session.getTransaction().commit();
    }

    public List<Tournament> getAllRegisteredTournament(Session session){
        return session.createQuery("from Tournament").list();
    }

    public void updateTournamentInfo(Session session){
        Scanner scanner = new Scanner(System.in);

        List<Tournament> tournamentList = getAllRegisteredTournament(session);
        Tournament tournamentTemp;
        int temp;

        if(tournamentList.size() > 0){
            do {
                System.out.println("Pick Tournament which information you want to update");
                tournamentPrinter(tournamentList);
                temp = scanner.nextInt()-1;
            }while (temp>=0 && temp < tournamentList.size());
            tournamentTemp = tournamentList.get(temp);
            do {
                System.out.printf("""
                        Pick option from the list below:
                        [ 1 ] Change tournament name: %s
                        [ 2 ] Change tournament prize fund: %s
                        [ 3 ] Save and Exit
                        [ 4 ] Exit without saving""", tournamentTemp.getName(), tournamentTemp.getPrizeFund());
                temp = scanner.nextInt()-1;
                switch (temp){
                    case 0->{
                        System.out.println("Enter new tournament name");
                        tournamentTemp.setName(scanner.nextLine());
                    }
                    case 1->{
                        System.out.println("Enter new prize fund");
                        tournamentTemp.setPrizeFund(scanner.nextBigDecimal());
                    }
                    case 2->{
                        session.beginTransaction();
                        session.save(tournamentTemp);
                        session.getTransaction().commit();
                        temp = 3;
                    }
                    case 3->{}
                    default -> System.out.println("Entered wrong command try again");
                }

            }while (temp!=3);
        }

    }

    public void tournamentPrinter(List<Tournament> tournamentList){
        TeamService teamService = new TeamService();

        for (int i = 0; i < tournamentList.size(); i++) {
            System.out.printf("[ %s ] Tournament name %s prize fund %s\n"
                    , (i+1),tournamentList.get(i).getName(), tournamentList.get(i).getPrizeFund());
            if(tournamentList.get(i).getTeams().size() > 0){
                System.out.println("Teams registered in tournament: ");
                teamService.printTeams(tournamentList.get(i).getTeams());
            }
        }
    }

    public void finishTournament(Session session){
        Scanner scanner = new Scanner(System.in);

        List<Tournament> tournamentList;
        int temp;

        do{
            tournamentList = getAllRegisteredTournament(session);
            System.out.println("Pick tournament to finish and erase");
            tournamentPrinter(tournamentList);
            System.out.println("[ " + tournamentList.size() + " ] To exit");
            temp = scanner.nextInt()-1;

            if(temp> 0 && temp<tournamentList.size()){
                session.beginTransaction();
                session.delete(tournamentList.get(temp));
                session.beginTransaction().commit();
            }
        }while (temp != tournamentList.size());
    }

    public void playMatchInTheTournament(Session session, Tournament tournament){
        Scanner scanner = new Scanner(System.in);
        MatchServices matchServices = new MatchServices();

        List<Match> matches;

        matchServices.playMatch(session);
        matches = matchServices.getMatchesPlayed(session);
        matches.get(matches.size()-1).setFriendlyMatch(false);
        tournament.getMatches().add(matches.get(matches.size()-1));

        calculateTournamentPoints(tournament,matches.get(matches.size()-1));

        session.beginTransaction();
        session.update(tournament);
        session.getTransaction().commit();
    }

    public void pickTeamsForTournament(Session session, Tournament tournament){
        Scanner scanner = new Scanner(System.in);
        TeamService teamService = new TeamService();

        List<Team> teams;
        int temp;

        do{
            teams = teamService.getTeams(session);
            teams.removeAll(tournament.getTeams());
            System.out.println("Pick team from the list to add to tournament ");
            teamService.printTeams(teams);
            System.out.println("[ " + teams.size() +" ] To save and exit");
            temp = scanner.nextInt()-1;

            if(temp> 0 && temp < teams.size()){
                tournament.getTeams().add(teams.get(temp));
                tournament.getTeams_points().put(teams.get(temp), 0);
            }else {
                System.out.println("Entered wrong value try again");
            }
        }while (temp != teams.size());

        session.beginTransaction();
        session.update(tournament);
        session.getTransaction().commit();
    }

    public void removeTeamFromTournament(Session session, Tournament tournament){
        Scanner scanner = new Scanner(System.in);

        TeamService teamService = new TeamService();

        int temp;
        do{
            System.out.println("Pick a team to be removed from tournament");
            teamService.printTeams(tournament.getTeams());
            System.out.println("[ " + tournament.getTeams().size() + " ] To save exit");
            temp = scanner.nextInt()-1;

            if(temp> 0 && temp < tournament.getTeams().size()){
                tournament.getTeams().remove(tournament.getTeams().get(temp));
                tournament.getTeams_points().remove(tournament.getTeams().get(temp));
            }else {
                System.out.println("Entered wrong value try again");
            }
        }while (temp != tournament.getTeams().size());

        session.beginTransaction();
        session.update(tournament);
        session.getTransaction().commit();
    }

    private void calculateTournamentPoints(Tournament tournament, Match match){
        if(match.getHomeTeamScore() > match.getAwayTeamScore()){
            tournament.getTeams_points().computeIfPresent(match.getHomeTeam(), (team, points) -> points + 5);
            tournament.getTeams_points().computeIfPresent(match.getAwayTeam(), (team, points) -> points + 3);
        }else {
            tournament.getTeams_points().computeIfPresent(match.getHomeTeam(), (team, points) -> points + 3);
            tournament.getTeams_points().computeIfPresent(match.getAwayTeam(), (team, points) -> points + 5);
        }
    }
}
