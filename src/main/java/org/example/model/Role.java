package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @BsonProperty(value = "_id")
    private ObjectId id;
    private RoleType role;
    @BsonProperty(value = "role_value")
    private int roleValue;

    public Role(RoleType roleType) {
        this.id = new ObjectId();
        this.role = roleType;
        this.roleValue = roleType.getValue();
    }
}
