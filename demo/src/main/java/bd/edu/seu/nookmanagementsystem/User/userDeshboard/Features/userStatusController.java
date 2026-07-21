package bd.edu.seu.nookmanagementsystem.User.userDeshboard.Features;

import bd.edu.seu.nookmanagementsystem.User.Model.NookBook;
import bd.edu.seu.nookmanagementsystem.User.UserService.userService;
import bd.edu.seu.nookmanagementsystem.User.loginAndRegister.userLoginController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class userStatusController implements Initializable {

    @FXML
    private TableColumn<NookBook, String> colAuthor;

    @FXML
    private TableColumn<NookBook, String> colGenre;

    @FXML
    private TableColumn<NookBook, Number> colId;

    @FXML
    private TableColumn<NookBook, Number> colNo;

    @FXML
    private TableColumn<NookBook, String> colPrice;

    @FXML
    private TableColumn<NookBook, Number> colQty;

    @FXML
    private TableColumn<NookBook, String> colStatus;

    @FXML
    private TableColumn<NookBook, String> colTitle;

    @FXML
    private TableView<NookBook> bookDataTable;

    private ObservableList<NookBook> bookData = FXCollections.observableArrayList();
    private userService service = new userService();
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNo.setCellValueFactory(c -> new SimpleIntegerProperty(bookDataTable.getItems().indexOf(c.getValue()) + 1));
        colId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getBookId()));
        colTitle.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTitle()));
        colAuthor.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAuthor()));
        colGenre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGenre()));
        colQty.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getQuantity()));
        colPrice.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPrice()));
        colStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));

        loadBooks();
    }

    private void loadBooks() {
        try {
            List<NookBook> list = service.getBookList();
            bookData.clear();
            bookData.addAll(list);
            bookDataTable.setItems(bookData);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading book catalog table");
        }
    }

    @FXML
    void refreshCatalog(ActionEvent event) {
        loadBooks();
    }

    @FXML
    void borrowSelected(ActionEvent event) {
        NookBook selectedBook = bookDataTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            errorAlert.setTitle("Selection Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please select a book from the table to borrow.");
            errorAlert.showAndWait();
            return;
        }

        if (selectedBook.getQuantity() <= 0) {
            errorAlert.setTitle("Out of Stock");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("This book is currently out of stock.");
            errorAlert.showAndWait();
            return;
        }

        service.borrowBook(selectedBook.getBookId(), userLoginController.Umail);

        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Successfully borrowed: " + selectedBook.getTitle() + "\nYou can view it in your Reading History.");
        alert.showAndWait();

        // Reload data
        loadBooks();
    }
}
