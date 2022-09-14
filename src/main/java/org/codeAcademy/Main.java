package org.codeAcademy;

import org.codeAcademy.model.Team;
import org.codeAcademy.services.PlayerServices;
import org.codeAcademy.services.TeamService;
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

        List<Team> teamList = teamService.getTeams(session);
        //teamService.createTeam(session);
       // teamService.createTeam(session);
        //teamService.updateTeam(session);
        //teamService.removeTeamFromPool(session);
        //teamService.printTeams(teamService.getTeams(session));

        //teamService.pickPlayersForTeam(session);

        //playerServices.addPlayerToThePool(session);
        //playerServices.addPlayerToThePool(session);
        //playerServices.updatePlayerInfoFo(session);
        //playerServices.removePlayerFromThePool(session);

        playerServices.printPlayerPool(playerServices.getPlayerPool(session));
    }
}