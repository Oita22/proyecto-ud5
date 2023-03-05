package org.example;

import com.mongodb.client.FindIterable;
import org.bson.types.ObjectId;
import org.example.dao.ActivityDAO;
import org.example.dao.EventDAO;
import org.example.dao.UserDAO;
import org.example.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Main {
    private static UserDAO userDAO;
    private static ActivityDAO activityDAO;
    private static EventDAO eventDAO;

    private static Random random;

    public static void main(String[] args) {
        random = new Random();

        userDAO = new UserDAO();
        eventDAO = new EventDAO();
        activityDAO = new ActivityDAO();

        // Búsqueda de actividades por usuario y estado
        FindIterable<Activity> activities = activityDAO.findByUserIdAndFinished(
                userDAO.findByUsername("Oita").getId(),
                true
        );

        for (Activity activity : activities)
            System.out.println(activity);

        // Búsqueda de eventos por su propietario
        FindIterable<Event> events = eventDAO.findByOwnerId(userDAO.findByUsername("User1629524261").getId());
        for (Event event : events)
            System.out.println(event);

        // Todos los usuarios
        FindIterable<User> users = userDAO.findAll();
        for (User user : users)
            System.out.println(user);

        // Búsqueda de un usuario por su username y estado de actividad
        System.out.println(userDAO.findByUsernameAndEnable("Oita", true));

        // Búsqueda de los eventos en los que está inscrito un usuario
        FindIterable<Event> events1 = eventDAO.findByUserInEvents(userDAO.findByUsername("Oita").getId());
        for (Event event : events1)
            System.out.println(event);

        System.out.println("\n".repeat(3));
        // Búsqueda de los Eventos comprendidos entre dos fechas y que al menos tienen un usuario inscrito
        FindIterable<Event> events2 = eventDAO.findByBetweenDateAndAtLeastOneUser(LocalDate.of(2022, 1, 1), LocalDate.now());
        for (Event event : events2)
            System.out.println(event);

    }

}