package org.example;

import org.bson.types.ObjectId;
import org.example.dao.UserDAO;
import org.example.model.Profile;
import org.example.model.Role;
import org.example.model.RoleType;
import org.example.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GenerateData {
    private static UserDAO userDAO;
    private static Random random;
    public static void main(String[] args) {
        userDAO = new UserDAO();
        random = new Random();

        int number = random.nextInt();
        Role[] roleAvailable = {new Role(RoleType.ADMIN), new Role(RoleType.MODERATOR)};
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(RoleType.USER));

        int rdm;
        if ((rdm = random.nextInt(0, 3)) != 2)
            roles.add(roleAvailable[rdm]);

        User user = new User();
        user.setId(new ObjectId());
        user.setUsername("User" + number);
        user.setEmail("user" + number + "@email.com");
        user.setEnabled(random.nextBoolean());
        user.setProfile(new Profile(new ObjectId(), "User", String.valueOf(number),
                String.valueOf(99000000 + random.nextInt(1000000)), random.nextInt(1900, 2024)));
        user.setRoles(roles);

        userDAO.create(user);
    }
}
