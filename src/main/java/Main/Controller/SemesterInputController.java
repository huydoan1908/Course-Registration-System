package Main.Controller;

import Main.DAO.SemesterDAO;
import Main.POJO.Semester;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class SemesterInputController {
    @FXML
    private TextField yearText;
    @FXML
    private TextField nameText;
    @FXML
    private DatePicker startText;
    @FXML
    private DatePicker endText;

    private Semester semester;
    private boolean update = false;

    public void setUpdate(boolean u)
    {
        update = u;
    }
    public void setTextField(Semester semester)
    {
        yearText.setDisable(true);
        nameText.setDisable(true);
        yearText.setText(semester.getSemYear().toString());
        nameText.setText(semester.getSemName());
        startText.setValue(semester.getStartDate().toLocalDate());
        endText.setValue(semester.getEndDate().toLocalDate());
    }
    @FXML
    private void submit(ActionEvent e)
    {
        String year = yearText.getText();
        String name = nameText.getText();
        LocalDate start = startText.getValue();
        LocalDate end = endText.getValue();
        if (year.isEmpty() || name.isEmpty() || start == null || end == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Hãy điền tất cả thông tin");
            alert.showAndWait();
        } else {
            semester = new Semester();
            semester.setSemName(name);
            semester.setSemYear(Integer.parseInt(year));
            semester.setStartDate(Date.valueOf(start));
            semester.setEndDate(Date.valueOf(end));
        }
        if(!update) {
            if(findSemester(semester))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Học kỳ đã tồn tại!");
                alert.showAndWait();
            }else {
                SemesterDAO.addSemester(semester);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.close();
            }
        }
        else
        {
            findSemester(semester);
            SemesterDAO.updateSemester(semester);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    private boolean findSemester(Semester semester) {
        List<Semester> list = SemesterDAO.getAllSemester();
        for (Semester i : list)
            if (i.getSemName().compareTo(semester.getSemName())==0 && i.getSemYear().equals(semester.getSemYear())){
                semester.setSemId(i.getSemId());
                return true;
            }
        return false;
    }
}
