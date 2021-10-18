package users;


import java.util.Set;

public class UserAddBindingModel {

    private String username;
    private String password;
    private String email;
    private Set<String> roles;


    public String getUsername() {
        return username;
    }

    public UserAddBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserAddBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserAddBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public UserAddBindingModel setRoles(Set<String> roles) {
        this.roles = roles;
        return this;
    }
}
