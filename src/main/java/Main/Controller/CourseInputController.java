package Main.Controller;

import Main.DAO.CourseDAO;
import Main.DAO.SessionDAO;
import Main.DAO.SubjectDAO;
import Main.POJO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CourseInputController implements Initializable {
    @FXML
    private ComboBox<Subject> subjectText;
    @FXML
    private TextField teacherText;
    @FXML
    private TextField roomText;
    @FXML
    private TextField dayText;
    @FXML
    private TextField maxText;
    @FXML
    private ComboBox<Session> sessionText;

    private List<Subject> subjSrc = SubjectDAO.getAllSubject();
    private ObservableList<Subject> subjList = FXCollections.observableList(subjSrc);

    private List<Session> sesSrc = SessionDAO.getAllSession();
    private ObservableList<Session> sesList = FXCollections.observableList(sesSrc);

    private Course course;
    private Semester sem;
    public void setSem(Semester sem)
    {
        this.sem = sem;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        subjectText.getItems().addAll(subjList);
        sessionText.getItems().addAll(sesList);
    }
    @FXML
    private void submit(ActionEvent e)
    {
        Subject subject = subjectText.getValue();
        String teacher = teacherText.getText();
        String room = roomText.getText();
        String day = dayText.getText();
        String max = maxText.getText();
        Session session = sessionText.getValue();
        if(subject == null || teacher.isEmpty() || room.isEmpty() || day.isEmpty() || max.isEmpty() || session == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Hãy điền tất cả thông tin");
            alert.showAndWait();
        }
        else
        {
            course = new Course();
            course.setSubjId(subject.getSubjectId());
            course.setTeacher(teacher);
            course.setRoom(room);
            course.setMaxSlot(Integer.parseInt(max));
            course.setDayOfWeek(day);
            course.setSessId(session.getSessId());
            course.setSemiId(sem.getSemId());
        }

        if(findCourse(course))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Học phần đã tồn tại!");
            alert.showAndWait();
        }
        else
        {
            CourseDAO.addCourse(course);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    private boolean findCourse(Course course)
    {
        List<Course> src = CourseDAO.getAll();
        for(Course i : src)
            if(course.getSubjId().compareTo(i.getSubjId())==0 && course.getDayOfWeek().compareTo(i.getDayOfWeek())==0 && course.getSessId().compareTo(i.getSessId()) == 0 && course.getRoom().compareTo(i.getRoom())==0 && course.getSemiId().compareTo(i.getSemiId())==0)
                return true;
        return false;
    }
}
