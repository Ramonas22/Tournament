package org.codeAcademy.services;

import org.codeAcademy.model.Player;
import org.codeAcademy.model.position;
import org.hibernate.Session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Scanner;

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

        player.setAge(Period.between(LocalDate.ofEpochDay(player.getAge()), LocalDate.now()).getYears());

        do {
            System.out.println("Pick position for player");
            System.out.println("[ 1 ] " + position.POINT_GUARD);
            System.out.println("[ 2 ] " + position.SHOOTING_GUARD);
            System.out.println("[ 3 ] " + position.SMALL_FORWARD);
            System.out.println("[ 4 ] " + position.POWER_FORWARD);
            System.out.println("[ 5 ] " + position.CENTER);
            temp = scanner.nextInt()-1;

            switch (temp){
                case 0-> player.setPosition(position.POINT_GUARD);
                case 1-> player.setPosition(position.SHOOTING_GUARD);
                case 2-> player.setPosition(position.SMALL_FORWARD);
                case 3-> player.setPosition(position.POWER_FORWARD);
                case 4-> player.setPosition(position.CENTER);
                default -> {
                    System.out.println("Index out of bounds try again");
                    temp = 6;
                }
            }
        }while (temp == 6);


        session.getTransaction().begin();
        session.save(player);
        session.getTransaction().commit();
    }

    public List<Player> getPlayerPool(Session session){
        return session.createQuery("from Player").list();
    }



}
