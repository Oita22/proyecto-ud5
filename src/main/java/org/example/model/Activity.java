package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class Activity {
    @BsonProperty(value = "_id")
    private ObjectId id;
    private String tittle;
    private String description;
    private boolean finished;
    private LocalDate date;
    private LocalTime time;

    // Many To One - Por referencia
    private ObjectId user;

    public static Activity createActivity(User user) {
        return new Activity(new ObjectId(),
                "Tittle",
                "Description",
                false,
                LocalDate.now(),
                LocalTime.now(),
                user.getId());
    }

    public static Activity createActivityWithOutDescription() {
        Activity activity = new Activity();
        activity.setId(new ObjectId());
        activity.setTittle("Title without Description and Time");
        activity.setFinished(false);
        activity.setDate(LocalDate.now());

        return activity;
    }
}
