package core;

public class User {
    private String email;
    private String password;
    private boolean isManager;

    public User(String email, String password, boolean isManager) {
        this.email = email;
        this.password = password;
        this.isManager = isManager;
    }

    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
    public String getFileFormat() {
        return this.getEmail() + ", "
                + this.getPassword() + ", "
                + (this.isManager() ? "true\n" : "false");
    }

    public boolean isManager() {
        return this.isManager;
    }
}
