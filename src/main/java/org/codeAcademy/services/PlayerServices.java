package org.codeAcademy.services;

import org.codeAcademy.model.Player;
import org.codeAcademy.model.position;
import org.hibernate.Session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PlayerServices {


    //Creating new players and adding them to the database
    public void addPlayerToThePool(Session session) throws ParseException {
        Scanner scanner = new Scanner(System.in);

        Player player = new Player();
        int temp;

        System.out.println("Enter player name ");
        player.setName(scanner.nextLine());

        System.out.println("Enter player surname");
        player.setSurname(scanner.nextLine());

        System.out.println("Enter player date of birth yyyy-MM-dd");
        player.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine()));

        Date todayDate = Date.from(java.time.ZonedDateTime.now().toInstant());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String formatedDate = formatter.format(todayDate);
        Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(formatedDate);

        player.setAge((int) TimeUnit.DAYS.convert(Math.abs(
                date1.getTime() - player.getDateOfBirth().getTime()
        ), TimeUnit.MILLISECONDS) / 365);

        pickPlayerRole(player, false);


        session.getTransaction().begin();
        session.save(player);
        session.getTransaction().commit();
    }

    public List<Player> getPlayerPool(Session session) {
        return session.createQuery("from Player").list();
    }

    public void updatePlayerInfoFo(Session session) throws ParseException {
        Scanner scanner = new Scanner(System.in);

        List<Player> playerList;
        Player playerTemp;
        int temp;

        while (true) {
            playerList = getPlayerPool(session);
            System.out.println("Pick player from list which information needs to be updated");

            printPlayerPool(playerList);

            System.out.println("Enter " + (playerList.size() + 1) + " to EXIT");
            temp = scanner.nextInt() - 1;

            if (temp >= 0 && temp < playerList.size()) {
                playerTemp = playerList.get(temp);
                updateInfo(playerTemp, session);
            } else if (temp == playerList.size()) {
                break;
            } else {
                System.out.println("Entered number out of range pick another one");
            }

        }
    }


    private void updateInfo(Player playerTemp, Session session) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int temp;
        Player player = playerTemp;

        do {
            System.out.println("Pick attribute to update: ");
            System.out.println("[ 1 ] Name " + playerTemp.getName());
            System.out.println("[ 2 ] Surname " + playerTemp.getSurname());
            System.out.println("[ 3 ] Date of birth " + playerTemp.getDateOfBirth());
            System.out.println("[ 4 ] Position " + playerTemp.getPosition());
            System.out.println("[ 5 ] Exit and update");
            System.out.println("[ 6 ] Exit without update");
            temp = scanner.nextInt() - 1;

            switch (temp) {
                case 0 -> {
                    System.out.println("Enter name ");
                    scanner.next();
                    playerTemp.setName(scanner.nextLine());
                    temp = -1;
                    break;
                }
                case 1 -> {
                    System.out.println("Enter surname");
                    scanner.next();
                    playerTemp.setSurname(scanner.nextLine());
                    temp = -1;
                    break;
                }
                case 2 -> {
                    System.out.println("Enter player date of birth yyyy-MM-dd");
                    playerTemp.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(scanner.next()));

                    Date todayDate = Date.from(java.time.ZonedDateTime.now().toInstant());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String formatedDate = formatter.format(todayDate);
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(formatedDate);

                    playerTemp.setAge((int) TimeUnit.DAYS.convert(Math.abs(
                            date1.getTime() - playerTemp.getDateOfBirth().getTime()
                    ), TimeUnit.MILLISECONDS) / 365);

                    temp = -1;
                    break;
                }
                case 3 -> {
                    pickPlayerRole(playerTemp, true);
                    temp = -1;
                    break;
                }
                case 4 -> {
                    session.beginTransaction();
                    session.update(playerTemp);
                    session.getTransaction().commit();
                    break;
                }
                case 5 -> {
                    playerTemp = player;
                    break;
                }
                default -> {
                    System.out.println("Entered index out of bounds try again ");
                    temp = -1;
                }
            }

        } while (temp == -1);
    }

    private Player pickPlayerRole(Player player, boolean update) {
        Scanner scanner = new Scanner(System.in);
        int temp;

        if (update) {
            System.out.println("[ 1 ] " + position.POINT_GUARD);
            System.out.println("[ 2 ] " + position.SHOOTING_GUARD);
            System.out.println("[ 3 ] " + position.SMALL_FORWARD);
            System.out.println("[ 4 ] " + position.POWER_FORWARD);
            System.out.println("[ 5 ] " + position.CENTER);
            temp = scanner.nextInt() - 1;
            if (temp >= 0 && temp < 5) {
                switch (temp) {
                    case 0 -> player.setPosition(position.POINT_GUARD);
                    case 1 -> player.setPosition(position.SHOOTING_GUARD);
                    case 2 -> player.setPosition(position.SMALL_FORWARD);
                    case 3 -> player.setPosition(position.POWER_FORWARD);
                    case 4 -> player.setPosition(position.CENTER);
                    default -> System.out.println("Index out of bounds, position will stay un changed");
                }
            }
        } else {
            do {
                System.out.println("Pick position for player");
                System.out.println("[ 1 ] " + position.POINT_GUARD);
                System.out.println("[ 2 ] " + position.SHOOTING_GUARD);
                System.out.println("[ 3 ] " + position.SMALL_FORWARD);
                System.out.println("[ 4 ] " + position.POWER_FORWARD);
                System.out.println("[ 5 ] " + position.CENTER);
                temp = scanner.nextInt() - 1;

                switch (temp) {
                    case 0 -> player.setPosition(position.POINT_GUARD);
                    case 1 -> player.setPosition(position.SHOOTING_GUARD);
                    case 2 -> player.setPosition(position.SMALL_FORWARD);
                    case 3 -> player.setPosition(position.POWER_FORWARD);
                    case 4 -> player.setPosition(position.CENTER);
                    default -> {
                        System.out.println("Index out of bounds try again");
                        temp = 6;
                    }
                }
            } while (temp == 6);
        }
        return player;
    }

    public void printPlayerPool(List<Player> playerList) {
        Player playerTemp;

        for (int i = 0; i < playerList.size(); i++) {
            playerTemp = playerList.get(i);
            System.out.print("[ " + (i + 1) + " ] " + playerTemp.getName() + " "
                    + playerTemp.getSurname() + " " + playerTemp.getAge() + " " + playerTemp.getPosition());
            if (playerTemp.isSignContract()) {
                System.out.print(" player has contract\n");
            } else {
                System.out.print(" player is open for proposal\n\n\n");
            }
        }
    }

    public void removePlayerFromThePool(Session session) {
        Scanner scanner = new Scanner(System.in);

        List<Player> playerList;
        int temp;

        while (true) {
            playerList = getPlayerPool(session);
            System.out.println("Pick player to remove from the list");
            printPlayerPool(playerList);
            System.out.println("Enter " + (playerList.size() + 1) + " to exit");
            temp = scanner.nextInt() - 1;
            if (temp == playerList.size()) {
                break;
            } else if (temp >= 0 && temp < playerList.size()) {
                session.beginTransaction();
                session.delete(playerList.get(temp));
                session.getTransaction().commit();
            } else {
                System.out.println("Entered wrong index try again");
            }
        }
    }
}
