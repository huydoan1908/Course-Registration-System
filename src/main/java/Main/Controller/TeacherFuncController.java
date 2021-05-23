package Main.Controller;

import Main.App;
import Main.POJO.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class TeacherFuncController {
    @FXML
    Label username;

    private User cur;
    public void setUsername(User user)
    {
        username.setText(user.getName());
        cur=user;
    }
    @FXML
    public void teacher(ActionEvent e) throws IOException {
        FXMLLoader loader = App.loadFXML("TeacherInfo");
        loader.load();
        TeacherInfoController controller = loader.getController();
        controller.setUsernameText(cur);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.getRoot()));
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
    }
    @FXML
    public void subject(ActionEvent e) throws IOException {
        FXMLLoader loader = App.loadFXML("SubjectInfo");
        loader.load();
        SubjectInfoController controller = loader.getController();
        controller.setUsernameText(cur);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.getRoot()));
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
    }
    @FXML
    public void logout() throws IOException {
        App.changeScene("Login","");
    }
}
