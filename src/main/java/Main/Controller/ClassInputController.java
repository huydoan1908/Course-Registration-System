package Main.Controller;

import Main.DAO.ClassDAO;
import Main.POJO.Clazz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class ClassInputController {
    @FXML
    private TextField idText;

    private Clazz clazz;
    @FXML
    private void submit(ActionEvent e)
    {
        String id = idText.getText();
        if (id.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Hãy điền tất cả thông tin");
            alert.showAndWait();
        } else {
            clazz = new Clazz();
            clazz.setClassId(id);
            clazz.setTotal(0);
            clazz.setMale(0);
            clazz.setFemale(0);
        }
        if(findClazz(clazz))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Mã đã tồn tại!");
            alert.showAndWait();
        }else {
            ClassDAO.addClass(clazz);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    private boolean findClazz(Clazz clazz) {
        List<Clazz> list = ClassDAO.getAllClass();
        for (Clazz i : list)
            if (i.getClassId().compareTo(clazz.getClassId())==0)
                return true;
        return false;
    }
}
