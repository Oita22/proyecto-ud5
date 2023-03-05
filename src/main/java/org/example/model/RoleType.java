package org.example.model;

import lombok.Data;
import lombok.ToString;

@ToString
public enum RoleType {
    USER(2),
    MODERATOR(1),
    ADMIN(0);

    private final int value;

    private RoleType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
