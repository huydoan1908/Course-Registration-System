package Main.Controller;

import Main.App;
import Main.DAO.CourseDAO;
import Main.DAO.SemesterDAO;
import Main.POJO.CourseInfo;
import Main.POJO.Semester;
import Main.POJO.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrationResultController implements Initializable {
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
        if(user != null)
            usernameText.setText(user.getName()+", ");
        cur=user;
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

    @FXML
    private void logout(ActionEvent e) throws IOException {
        App.changeScene("Login", "");
    }

    @FXML
    private void back(ActionEvent e) throws IOException {
        FXMLLoader loader = App.loadFXML("StudentFunc");
        loader.load();
        StudentFuncController controller = loader.getController();
        controller.setUsername(cur, curSem);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.getRoot()));
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
    }
}
