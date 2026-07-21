package bd.edu.seu.nookmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        HelloApplication.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("userLoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1150, 670); // (width, height)
        stage.setTitle("Nook Book Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void changeScene(String fxml){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1150, 670);
            stage.setScene(scene);
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Problem in changeScene method: " + e.getMessage());
        }
    }
}
