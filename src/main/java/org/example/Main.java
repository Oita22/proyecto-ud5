package org.example;

import org.example.dao.ActivityDAO;
import org.example.dao.EventDAO;
import org.example.dao.UserDAO;
import org.example.model.Activity;
import org.example.model.Event;
import org.example.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;


public class Main {
    private static UserDAO userDAO;
    private static ActivityDAO activityDAO;
    private static EventDAO eventDAO;

    private static Random random;

    public static void main(String[] args) {
        random = new Random();

        //testUser();
        testActivity();
        //testEvent();
    }

    private static void testActivity() {
        activityDAO = new ActivityDAO();

        testCreateActivity();
        //testUpdateActivity();
        //testDeleteActivity();
    }

    private static void testEvent() {
        eventDAO = new EventDAO();

        testCreateEvent();
        //testUpdateEvent();
        //testDeleteEvent();
    }

    private static void testUser() {
        userDAO = new UserDAO();

        //testCreateSimpleUser();
        //testUpdateSimpleUser();
        //testDeleteSimpleUser();
    }

    private static void testCreateSimpleUser() {
        User user = User.createUser();

        System.out.println("original: " + user);

        // Create
        userDAO.create(user);

        // Read
        System.out.println("CREATE: " + userDAO.read(user.getId()));

        // Update > Si no se cambia nada cuando se actualiza, salta la excepción puesta en UserDAO
        try {
            userDAO.update(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Read
        System.out.println(userDAO.read(user.getId()));

        // Delete
        /*try {
            userDAO.delete(user.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/

        // Read
        System.out.println(userDAO.read(user.getId()));
    }

    private static void testUpdateSimpleUser() {
        User user = userDAO.findByUsername("Oita");
        System.out.println(user);

        System.out.println("READ: " + userDAO.read(user.getId()));


        user.setEmail("oita" + random.nextInt() + "@email.com");
        user.getProfile().setName(user.getProfile().getName() + String.valueOf(random.nextInt()));
        user.getRoles().remove(1);

        userDAO.update(user);

        System.out.println("READ: " + userDAO.read(user.getId()));
        System.out.println("FINDBYUSERNAME: " + userDAO.findByUsername(user.getUsername()));
    }

    private static void testDeleteSimpleUser() {
        User user = userDAO.findByUsername("Oita");
        System.out.println(user);

        userDAO.delete(user.getId());
    }



    private static void testCreateActivity() {
        Activity activity = Activity.createActivity();
        Activity activity1 = Activity.createActivityWithOutDescription();

        // Create
        activityDAO.create(activity);
        activityDAO.create(activity1);

        // Read
        System.out.println("READ: " + activityDAO.read(activity.getId()));
        System.out.println("READ: " + activityDAO.read(activity1.getId()));
    }
    private static void testUpdateActivity() {
        Activity activity = activityDAO.findByTittle("Tittle");
        Activity activity1 = activityDAO.findByTittle("Title without Description and Time");

        System.out.println("PRE UPDATE " + activity);
        System.out.println("PRE UPDATE " + activity1);

        activity.setDescription(activity.getDescription() + random.nextInt());
        activity1.setDescription(activity1.getDescription() + random.nextInt());
        activity1.setTime(LocalTime.now());

        activityDAO.update(activity);
        activityDAO.update(activity1);

        System.out.println("POST UPDATE " + activityDAO.findByTittle("Tittle"));
        System.out.println("POST UPDATE " + activityDAO.findByTittle("Title without Description and Time"));
    }
    private static void testDeleteActivity() {
        Activity activity = activityDAO.findByTittle("Tittle");
        Activity activity1 = activityDAO.findByTittle("Title without Description and Time");

        System.out.println("TO DELETE " + activity);
        System.out.println("TO DELETE " + activity1);

        activityDAO.delete(activity.getId());
        activityDAO.delete(activity1.getId());

        System.out.println("POST DELETE " + activityDAO.findByTittle("Tittle"));
        System.out.println("POST DELETE " + activityDAO.findByTittle("Title without Description and Time"));
    }



    private static void testCreateEvent() {
        Event event = Event.createEvent();
        Event event1 = Event.createEventWithOutDescription();

        // Create
        eventDAO.create(event);
        eventDAO.create(event1);

        // Read
        System.out.println("READ: " + eventDAO.read(event.getId()));
        System.out.println("READ: " + eventDAO.read(event1.getId()));
    }
    private static void testUpdateEvent() {
        Event event = eventDAO.findByTittle("Tittle");
        Event event1 = eventDAO.findByTittle("Title without Description and Time");

        System.out.println("PRE UPDATE " + event);
        System.out.println("PRE UPDATE " + event1);

        event.setDescription(event.getDescription() + random.nextInt());
        event1.setDescription(event1.getDescription() + random.nextInt());
        event1.setDate(LocalDate.now());

        eventDAO.update(event);
        eventDAO.update(event1);

        System.out.println("POST UPDATE " + eventDAO.findByTittle("Tittle"));
        System.out.println("POST UPDATE " + eventDAO.findByTittle("Title without Description and Time"));
    }
    private static void testDeleteEvent() {
        Event event = eventDAO.findByTittle("Tittle");
        Event event1 = eventDAO.findByTittle("Title without Description and Time");

        System.out.println("TO DELETE " + event);
        System.out.println("TO DELETE " + event1);

        eventDAO.delete(event.getId());
        eventDAO.delete(event1.getId());

        System.out.println("POST DELETE " + eventDAO.findByTittle("Tittle"));
        System.out.println("POST DELETE " + eventDAO.findByTittle("Title without Description and Time"));
    }
}