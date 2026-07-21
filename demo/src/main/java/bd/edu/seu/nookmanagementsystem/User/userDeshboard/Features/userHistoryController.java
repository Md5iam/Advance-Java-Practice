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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class userHistoryController implements Initializable {

    @FXML
    private TableColumn<NookBook, String> colAuthor;

    @FXML
    private TableColumn<NookBook, String> colBorrowDate;

    @FXML
    private TableColumn<NookBook, Number> colId;

    @FXML
    private TableColumn<NookBook, Number> colNo;

    @FXML
    private TableColumn<NookBook, String> colReturnDate;

    @FXML
    private TableColumn<NookBook, String> colStatus;

    @FXML
    private TableColumn<NookBook, String> colTitle;

    @FXML
    private TableView<NookBook> historyDataTable;

    private ObservableList<NookBook> historyData = FXCollections.observableArrayList();
    private userService service = new userService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNo.setCellValueFactory(c -> new SimpleIntegerProperty(historyDataTable.getItems().indexOf(c.getValue()) + 1));
        colId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getBookId()));
        colTitle.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTitle()));
        colAuthor.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAuthor()));
        colBorrowDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBorrowDate()));
        colReturnDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getReturnDate()));
        colStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));

        loadHistory();
    }

    private void loadHistory() {
        try {
            List<NookBook> list = service.getBorrowHistory(userLoginController.Umail);
            historyData.clear();
            historyData.addAll(list);
            historyDataTable.setItems(historyData);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading history table");
        }
    }

    @FXML
    void refreshHistory(ActionEvent event) {
        loadHistory();
    }
}
