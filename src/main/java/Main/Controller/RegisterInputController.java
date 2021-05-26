package Main.Controller;

import Main.DAO.CourseRegisterDAO;
import Main.DAO.SemesterDAO;
import Main.POJO.CourseRegister;
import Main.POJO.CourseRegisterInfo;
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

public class RegisterInputController {
    @FXML
    private TextField yearText;
    @FXML
    private TextField nameText;
    @FXML
    private DatePicker startText;
    @FXML
    private DatePicker endText;

    private CourseRegister courseRegister;

    private Semester sem;
    private int idUpdate;
    private boolean update = false;

    public void setUpdate(boolean u,Semester semester)
    {
        update = u;
        sem = semester;
        yearText.setDisable(true);
        nameText.setDisable(true);
        yearText.setText(sem.getSemYear().toString());
        nameText.setText(sem.getSemName());
    }
    public void setTextField(CourseRegisterInfo info)
    {
        startText.setValue(info.getStart().toLocalDate());
        endText.setValue(info.getEnd().toLocalDate());
        idUpdate = info.getId();
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
            courseRegister = new CourseRegister();
            courseRegister.setSemId(sem.getSemId());
            courseRegister.setStartDate(Date.valueOf(start));
            courseRegister.setEndDate(Date.valueOf(end));
        }
        if(!update) {
            if(findCourseRegisterInfo(courseRegister))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Học kỳ đã tồn tại!");
                alert.showAndWait();
            }else {
                CourseRegisterDAO.addCourseRegister(courseRegister);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.close();
            }
        }
        else
        {
            courseRegister.setRegistId(idUpdate);
            CourseRegisterDAO.updateCourseRegister(courseRegister);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    private boolean findCourseRegisterInfo(CourseRegister info) {
        List<CourseRegister> list = CourseRegisterDAO.getAll();
        for (CourseRegister i : list)
            if (i.getStartDate().compareTo(info.getStartDate())==0 && i.getEndDate().compareTo(info.getEndDate())==0){
                info.setRegistId(i.getRegistId());
                return true;
            }
        return false;
    }
}
