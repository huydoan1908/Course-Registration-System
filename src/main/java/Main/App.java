package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("StudentLogin"));
        setCSS("style");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("GUI/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static String loadCSS(String css)
    {
        return App.class.getResource("GUI/"+css+".css").toExternalForm();
    }
    public static void setCSS(String css)
    {
        scene.getStylesheets().add(loadCSS(css));
    }

    public static void main(String[] args) {
        launch();
    }

}