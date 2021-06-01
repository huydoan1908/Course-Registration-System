package Main.Controller;

import Main.DAO.CourseDAO;
import Main.DAO.SemesterDAO;
import Main.POJO.CourseInfo;
import Main.POJO.Semester;
import Main.POJO.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentCourseController implements Initializable {
    @FXML
    private TableView<CourseInfo> courseTable;
    @FXML
    private TableColumn<CourseInfo, String> idCol;
    @FXML
    private TableColumn<CourseInfo, String> nameCol;
    @FXML
    private TableColumn<CourseInfo, String> teacherCol;
    @FXML
    private TableColumn<CourseInfo, String> roomCol;
    @FXML
    private TableColumn<CourseInfo, String> dayCol;
    @FXML
    private TableColumn<CourseInfo, String> sessionCol;
    @FXML
    private Label usernameText;
    @FXML
    private ComboBox<Semester> semPicker;
    @FXML
    private Label semText;


    private User cur;
    private Semester curSem;
    private List<CourseInfo> src;
    private ObservableList<CourseInfo> courseList= FXCollections.observableArrayList();
    private List<Semester> semSrc = SemesterDAO.getAllSemester();
    private ObservableList<Semester> semList= FXCollections.observableArrayList(semSrc);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("subjectId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("subjectName"));
        teacherCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("teacher"));
        roomCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("room"));
        dayCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("dayOfWeek"));
        sessionCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("session"));
        courseTable.setItems(courseList);
        semPicker.getItems().addAll(semList);
    }

    public void setUsernameText(User user, Semester sem)
    {
        cur=user;
        usernameText.setText("Sinh viên: "+user.getId()+" - "+user.getName());
        curSem = sem;
        semPicker.setValue(curSem);
        refresh(curSem);
    }

    @FXML
    private void refresh(Semester sem)
    {
        courseList.clear();
        src = CourseDAO.getAllCourseOfStudent(sem.getSemId(),cur.getId());
        for(CourseInfo i : src)
            courseList.add(i);
        idCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("subjectId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("subjectName"));
        teacherCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("teacher"));
        roomCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("room"));
        dayCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("dayOfWeek"));
        sessionCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("session"));
        courseTable.setItems(courseList);
    }

    @FXML
    private void choose(ActionEvent e)
    {
        Semester pick = semPicker.getValue();
        refresh(pick);
    }
}
