package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @BsonProperty(value = "_id")
    private ObjectId id;
    private String name;
    private String surname;
    private String phone;
    @BsonProperty(value = "birth_year")
    private int birthYear;
}
