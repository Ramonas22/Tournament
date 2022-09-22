package org.codeAcademy;

import org.codeAcademy.model.Tournament;
import org.codeAcademy.services.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.text.ParseException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {
        ExecuteService.run();
    }
}