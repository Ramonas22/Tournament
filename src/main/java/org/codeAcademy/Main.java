package org.codeAcademy;

import org.codeAcademy.services.PlayerServices;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {

        SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        //Testing
        PlayerServices playerServices = new PlayerServices();

        //playerServices.addPlayerToThePool(session);
        //playerServices.addPlayerToThePool(session);
        playerServices.updatePlayerInfoFo(session);
        playerServices.removePlayerFromThePool(session);

        playerServices.printPlayerPool(playerServices.getPlayerPool(session));
    }
}