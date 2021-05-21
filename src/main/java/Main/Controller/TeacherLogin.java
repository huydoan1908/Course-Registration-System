package Main.Controller;

import Main.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class TeacherLogin {
    @FXML
    BorderPane mainPane;
    @FXML
    Button student;
    @FXML Button exit;
    public void MouseEnter(MouseEvent e){
        if(e.getSource()==student) {
            student.setStyle("-fx-background-color: #4682b4");
            student.setTextFill(Color.WHITE);
        }
        if(e.getSource() == exit){
            exit.setStyle("-fx-background-color: #4682b4");
            exit.setTextFill(Color.WHITE);
        }
    }

    public void MouseExit(MouseEvent e){
        if(e.getSource()==student) {
            student.setStyle("-fx-background-color: #ffffff");
            student.setTextFill(Color.BLACK);
        }
        if(e.getSource() == exit){
            exit.setStyle("-fx-background-color: #ffffff");
            exit.setTextFill(Color.BLACK);
        }
    }

    public void ChangeStudent(ActionEvent e) throws IOException {
        App.changeScene("StudentLogin","");
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
