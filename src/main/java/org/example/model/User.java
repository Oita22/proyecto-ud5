package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class User {
    @BsonProperty(value = "_id")
    private ObjectId id;
    private String username;
    private String email;

    // One To One - Incrustado
    private Profile profile;

    // One To Many - Incrustado
    private List<Role> roles;

    // One To Many - Por referencia
    //private List<Activity> activities; -> Buscar como, relacionando los ObjectId imagino

    public static User createUser() {
        return new User(
                new ObjectId(),
                "Oita",
                "oita@email.com",
                new Profile(new ObjectId(), "Iago", "Oitavén", "123456789", 2000),
                Arrays.asList(new Role(new ObjectId(), "ADMIN", 0), new Role(new ObjectId(), "MOD", 1)));
    }
}
