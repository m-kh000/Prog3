package core;

import java.time.LocalDate;

public class User {
    private String email;
    private String password;
    private boolean isManager;
    private LocalDate lastSeen;

    public User() {}
    
    public User(String email, String password, boolean isManager) {
        this.email = email;
        this.password = password;
        this.isManager = isManager;
        this.lastSeen = LocalDate.now();
    }

    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
    public String getLastSeen() {
        return this.lastSeen.toString();
    }

    public void resetLastSeen() {
        this.lastSeen = LocalDate.now();
    } 
    public boolean isManager() {
        return this.isManager;
    }
}
