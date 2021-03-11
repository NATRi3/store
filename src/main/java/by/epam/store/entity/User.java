package by.epam.store.entity;

import java.util.Date;

public class User {
    private long id;
    private String email;
    private TypeRole role;
    private String name;
    private String imageName;
    private TypeStatus access;
    private Date registerDate;

    public User(String name, String email, TypeRole role) {
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

    public void setParametersForm(User user) {
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

        if (id != user.id) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (role != user.role) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (imageName != null ? !imageName.equals(user.imageName) : user.imageName != null) return false;
        if (access != user.access) return false;
        return registerDate != null ? registerDate.equals(user.registerDate) : user.registerDate == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (imageName != null ? imageName.hashCode() : 0);
        result = 31 * result + (access != null ? access.hashCode() : 0);
        result = 31 * result + (registerDate != null ? registerDate.hashCode() : 0);
        return result;
    }
}
