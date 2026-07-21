package bd.edu.seu.nookmanagementsystem.User.userDeshboard.Features;

import bd.edu.seu.nookmanagementsystem.User.loginAndRegister.userLoginController;
import bd.edu.seu.nookmanagementsystem.utill.connectionSingleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class userHomeController implements Initializable {

    @FXML
    private Label totalBooksLabel;

    @FXML
    private Label totalAvailableLabel;

    @FXML
    private Label userBorrowedLabel;

    @FXML
    private Label welcomeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeLabel.setText("Welcome back, " + userLoginController.Uname + "!");
        loadStatistics();
    }

    private void loadStatistics() {
        Connection connection = connectionSingleton.getConnection();
        if (connection == null) return;

        try {
            // Count total book titles
            String q1 = "SELECT COUNT(*) FROM nookbooks";
            PreparedStatement pst1 = connection.prepareStatement(q1);
            ResultSet rs1 = pst1.executeQuery();
            if (rs1.next()) {
                totalBooksLabel.setText(String.valueOf(rs1.getInt(1)));
            }

            // Sum total stock quantity
            String q2 = "SELECT SUM(quantity) FROM nookbooks";
            PreparedStatement pst2 = connection.prepareStatement(q2);
            ResultSet rs2 = pst2.executeQuery();
            if (rs2.next()) {
                totalAvailableLabel.setText(String.valueOf(rs2.getInt(1)));
            }

            // Count current user's borrowed books
            String q3 = "SELECT COUNT(*) FROM borrow_history WHERE userEmail = ? AND status = 'Borrowed'";
            PreparedStatement pst3 = connection.prepareStatement(q3);
            pst3.setString(1, userLoginController.Umail);
            ResultSet rs3 = pst3.executeQuery();
            if (rs3.next()) {
                userBorrowedLabel.setText(String.valueOf(rs3.getInt(1)));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading dashboard statistics");
        }
    }
}
