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

}
