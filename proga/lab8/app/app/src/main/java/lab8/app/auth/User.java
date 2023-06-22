package lab8.app.auth;

import java.io.Serializable;

import lab8.app.labwork.Person;

public class User implements Serializable{

    private long id;
    
    private String username;

    private String password;

    private Person profile;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Person profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getProfile() {
        return profile;
    }

    public void setProfile(Person profile) {
        this.profile = profile;
    }

}
