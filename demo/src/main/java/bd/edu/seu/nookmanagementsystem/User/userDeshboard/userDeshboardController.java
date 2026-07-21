package bd.edu.seu.nookmanagementsystem.User.userDeshboard;

import bd.edu.seu.nookmanagementsystem.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static bd.edu.seu.nookmanagementsystem.User.loginAndRegister.userLoginController.Uname;

public class userDeshboardController implements Initializable {

    @FXML
    private AnchorPane sceneShower;

    @FXML
    private Label usernameLabel;

    private AnchorPane loadPage(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bd/edu/seu/nookmanagementsystem/" + fxml + ".fxml"));
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading nested view: " + fxml);
            return null;
        }
    }

    private void setScenePane(AnchorPane pane) {
        if (pane != null) {
            sceneShower.getChildren().setAll(pane);
        }
    }

    @FXML
    void userHome(ActionEvent event) {
        setScenePane(loadPage("userHomePage"));
    }

    @FXML
    void userBook(ActionEvent event) {
        setScenePane(loadPage("userBookPage"));
    }

    @FXML
    void userStatus(ActionEvent event) {
        setScenePane(loadPage("userStatusPage"));
    }

    @FXML
    void userHistory(ActionEvent event) {
        setScenePane(loadPage("userHistoryPage"));
    }

    @FXML
    void userLogout(ActionEvent event) {
        HelloApplication.changeScene("userLoginPage");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initially load home page overview
        setScenePane(loadPage("userHomePage"));
        usernameLabel.setText(Uname != null ? Uname : "Guest");
    }
}
