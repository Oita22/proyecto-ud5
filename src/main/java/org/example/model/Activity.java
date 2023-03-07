package org.example.model;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
@ToString
public class Activity {
    @BsonProperty(value = "_id")
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

    public Activity(ObjectId id, String tittle, String description, boolean finished, LocalDate date, ObjectId user) {
        this.id = id;
        this.tittle = tittle;
        this.description = description;
        this.finished = finished;
        this.date = date;
        this.user = user;
    }

    public Activity(ObjectId id, String tittle, String description, boolean finished, LocalDate date, LocalTime time, ObjectId user) {
        this.id = id;
        this.tittle = tittle;
        this.description = description;
        this.finished = finished;
        this.date = date;
        this.time = time;
        this.user = user;
    }

//    @Override
//    public String toString() {
//        String aux = "new Activity(new ObjectId(\"" + id.toString() + "\"), \"" + tittle + "\", \"" + description + "\", " + finished +
//                ", " + date() + time() + durationTime() + "new ObjectId(\"" + user.toString() + "\")));";
//
//        return aux;
//    }
    private String date() {
        if (date != null) {
            String aux = "LocalDate.of(";

            String[] numbers = date.toString().split("-");
            aux += numbers[0] + ", " + numbers[1] + ", " + numbers[2] + "), ";

            return aux;
        }
        return "";
    }

    private String time() {
        if (time != null) {
            String aux = "LocalTime.of(";

            String[] numbers = time.toString().split(":");
            aux += numbers[0] + ", " + numbers[1] + ", 0), ";

            return aux;
        }
        return "";
    }

    private String durationTime() {
        if (durationTime != null) {
            String aux = "DurationTime.";

            if (durationTime == DurationTime.SHORT)
                aux += "SHORT";

            else if (durationTime == DurationTime.MEDIUM)
                aux += "MEDIUM";

            else if (durationTime == DurationTime.LONG)
                aux += "LONG";

            aux += ", ";
            return aux;
        }
        return "";
    }
}
