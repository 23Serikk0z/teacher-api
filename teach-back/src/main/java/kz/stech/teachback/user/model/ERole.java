package kz.stech.teachback.user.model;

import lombok.Getter;

@Getter
public enum ERole {

    ROLE_TEACHER ("Преподаватель", "Мұғалім"),
    ROLE_STUDENT ("Студент", "Студент"),
    ROLE_ADMIN ("Администратор", "Әкімші");

    private String ruName;
    private String kkName;

    ERole(String ruName, String kkName) {  }
}
