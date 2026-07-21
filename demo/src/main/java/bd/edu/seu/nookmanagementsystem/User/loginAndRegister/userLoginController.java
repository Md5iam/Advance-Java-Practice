package bd.edu.seu.nookmanagementsystem.User.loginAndRegister;

import bd.edu.seu.nookmanagementsystem.HelloApplication;
import bd.edu.seu.nookmanagementsystem.utill.connectionSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class userLoginController implements Initializable {

    @FXML
    private TextField customerEmail;

    @FXML
    private TextField customerPassword;

    public static String Umail , Uname;

    Alert alert = new Alert(Alert.AlertType.ERROR);

    @FXML
    void customerLogin(ActionEvent event) {
        Umail = customerEmail.getText();
        String email = customerEmail.getText();
        String password = customerPassword.getText();
        try {
            Connection connection = connectionSingleton.getConnection();
            if (connection == null) {
                alert.setTitle("Database Error");
                alert.setHeaderText(null);
                alert.setContentText("Connection to MySQL failed. Make sure server is running and schema is loaded.");
                alert.showAndWait();
                return;
            }
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM userdetails";
            ResultSet resultSet = statement.executeQuery(query);
            boolean ok = false;
            while (resultSet.next()) {
                String tmpEmail = resultSet.getString("userEmail");
                String tmpPassword = resultSet.getString("userPassword");
                if (tmpEmail.equals(email) && tmpPassword.equals(password)) {
                    Uname = resultSet.getString("userName");
                    ok = true;
                    break;
                }
            }
            if (ok){
                HelloApplication.changeScene("userDeshboard");
            } else {
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Wrong Email or Password");
                alert.showAndWait();
            }
        } catch (Exception ex ){
            ex.printStackTrace();
            System.out.println("Error in userLoginController: " + ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization logic if any
    }
}
