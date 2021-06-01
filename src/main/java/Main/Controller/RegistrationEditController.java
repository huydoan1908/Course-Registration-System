package Main.Controller;

import Main.App;
import Main.DAO.CourseDAO;
import Main.DAO.StudentDAO;
import Main.POJO.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrationEditController implements Initializable {
    @FXML
    private TableView<CourseInfo> courseTable;
    @FXML
    private TableColumn<CourseInfo, String> idCol;
    @FXML
    private TableColumn<CourseInfo, String> nameCol;
    @FXML
    private TableColumn<CourseInfo, Integer> creditCol;
    @FXML
    private TableColumn<CourseInfo, String> teacherCol;
    @FXML
    private TableColumn<CourseInfo, String> roomCol;
    @FXML
    private TableColumn<CourseInfo, String> dayCol;
    @FXML
    private TableColumn<CourseInfo, String> sessionCol;
    @FXML
    private TableColumn<CourseInfo, Integer> maxCol;
    @FXML
    private TableColumn<CourseInfo, CheckBox> selectCol;

    @FXML
    private Label usernameText;
    @FXML
    private Label semText;
    @FXML
    private Label totalText;

    private User cur;
    private Semester curSem;
    private List<Course> allCourse;
    private List<Attend> allAttend;
    ObservableList<CourseInfo> courseList= FXCollections.observableArrayList();
    List<CourseInfo> isRegisted;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("subjectId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("subjectName"));
        creditCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,Integer>("credits"));
        teacherCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("teacher"));
        roomCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("room"));
        dayCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("dayOfWeek"));
        sessionCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("session"));
        maxCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,Integer>("maxSlot"));
        selectCol.setCellValueFactory(new  PropertyValueFactory<CourseInfo,CheckBox>("select"));
        courseTable.setItems(courseList);
    }

    public void setUsernameText(User user, Semester sem)
    {
        if(user != null)
            usernameText.setText(user.getName()+", ");
        cur=user;
        curSem = sem;
        semText.setText("Học kỳ hiện tại: "+sem.getSemName()+" - " +sem.getSemYear().toString());
        isRegisted = CourseDAO.getAllCourseOfStudent(curSem.getSemId(),cur.getId());
        totalText.setText("Số môn đã đăng ký: "+ isRegisted.size());
        allCourse = CourseDAO.getAllInSem(curSem.getSemId());
        allAttend = StudentDAO.getAllAttendOfStudent(user.getId());
        refresh();
    }

    @FXML
    private void refresh()
    {
        courseList.clear();
        courseList.addAll(isRegisted);
        idCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("subjectId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("subjectName"));
        creditCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,Integer>("credits"));
        teacherCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("teacher"));
        roomCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("room"));
        dayCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("dayOfWeek"));
        sessionCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,String>("session"));
        maxCol.setCellValueFactory(new PropertyValueFactory<CourseInfo,Integer>("maxSlot"));
        courseTable.setItems(courseList);
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

    private List<CourseInfo> getSelected()
    {
        List<CourseInfo> selectedCourse = new ArrayList<CourseInfo>();
        for(CourseInfo i : courseList) {
            if (i.getSelect().isSelected() && !i.getSelect().isDisable())
                selectedCourse.add(i);
        }
        return selectedCourse;
    }

    @FXML
    private void delete(ActionEvent e)
    {
        List<CourseInfo> selected = getSelected();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Bạn muốn hủy đăng ký học phần này ?");
        if(alert.showAndWait().get()== ButtonType.OK) {
            for(CourseInfo i : selected)
            {
                for(Course j : allCourse)
                {
                    if(i.getCourseId().equals(j.getCourseId()))
                    {
                        j.setCurrent(j.getCurrent()-1);
                        CourseDAO.updateCourse(j);
                    }
                }
                for(Attend j : allAttend)
                {
                    if(i.getCourseId().equals(j.getCourseId()))
                    {
                        CourseDAO.deleteAttend(j);
                    }
                }
                isRegisted.remove(i);
            }
        }
        totalText.setText("Số môn đã đăng ký: "+ isRegisted.size());
        refresh();
    }
}
