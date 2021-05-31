package Main.Controller;

import Main.App;
import Main.DAO.UserDAO;
import Main.POJO.Semester;
import Main.POJO.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class AccountController {
    @FXML
    private TextField nameText;
    @FXML
    private TextField idText;
    @FXML
    private DatePicker dayText;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private Button edit;
    @FXML
    private Button save;

    @FXML
    private Label userText;
    private User user;
    private Semester sem;

    public void setData(User user, Semester sem) {
        this.user = user;
        this.sem = sem;
        nameText.setText(user.getName());
        idText.setText(user.getId());
        dayText.setValue(user.getBirthday().toLocalDate());
        if (user.getGender())
            female.setSelected(true);
        else
            male.setSelected(true);
        userText.setText(user.getName());
    }

    @FXML
    private void update(ActionEvent e) {
        edit.setVisible(false);
        save.setVisible(true);
        nameText.setDisable(false);
        dayText.setDisable(false);
        male.setDisable(false);
        female.setDisable(false);
    }

    @FXML
    private void save(ActionEvent e) {
        String name = nameText.getText();
        LocalDate day = dayText.getValue();
        if (name.isEmpty() || day == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Hãy điền tất cả thông tin");
            alert.showAndWait();
        } else {
            user.setName(name);
            user.setBirthday(Date.valueOf(day));
            if (male.isSelected())
                user.setGender(false);
            else
                user.setGender(true);
            UserDAO.updateUser(user);
            userText.setText(user.getName());
            edit.setVisible(true);
            save.setVisible(false);
            idText.setDisable(true);
            nameText.setDisable(true);
            dayText.setDisable(true);
            male.setDisable(true);
            female.setDisable(true);
        }

    }

    @FXML
    private void changePass(ActionEvent e) throws IOException {
        FXMLLoader loader = App.loadFXML("Password");
        loader.load();
        PasswordController controller = loader.getController();
        controller.setData(user);
        Scene scene = new Scene(loader.getRoot());
        Stage primary = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initOwner(primary);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void logout(ActionEvent e) throws IOException {
        App.changeScene("Login", "");
    }

    @FXML
    private void back(ActionEvent e) throws IOException {
        if (user.getRoleId().compareTo("TCH") == 0) {
            FXMLLoader loader = App.loadFXML("TeacherFunc");
            loader.load();
            TeacherFuncController controller = loader.getController();
            controller.setUsername(user, sem);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.getRoot()));
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
        } else {
            FXMLLoader loader = App.loadFXML("StudentFunc");
            loader.load();
            StudentFuncController controller = loader.getController();
            controller.setUsername(user, sem);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.getRoot()));
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
        }
    }
}
