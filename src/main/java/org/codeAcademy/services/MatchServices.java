package org.codeAcademy.services;

import org.codeAcademy.model.Match;
import org.codeAcademy.model.Team;
import org.codeAcademy.model.Tournament;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class MatchServices {

    public void playMatch(Session session){
        Scanner scanner = new Scanner(System.in);

        TeamService teamService = new TeamService();

        List<Team> teamsList = teamService.getTeams(session);
        Match match = new Match();
        int temp;

        System.out.println("Pick Home team from the list");
        teamService.printTeams(teamsList);
        temp = scanner.nextInt()-1;

        if(temp < 0 || temp >= teamsList.size()){
            System.out.println("Home team will be picked randomly since u entered invalid index");
            temp = ThreadLocalRandom.current().nextInt(0 , teamsList.size());
        }
        match.setHomeTeam(teamsList.get(temp));
        teamsList.remove(teamsList.get(temp));

        System.out.println("Pick Away team from the list");
        teamService.printTeams(teamsList);
        temp = scanner.nextInt()-1;

        if(temp < 0 || temp >= teamsList.size()){
            System.out.println("Away team will be picked randomly since u entered invalid index");
            temp = ThreadLocalRandom.current().nextInt(0 , teamsList.size());
        }
        match.setAwayTeam(teamsList.get(temp));

        System.out.println("Enter Home team " + match.getHomeTeam().getName() + " score");
        match.setHomeTeamScore(scanner.nextInt());

        System.out.println("Enter Away team " + match.getAwayTeam().getName() +" score");
        match.setAwayTeamScore(scanner.nextInt());

        session.beginTransaction();
        session.save(match);
        session.getTransaction().commit();
    }

    public List<Match> getMatchesPlayed(Session session){
        return session.createQuery("from Match").list();
    }

    public void showMatchesPlayed(List<Match> matches){

        for (Match match:matches
             ) {
            System.out.println(match.getHomeTeam().getName() +" VS " + match.getAwayTeam().getName());
            System.out.println(match.getHomeTeamScore() + " : " + match.getAwayTeamScore());
            if(match.getHomeTeamScore()>match.getAwayTeamScore()){
                System.out.println(match.getHomeTeam().getName() + " was Victorious");
            }else {
                System.out.println(match.getAwayTeam().getName() + " was Victorious");
            }
        }
    }

    public void eraseMatchHistory(Session session,List<Match> matches){
        session.beginTransaction();

        for (Match match:matches
                ) {
            session.delete(match);
        }
        session.getTransaction().commit();
    }

    public void playMatch(Session session, Tournament tournament){
        Scanner scanner = new Scanner(System.in);

        TeamService teamService = new TeamService();

        List<Team> teamsList = new ArrayList<>(tournament.getTeams());
        Match match = new Match();
        int temp;

        System.out.println("Pick Home team from the list");
        teamService.printTeams(teamsList);
        temp = scanner.nextInt()-1;

        if(temp < 0 || temp >= teamsList.size()){
            System.out.println("Home team will be picked randomly since u entered invalid index");
            temp = ThreadLocalRandom.current().nextInt(0 , teamsList.size());
        }
        match.setHomeTeam(teamsList.get(temp));
        teamsList.remove(teamsList.get(temp));

        System.out.println("Pick Away team from the list");
        teamService.printTeams(teamsList);
        temp = scanner.nextInt()-1;

        if(temp < 0 || temp >= teamsList.size()){
            System.out.println("Away team will be picked randomly since u entered invalid index");
            temp = ThreadLocalRandom.current().nextInt(0 , teamsList.size());
        }
        match.setAwayTeam(teamsList.get(temp));

        System.out.println("Enter Home team " + match.getHomeTeam().getName() + " score");
        match.setHomeTeamScore(scanner.nextInt());

        System.out.println("Enter Away team " + match.getAwayTeam().getName() +" score");
        match.setAwayTeamScore(scanner.nextInt());

        session.beginTransaction();
        session.save(match);
        session.getTransaction().commit();
    }
}
