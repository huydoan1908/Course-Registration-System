package Main.Controller;

import Main.App;
import Main.DAO.UserDAO;
import Main.POJO.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LoginController {
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    private List<User> userList = UserDAO.getAllUser();
    @FXML
    public void submit(ActionEvent e) throws IOException {
        String username = usernameText.getText();
        String password = passwordText.getText();
        User user = findUser(username,password);
        if(user == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Thông tin không chính xác!");
            alert.showAndWait();
        }
        else if(user.getRoleId().compareTo("TCH") == 0){
            FXMLLoader loader = App.loadFXML("TeacherFunc");
            loader.load();
            TeacherFuncController controller = loader.getController();
            controller.setUsername(user);
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.getRoot()));
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
        }
        else{

        }
    }

    private User findUser(String username, String password) {
        for (User i : userList)
            if (i.getUsername().compareTo(username) == 0 && i.getPassword().compareTo(password) == 0)
                return i;
        return null;
    }
}
