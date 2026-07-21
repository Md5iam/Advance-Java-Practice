package bd.edu.seu.nookmanagementsystem.User.Model;

public class User {
    private String userName;
    private String userEmail;
    private String userNumber;
    private String userAddress;
    private String userBirthday;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public User() {}
    
    public User(String userName, String userEmail, String userNumber, String userAddress, String userBirthday , String password) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userNumber = userNumber;
        this.userAddress = userAddress;
        this.userBirthday = userBirthday;
        this.password = password;
    }

    public User (String userName, String userEmail, String userNumber, String userAddress, String userBirthday){
        this.userName = userName;
        this.userBirthday = userBirthday;
        this.userNumber = userNumber;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
    }
}
