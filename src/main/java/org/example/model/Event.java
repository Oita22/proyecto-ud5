package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
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

    public static Event createEvent(User user) {
        return new Event(
                new ObjectId(),
                "Tittle Owner",
                "Description",
                false,
                LocalDate.now(),
                user.getId(),
                new ArrayList<>());
    }

    public static Event createEventWithOutDescription(User user) {
        Event event = new Event();
        event.setId(new ObjectId());
        event.setTittle("Title - Owner - without Description and Time");
        event.setFinished(false);
        event.setDate(LocalDate.now());
        event.setOwner(user.getId());

        return event;
    }

    public void addUser(ObjectId userId) {
        this.users.add(userId);
    }
}
