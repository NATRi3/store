package by.epam.store.entity;

import java.util.Date;

/**
 * The type User.
 */
public class User {
    private long id;
    private String email;
    private TypeRole role;
    private String name;
    private String imageName;
    private TypeStatus access;
    private Date registerDate;

    /**
     * Instantiates a new User.
     *
     * @param name  the name
     * @param email the email
     * @param role  the role
     */
    public User(String name, String email, TypeRole role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param id           the id
     * @param email        the email
     * @param role         the role
     * @param name         the name
     * @param imageName    the image name
     * @param access       the access
     * @param registerDate the register date
     */
    private User(long id, String email, TypeRole role, String name, String imageName, TypeStatus access, Date registerDate) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.name = name;
        this.imageName = imageName;
        this.access = access;
        this.registerDate = registerDate;
    }

    /**
     * Instantiates a new User.
     *
     * @param email the email
     */
    public User(String email) {
        this.email = email;
    }

    /**
     * Builder user builder.
     *
     * @return the user builder
     */
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public TypeRole getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(TypeRole role) {
        this.role = role;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets image name.
     *
     * @return the image name
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Sets image name.
     *
     * @param imageName the image name
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Gets access.
     *
     * @return the access
     */
    public TypeStatus getAccess() {
        return access;
    }

    /**
     * Sets access.
     *
     * @param access the access
     */
    public void setAccess(TypeStatus access) {
        this.access = access;
    }

    /**
     * Gets register date.
     *
     * @return the register date
     */
    public Date getRegisterDate() {
        return registerDate;
    }

    /**
     * Sets register date.
     *
     * @param registerDate the register date
     */
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * Sets parameters form.
     *
     * @param user the user
     */
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

    /**
     * The type User builder.
     */
    public static class UserBuilder {
        private long id;
        private String email;
        private TypeRole role;
        private String name;
        private String imageName;
        private TypeStatus access;
        private Date registerDate;

        /**
         * Instantiates a new User builder.
         */
        UserBuilder() {
        }

        /**
         * Id user builder.
         *
         * @param id the id
         * @return the user builder
         */
        public UserBuilder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Email user builder.
         *
         * @param email the email
         * @return the user builder
         */
        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        /**
         * Role user builder.
         *
         * @param role the role
         * @return the user builder
         */
        public UserBuilder role(TypeRole role) {
            this.role = role;
            return this;
        }

        /**
         * Name user builder.
         *
         * @param name the name
         * @return the user builder
         */
        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Image name user builder.
         *
         * @param imageName the image name
         * @return the user builder
         */
        public UserBuilder imageName(String imageName) {
            this.imageName = imageName;
            return this;
        }

        /**
         * Access user builder.
         *
         * @param access the access
         * @return the user builder
         */
        public UserBuilder access(TypeStatus access) {
            this.access = access;
            return this;
        }

        /**
         * Register date user builder.
         *
         * @param registerDate the register date
         * @return the user builder
         */
        public UserBuilder registerDate(Date registerDate) {
            this.registerDate = registerDate;
            return this;
        }

        /**
         * Build user.
         *
         * @return the user
         */
        public User build() {
            return new User(id, email, role, name, imageName, access, registerDate);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", email=" + this.email + ", role=" + this.role + ", name=" + this.name + ", imageName=" + this.imageName + ", access=" + this.access + ", registerDate=" + this.registerDate + ")";
        }
    }
}
