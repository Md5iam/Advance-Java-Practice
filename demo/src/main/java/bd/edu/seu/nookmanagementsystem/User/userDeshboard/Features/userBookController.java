package bd.edu.seu.nookmanagementsystem.User.userDeshboard.Features;

import bd.edu.seu.nookmanagementsystem.User.Model.NookBook;
import bd.edu.seu.nookmanagementsystem.User.UserService.userService;
import bd.edu.seu.nookmanagementsystem.User.loginAndRegister.userLoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class userBookController implements Initializable {

    @FXML
    private TextField authorField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField genreField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField titleField;

    private userService service = new userService();
    private Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    private Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private int generateBookId() {
        Random rand = new Random();
        int min = 10000;
        int max = 99999;
        return min + rand.nextInt(max - min + 1);
    }

    @FXML
    void submitBook(ActionEvent event) {
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();
        String description = descriptionField.getText();
        String price = priceField.getText();
        String qtyText = quantityField.getText();

        if (title.isEmpty() || author.isEmpty() || qtyText.isEmpty()) {
            errorAlert.setTitle("Validation Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Title, Author, and Quantity are required fields.");
            errorAlert.showAndWait();
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(qtyText);
        } catch (NumberFormatException e) {
            errorAlert.setTitle("Validation Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Quantity must be a valid integer.");
            errorAlert.showAndWait();
            return;
        }

        int bookId = generateBookId();
        NookBook book = new NookBook(
                bookId,
                title,
                author,
                genre,
                description,
                quantity,
                price.isEmpty() ? "0" : price,
                "Available",
                userLoginController.Umail
        );

        service.insertBook(book);

        infoAlert.setTitle("Success");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText("Nook Book successfully added!\nBook ID: " + bookId);
        infoAlert.showAndWait();

        // Clear fields
        titleField.clear();
        authorField.clear();
        genreField.clear();
        descriptionField.clear();
        priceField.clear();
        quantityField.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization if needed
    }
}
