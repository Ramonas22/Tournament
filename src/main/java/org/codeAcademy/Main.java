package org.codeAcademy;

import org.codeAcademy.model.Tournament;
import org.codeAcademy.services.MatchServices;
import org.codeAcademy.services.PlayerServices;
import org.codeAcademy.services.TeamService;
import org.codeAcademy.services.TournamentService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.text.ParseException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {

        SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        //Testing
        PlayerServices playerServices = new PlayerServices();
        TeamService teamService = new TeamService();
        MatchServices matchServices = new MatchServices();
        TournamentService tournamentService = new TournamentService();


        //List<Team> teamList = teamService.getTeams(session);
       // List<Match> matchList = matchServices.getMatchesPlayed(session);
        List<Tournament> tournamentList = tournamentService.getAllRegisteredTournament(session);


        //teamService.createTeam(session);
        //teamService.createTeam(session);
        //teamService.updateTeam(session);
        //teamService.removeTeamFromPool(session);
        //teamService.printTeams(teamService.getTeams(session));
        //matchServices.playMatch(session);
        //matchServices.playMatch(session);
        //matchServices.playMatch(session);



        //matchServices.eraseMatchHistory(session, matchList);
        //matchServices.showMatchesPlayed(matchList);

        //playerServices.addPlayerToThePool(session);
        //playerServices.addPlayerToThePool(session);
        //playerServices.addPlayerToThePool(session);
        //playerServices.addPlayerToThePool(session);
       // playerServices.addPlayerToThePool(session);
        //playerServices.updatePlayerInfoFo(session);
        //playerServices.removePlayerFromThePool(session);

        //teamService.pickPlayersForTeam(session);

        //teamService.printTeams(teamList);

        //playerServices.printPlayerPool(playerServices.getPlayerPool(session));

       // tournamentService.registerTournament(session);
       // tournamentService.registerTournament(session);

       // tournamentList = tournamentService.getAllRegisteredTournament(session);

        //tournamentService.tournamentPrinter(tournamentList);

        //tournamentService.updateTournamentInfo(session);


       // tournamentList.get(0).getTeams_points().clear();
        //tournamentService.updateTournamentInfo(session);

        tournamentList = tournamentService.getAllRegisteredTournament(session);

      //  tournamentService.removeTeamFromTournament(session, tournamentList.get(0));
       //tournamentService.pickTeamsForTournament(session, tournamentList.get(0));


        tournamentService.playMatchInTheTournament(session, tournamentList.get(0));
        tournamentService.playMatchInTheTournament(session, tournamentList.get(0));
        //tournamentService.playMatchInTheTournament(session, tournamentList.get(0));
        //tournamentService.playMatchInTheTournament(session, tournamentList.get(0));


    }
}