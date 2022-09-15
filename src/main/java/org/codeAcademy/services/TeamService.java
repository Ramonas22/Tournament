package org.codeAcademy.services;

import org.codeAcademy.model.Player;
import org.codeAcademy.model.Team;
import org.hibernate.Session;

import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;

public class TeamService {

    public void createTeam(Session session){
        Scanner scanner = new Scanner(System.in);

        Team team = new Team();

        System.out.println("Enter team name");
        team.setName(scanner.nextLine());

        System.out.println("Enter team budget ");
        team.setBudget(scanner.nextBigDecimal().setScale(2, RoundingMode.DOWN));

        System.out.println("Enter origin country of team");
        team.setCountry(scanner.nextLine());
        scanner.next();

        session.beginTransaction();
        session.save(team);
        session.getTransaction().commit();
    }

    public List<Team> getTeams(Session session){
        return session.createQuery("from Team").list();
    }

    public void updateTeam(Session session){
        Scanner scanner = new Scanner(System.in);

        List<Team> teamList = getTeams(session);
        int temp;
        Team team;

        while (true) {
            System.out.println("Pick team from list to update its information ");
            printTeams(teamList);
            System.out.println("Enter " + (teamList.size()+1) + " to exit");
            temp = scanner.nextInt() - 1;

            if (temp >= 0 && temp < teamList.size()) {

                team = teamList.get(temp);
                updateSingleTeam(team);

                session.beginTransaction();
                session.update(team);
                session.getTransaction().commit();

            } else if (temp == teamList.size()) {
                break;
            } else {
                System.out.println("Entered wrong index try again");
            }
        }
    }

    private void updateSingleTeam(Team team) {
        Scanner scanner = new Scanner(System.in);

        int temp;

        do {
            System.out.println("Pick attribute to update ");
            System.out.println("[ 1 ] Team name " + team.getName());
            System.out.println("[ 2 ] Team budget " + team.getBudget());
            System.out.println("[ 3 ] Team country " + team.getCountry());
            System.out.println("[ 4 ] To Exit");
            temp = scanner.nextInt()-1;

            if(temp >=0 && temp < 4) {
                switch (temp) {
                    case 0 -> {
                        System.out.println("Enter team name");
                        scanner.nextLine();
                        team.setName(scanner.nextLine());

                    }
                    case 1 -> {
                        System.out.println("Enter team budget");
                        team.setBudget(scanner.nextBigDecimal().setScale(2, RoundingMode.DOWN));}
                    case 2 -> {
                        System.out.println("Enter team country");
                        scanner.nextLine();
                        team.setCountry(scanner.nextLine());

                    }
                    default -> temp = -1;
                }
            }else if(temp == -1){
                break;
            }else {
                System.out.println("Entered wrong index");
            }
        }while (temp != -1);
    }

    public void printTeams(List<Team> teamList){
        PlayerServices playerServices = new PlayerServices();

        for (int i = 0; i < teamList.size(); i++) {
            System.out.println("[ "+ (i+1)+ " ] " + teamList.get(i).getName() + " "
                    + teamList.get(i).getBudget() + " " + teamList.get(i).getCountry());
            if(teamList.get(i).getPlayers().size()!=0){
                System.out.println("Players in team: ");
                playerServices.printPlayerPool(teamList.get(i).getPlayers());
            }
            System.out.print("\n----------------------------------------\n");
        }
    }

    public void removeTeamFromPool(Session session){
        Scanner scanner = new Scanner(System.in);

        List<Team> teamList;
        int temp;

        while (true) {
            teamList = getTeams(session);
            System.out.println("Pick team to remove from pool");
            printTeams(teamList);
            System.out.println("Enter " + (teamList.size() + 1) + " to exit");
            temp = scanner.nextInt()-1;

            if(temp >= 0 && temp < teamList.size()){
                session.beginTransaction();
                session.delete(teamList.get(temp));
                session.getTransaction().commit();
            }else if(temp == teamList.size()){
                break;
            }else {
                System.out.println("Entered wrong index");
            }
        }

    }

    public void pickPlayersForTeam(Session session){
        Scanner scanner = new Scanner(System.in);

        List<Team> teamList = getTeams(session);
        int temp;
        do {
            System.out.println("Pick team to add players ");
            printTeams(teamList);
            System.out.println("Enter " + (teamList.size() + 1) + " to exit");
            temp = scanner.nextInt()-1;

            if(temp >= 0 && temp < teamList.size()){
                pickPlayerForTeam(teamList.get(temp), session);

            }else if(temp == teamList.size()){
                break;
            }else{
                System.out.println("Wrong index");
            }

        }while (temp != 1);
    }

    private void pickPlayerForTeam(Team team, Session session) {
        Scanner scanner = new Scanner(System.in);
        PlayerServices playerServices = new PlayerServices();

        List<Player> playerPool;
        int temp;

        do {
            playerPool = playerServices.getPlayerPool(session);
            playerPool.removeIf(Player::isSignContract);

            System.out.println("Pick Players from the list to add to the team");
            playerServices.printPlayerPool(playerPool);
            System.out.println("Enter " + (playerPool.size() + 1) + " to exit");

            temp = scanner.nextInt()-1;

            if (temp >= 0 && temp < playerPool.size()) {
                playerPool.get(temp).setSignContract(true);

                team.getPlayers().add(playerPool.get(temp));
                playerPool.get(temp).setTeam(team);

                session.beginTransaction();
                session.save(team);
                session.save(playerPool.get(temp));
                session.getTransaction().commit();

            } else if (temp == playerPool.size()) {
                break;
            }else {
                System.out.println("Entered wrong value");
            }
        }while (temp != -1);
    }
}
