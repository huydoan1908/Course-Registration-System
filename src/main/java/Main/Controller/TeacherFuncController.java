package Main.Controller;

import Main.App;
import Main.DAO.SemesterDAO;
import Main.POJO.Semester;
import Main.POJO.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class TeacherFuncController {
    @FXML
    Label username;

    private User cur;
    private Semester curSem;
    public void setData() throws IOException {
        int res = SemesterDAO.readFile();
        if(res == -1)
            curSem = null;
        else
            curSem = SemesterDAO.getSemesterById(res);
    }
    public void setUsername(User user, Semester sem)
    {
        if(user != null)
            username.setText(user.getName());
        cur=user;
        if(sem != null)
            curSem=sem;
    }
    @FXML
    private void teacher(ActionEvent e) throws IOException {
        FXMLLoader loader = App.loadFXML("TeacherInfo");
        loader.load();
        TeacherInfoController controller = loader.getController();
        controller.setUsernameText(cur,curSem);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.getRoot()));
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
    }
    @FXML
    private void subject(ActionEvent e) throws IOException {
        FXMLLoader loader = App.loadFXML("SubjectInfo");
        loader.load();
        SubjectInfoController controller = loader.getController();
        controller.setUsernameText(cur,curSem);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.getRoot()));
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
    }
    @FXML
    private void semester(ActionEvent e) throws IOException {
        FXMLLoader loader = App.loadFXML("SemesterInfo");
        loader.load();
        SemesterInfoController controller = loader.getController();
        controller.setUsernameText(cur,curSem);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.getRoot()));
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
    }
    @FXML
    private void classInfo(ActionEvent e) throws IOException {
        FXMLLoader loader = App.loadFXML("ClassInfo");
        loader.load();
        ClassInfoController controller = loader.getController();
        controller.setUsernameText(cur,curSem);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.getRoot()));
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
    }
    @FXML
    private void courseRegister(ActionEvent e) throws IOException {
        if(curSem == null)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Hãy chọn học kỳ hiện tại trước!");
            alert.showAndWait();
        }else {
            FXMLLoader loader = App.loadFXML("CourseRegister");
            loader.load();
            CourseRegisterController controller = loader.getController();
            controller.setUsernameText(cur, curSem);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.getRoot()));
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
        }
    }

    @FXML
    private void course(ActionEvent e) throws IOException {
        if(curSem == null)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Hãy chọn học kỳ hiện tại trước!");
            alert.showAndWait();
        }else {
            FXMLLoader loader = App.loadFXML("CourseInfo");
            loader.load();
            CourseInfoController controller = loader.getController();
            controller.setUsernameText(cur, curSem);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.getRoot()));
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
        }
    }
    @FXML
    private void account(ActionEvent e) throws IOException {
        FXMLLoader loader = App.loadFXML("Account");
        loader.load();
        AccountController controller = loader.getController();
        controller.setData(cur,curSem);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.getRoot()));
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
    }
    @FXML
    private void logout() throws IOException {
        App.changeScene("Login","");
    }
}
