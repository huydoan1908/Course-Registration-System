package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        scene = new Scene(loadFXML("Login").load());
        stage.setScene(scene);
        Image icon = new Image("Main/img/IconHCMUS.png");
        stage.getIcons().add(icon);
        stage.setTitle("PORTAL");
        stage.show();
    }

    public static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("GUI/"+fxml + ".fxml"));
        return fxmlLoader;
    }

    public static void changeScene(String fxml,String title) throws IOException {
        Scene newScene = new Scene(loadFXML(fxml).load());
        if(title.compareTo("") != 0)
            stage.setTitle(title);
        stage.setScene(newScene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
    }

    public static void main(String[] args) {
        launch();
    }

}