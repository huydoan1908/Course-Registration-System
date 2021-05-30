package Main.Controller;

import Main.DAO.UserDAO;
import Main.POJO.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class PasswordController {
    @FXML
    private PasswordField currentPass;
    @FXML
    private PasswordField changePass;
    @FXML
    private PasswordField retypePass;

    private User user;

    public void setData(User user)
    {
        this.user = user;
    }

    @FXML
    private void submit(ActionEvent e)
    {
        String current = currentPass.getText();
        String change = changePass.getText();
        String retype = retypePass.getText();
        if(current.compareTo(user.getPassword())!= 0 ||change.compareTo(retype) != 0|| current.isEmpty() || change.isEmpty() || retype.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Thông tin không chính xác!");
            alert.showAndWait();
        }
        else{
            user.setPassword(change);
            UserDAO.updateUser(user);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
