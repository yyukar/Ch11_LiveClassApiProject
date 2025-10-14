package com.patika.enums;

public enum RoleType {
    ROLE_EMPLOYEE("Employee"),
    ROLE_ADMIN("Administrator");

    // client tarafına customer  ve Administrator olarak gitmesini istedik.
    private String name;

    RoleType(String name) {
        this.name = name;
    }
}
