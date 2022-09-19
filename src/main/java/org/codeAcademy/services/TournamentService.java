package org.codeAcademy.services;

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
        int temp;

        if(tournamentList.size() > 0){
            System.out.println("Pick Tournament which information you want to update");
            tournamentPrinter(tournamentList);
            temp = scanner.nextInt();

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

}
