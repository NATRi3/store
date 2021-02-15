package by.epam.store.entity;

import by.epam.store.entity.type.TypeRole;
import by.epam.store.entity.type.TypeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private long id;
    private String email;
    private TypeRole role;
    private String name;
    private String imageName;
    private TypeStatus access;
    private Date registerDate;

    public User( String name, String email, TypeRole role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public User(String email) {
        this.email = email;
    }

    public void setParametersForm(User user){
        this.setId(user.getId());
        this.setEmail(user.getEmail());
        this.setImageName(user.getImageName());
        this.setAccess(user.getAccess());
        this.setRole(user.getRole());
        this.setName(user.getName());
        this.setRegisterDate(user.getRegisterDate());
    }
}
