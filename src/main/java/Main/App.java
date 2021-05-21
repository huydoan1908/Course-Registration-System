package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        scene = new Scene(loadFXML("TeacherInfo").load());
        stage.setScene(scene);
        stage.setTitle("PORTAL");
        stage.show();
    }

    public static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("GUI/"+fxml + ".fxml"));
        return fxmlLoader;
    }

    public static void changeScene(String fxml,String title) throws IOException {
        Scene newScene = new Scene(loadFXML(fxml).load());
        if(title != "")
            stage.setTitle(title);
        stage.setScene(newScene);
    }

    public static void main(String[] args) {
        launch();
    }

}