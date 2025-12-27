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
    /**
     * No point of the method until we discuss about the email system.
     * @return
     */
    public UserInfo getUserInfo() {
        return new UserInfo(
                             this.email,
                             this.isManager ? "manager" : "supervisor",
                             this.lastSeen.toString()
                            );
    }

    public void resetLastSeen() {
        this.lastSeen = LocalDate.now();
    } 
    public boolean isManager() {
        return this.isManager;
    }

    /**
     * Will not be used until we decide what to do about the Email system.
     */
    public class UserInfo {
        private String email;
        private String role;
        private String lastSeen;

        public UserInfo(String email, String role, String lastSeen) {
            this.email = email;
            this.role = role;
            this.lastSeen = lastSeen;
        }

        public String getEmailInfo() {
            return this.email;
        }
        public String getRoleInfo() {
            return this.role;
        }
        public String getLastSeenInfo() {
            return this.lastSeen;
        }
    }
}
