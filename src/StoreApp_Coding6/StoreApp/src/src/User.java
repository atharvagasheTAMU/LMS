public class User {
    private int userID;
    private String username;
    private String password;
    private String fullName;
    private boolean isManager;
    
    public User(int userID, String username, String password, String fullName, boolean isManager) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.isManager = isManager;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean getisManager() {
        return isManager;
    }

    public void setisManager(boolean isManager) {
        this.isManager = isManager;
    }

}
