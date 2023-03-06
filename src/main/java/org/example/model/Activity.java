package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.example.model.deserializer.ObjectIdDeserializer;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class Activity implements Serializable {
    //@BsonProperty(value = "_id")
    @JsonProperty("_id.oid")
    private ObjectId id;
    private String tittle;
    private String description;
    private boolean finished;
    private LocalDate date;
    private LocalTime time;
    @BsonProperty(value = "duration_time")
    private DurationTime durationTime;

    // Many To One - Por referencia
    private ObjectId user;

    public static Activity createActivity(User user) {
        return new Activity(new ObjectId(),
                "Tittle",
                "Description",
                false,
                LocalDate.now(),
                LocalTime.now(),
                DurationTime.MEDIUM,
                user.getId());
    }

    public static Activity createActivityWithOutDescription() {
        Activity activity = new Activity();
        activity.setId(new ObjectId());
        activity.setTittle("Title without Description and Time");
        activity.setFinished(false);
        activity.setDate(LocalDate.now());
        activity.setDurationTime(DurationTime.MEDIUM);

        return activity;
    }
}
