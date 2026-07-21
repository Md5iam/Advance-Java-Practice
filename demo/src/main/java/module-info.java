module bd.edu.seu.nookmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens bd.edu.seu.nookmanagementsystem to javafx.fxml;
    opens bd.edu.seu.nookmanagementsystem.User.loginAndRegister to javafx.fxml;
    opens bd.edu.seu.nookmanagementsystem.User.userDeshboard to javafx.fxml;
    opens bd.edu.seu.nookmanagementsystem.User.userDeshboard.Features to javafx.fxml;
    opens bd.edu.seu.nookmanagementsystem.User.Model to javafx.base;

    exports bd.edu.seu.nookmanagementsystem;
}