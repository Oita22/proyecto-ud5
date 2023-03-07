package org.example.model;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
@ToString
public class Event {
    @BsonProperty(value = "_id")
    private ObjectId id;
    private String tittle;
    private String description;
    private boolean finished;
    private LocalDate date;

    // Many To One - Por referencia
    private ObjectId owner;

    // Many To Many - Por referencia
    private List<ObjectId> users;

    public Event(ObjectId id, String tittle, String description, boolean finished, LocalDate date) {
        this.id = id;
        this.tittle = tittle;
        this.description = description;
        this.finished = finished;
        this.date = date;
    }

    public Event(ObjectId id, String tittle, String description, boolean finished, LocalDate date, List<ObjectId> users) {
        this.id = id;
        this.tittle = tittle;
        this.description = description;
        this.finished = finished;
        this.date = date;
        this.users = users;
    }

    public void addUser(ObjectId userId) {
        if (users == null)
            users = new ArrayList<>();

        if (!users.contains(userId))
            users.add(userId);
    }

//    @Override
//    public String toString() {
//        return "new Event(new ObjectId(\"" + id.toString() + "\"), \"" + tittle + "\", \"" + description + "\", " + finished +
//                ", " + date() + genOwner() + genUsers() + "));";
//    }

    private String date() {
        if (date != null) {
            String aux = "LocalDate.of(";

            String[] numbers = date.toString().split("-");
            aux += numbers[0] + ", " + numbers[1] + ", " + numbers[2] + ")";

            return aux;
        }
        return "";
    }

    private String genUsers() {
        if (users != null) {
            String aux = ", Arrays.asList(";

            for (int i = 0; i < users.size() - 1; i++) {
                aux += "new ObjectId(\"" + users.get(i).toString() + "\")";
                if (i < users.size() - 2)
                    aux += ", ";
            }

            aux += ")";
            return aux;
        }
        return "";
    }

    private String genOwner() {
        if (owner != null) {
            return ", new ObjectId(\"" + owner.toString() + "\")";
        }
        return "";
    }
}
