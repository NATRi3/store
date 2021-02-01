package by.epam.store.entity;

import by.epam.store.entity.type.TypeAccess;
import by.epam.store.entity.type.TypeRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private long id;
    private String name;
    private String email;
    private Date registerDate;
    private String imageName;
    private TypeAccess access;
    private TypeRole role;


    public User(long id, String name, String email,  TypeRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role =role;
    }
}
