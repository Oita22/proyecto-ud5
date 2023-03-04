package org.example;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.example.dao.ActivityDAO;
import org.example.dao.EventDAO;
import org.example.dao.UserDAO;
import org.example.model.Profile;
import org.example.model.User;

import java.util.Arrays;
import java.util.Random;


public class Main {
    private static UserDAO userDAO;
    private static ActivityDAO activityDAO;
    private static EventDAO eventDAO;

    private static Random random;

    public static void main(String[] args) {
        // DAOs
        userDAO = new UserDAO();

        // Utils
        random = new Random();

        // Tests
        testCreateSimpleUser();
        //testUpdateSimpleUser();

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
        try {
            //userDAO.delete(user.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Read
        System.out.println(userDAO.read(user.getId()));
    }

    private static void testUpdateSimpleUser() {
        User user = userDAO.findByUsername("Oita");
        System.out.println(user);

        System.out.println("READ: " + userDAO.read(user.getId()));


        user.setEmail("oita" + random.nextInt() + "@email.com");
        user.getProfile().setName(user.getProfile().getName() + String.valueOf(random.nextInt()));

        userDAO.update(user);

        System.out.println("READ: " + userDAO.read(user.getId()));
        System.out.println("FINDBYUSERNAME: " + userDAO.findByUsername(user.getUsername()));
    }
}