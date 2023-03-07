package org.example.model;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
@ToString
public class User {
    @BsonProperty(value = "_id")
    private ObjectId id;
    private String username;
    private String email;
    private boolean enabled;

    // One To One - Incrustado
    private Profile profile;

    // One To Many - Incrustado
    private List<Role> roles;

    // One To Many - Por referencia
    private List<ObjectId> activities;

    // Many To Many - Por referencia
    private List<ObjectId> events;

    public User(ObjectId id, String username, String email, boolean enabled, Profile profile, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.profile = profile;
        this.roles = roles;
    }

    public void addActivity(ObjectId activityId) {
        if (activities == null)
            activities = new ArrayList<>();
        this.activities.add(activityId);
    }

    public void addEvent(ObjectId eventId) {
        if (events == null)
            events = new ArrayList<>();

        if (!events.contains(eventId))
            events.add(eventId);
    }

//    @Override
//    public String toString() {
//
//
//        String role = "";
//        for (Role role1 : roles)
//            if (RoleType.ADMIN == role1.getRole())
//                role = ", new Role(RoleType.ADMIN)";
//            else if (RoleType.MODERATOR == role1.getRole())
//                role = ", new Role(RoleType.MODERATOR)";
//
//        return "new User(new ObjectId(\"" + id + "\"), \"" + username + "\", \"" + email + "\", " + enabled + ", " +
//                "new Profile(new ObjectId(\"" + profile.getId() + "\"), \"" + profile.getName() + "\", \"" + profile.getSurname() + "\", \"" + profile.getPhone() + "\", " + profile.getBirthYear() + ")," +
//                "Arrays.asList(new Role(RoleType.USER)" + role + ")" +
//                ((activities == null && events == null ? "" : ",")) +
//                genActivities() +
//                genEvents() + ");";
//    }

    private String genActivities() {
        if (activities != null) {
            String aux = "Arrays.asList(";


            for (int i = 0; i < activities.size() - 1; i++) {
                aux += "new ObjectId(\"" + activities.get(i).toString() + "\")";
                if (i < activities.size() - 2)
                    aux += ", ";
            }

            aux += "),";
            return aux;
        }

        return "";
    }

    private String genEvents() {
        if (events != null) {
            String aux = "Arrays.asList(";

            for (int i = 0; i < events.size() - 1; i++) {
                aux += "new ObjectId(\"" + events.get(i).toString() + "\")";
                if (i < events.size() - 2)
                    aux += ", ";
            }

            aux += ")";
            return aux;
        }
        return "";
    }
}
