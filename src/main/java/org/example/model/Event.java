package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDate;

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

    public static Event createEvent() {
        return new Event(
                new ObjectId(),
                "Tittle",
                "Description",
                false,
                LocalDate.now()
        );
    }

    public static Event createEventWithOutDescription() {
        Event event = new Event();
        event.setId(new ObjectId());
        event.setTittle("Title without Description and Time");
        event.setFinished(false);
        event.setDate(LocalDate.now());

        return event;
    }
}
