package Main.Controller;

import Main.App;
import Main.DAO.UserDAO;
import Main.POJO.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class StudentLogin {
    @FXML
    BorderPane mainPane;
    @FXML
    Button teacher;
    @FXML Button exit;
    private List<User> userList;
    public void Submit(ActionEvent e) throws IOException {
        userList = UserDAO.getAllStudent();

    }
    public void MouseEnter(MouseEvent e){
        if(e.getSource() == teacher) {
            teacher.setStyle("-fx-background-color: #4682b4");
            teacher.setTextFill(Color.WHITE);
        }
        if(e.getSource() == exit){
            exit.setStyle("-fx-background-color: #4682b4");
            exit.setTextFill(Color.WHITE);
        }
    }

    public void MouseExit(MouseEvent e){
        if(e.getSource() == teacher) {
            teacher.setStyle("-fx-background-color: #ffffff");
            teacher.setTextFill(Color.BLACK);
        }
        if(e.getSource() == exit) {
            exit.setStyle("-fx-background-color: #ffffff");
            exit.setTextFill(Color.BLACK);
        }
    }
    public void ChangeTeacher(ActionEvent e) throws IOException {
        App.changeScene("TeacherLogin","");
    }
    public void Exit(ActionEvent e)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Do you sure you want to exit?");
        if(alert.showAndWait().get() == ButtonType.OK)
        {
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.close();
        }
    }
}
