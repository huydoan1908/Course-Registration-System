package Main.Controller;

import Main.DAO.SubjectDAO;
import Main.POJO.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SubjectInputController {
    @FXML
    private TextField idText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField creditText;

    private Subject subject;
    private boolean update;

    public void setUpdate(boolean u)
    {
        update = u;
    }
    public void setTextField(Subject subject)
    {
        idText.setDisable(true);
        idText.setText(subject.getSubjectId());
        nameText.setText(subject.getSubjectName());
        creditText.setText(subject.getCredits().toString());
    }
    @FXML
    private void submit(ActionEvent e)
    {
        String id = idText.getText();
        String name = nameText.getText();
        String credit = creditText.getText();
        if (id.isEmpty() || name.isEmpty() || credit.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Hãy điền tất cả thông tin");
            alert.showAndWait();
        } else {
            subject = new Subject();
            subject.setSubjectId(id);
            subject.setSubjectName(name);
            subject.setCredits(Integer.parseInt(credit));
        }
        if(!update) {
            SubjectDAO.addSubject(subject);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
        }
        else
        {
            SubjectDAO.updateSubject(subject);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
