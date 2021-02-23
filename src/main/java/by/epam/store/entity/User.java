package by.epam.store.entity;

import by.epam.store.entity.type.TypeRole;
import by.epam.store.entity.type.TypeStatus;

import java.util.Date;
import java.util.Objects;

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

    public User() {
    }

    public User(long id, String email, TypeRole role, String name, String imageName, TypeStatus access, Date registerDate) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.name = name;
        this.imageName = imageName;
        this.access = access;
        this.registerDate = registerDate;
    }

    public User(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TypeRole getRole() {
        return role;
    }

    public void setRole(TypeRole role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public TypeStatus getAccess() {
        return access;
    }

    public void setAccess(TypeStatus access) {
        this.access = access;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email) && role == user.role && Objects.equals(name, user.name) && Objects.equals(imageName, user.imageName) && access == user.access && Objects.equals(registerDate, user.registerDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, role, name, imageName, access, registerDate);
    }
}
