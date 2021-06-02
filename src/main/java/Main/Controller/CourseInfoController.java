package Main.Controller;

import Main.App;
import Main.DAO.CourseDAO;
import Main.DAO.CourseRegisterDAO;
import Main.DAO.StudentDAO;
import Main.DAO.SubjectDAO;
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
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CourseInfoController implements Initializable {
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
    private Label usernameText;
    @FXML
    private TextField searchText;
    @FXML
    private Label semText;
    private User cur;
    private Semester curSem;
    ObservableList<CourseInfo> courseList= FXCollections.observableArrayList();
    List<CourseInfo> src;

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
        courseTable.setItems(courseList);
    }

    public void setUsernameText(User user, Semester sem)
    {
        if(user != null)
            usernameText.setText(user.getName()+", ");
        cur=user;
        curSem = sem;
        semText.setText("Học kỳ hiện tại: "+sem.getSemName()+" - " +sem.getSemYear().toString());
        refresh();
    }

    @FXML
    private void refresh()
    {
        courseList.clear();
        src = CourseDAO.getAllCourseInSem(curSem.getSemId());
        for(CourseInfo i : src)
            courseList.add(i);
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
    private void add(ActionEvent e) throws IOException {
        FXMLLoader loader = App.loadFXML("CourseInput");
        loader.load();
        CourseInputController controller = loader.getController();
        controller.setSem(curSem);
        Stage primary = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.getRoot());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initOwner(primary);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        refresh();
    }

    @FXML
    private void delete(ActionEvent e)
    {
        CourseInfo info = courseTable.getSelectionModel().getSelectedItem();
        if(info==null)
            return;
        List<User> list = StudentDAO.getAllStudentInCourse(info.getCourseId());
        if(!list.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Tồn tại sinh viên đã đăng ký học phần này!");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Bạn muốn xóa học phần này ?");
        if(alert.showAndWait().get()== ButtonType.OK) {
            Course del = new Course();
            del.setCourseId(info.getCourseId());
            CourseDAO.deleteCourse(del);
            refresh();
        }
    }

    @FXML
    private void detail(ActionEvent e) throws IOException {
        CourseInfo info = courseTable.getSelectionModel().getSelectedItem();
        if(info==null)
            return;
        FXMLLoader loader = App.loadFXML("CourseDetail");
        loader.load();
        CourseDetailController controller = loader.getController();
        controller.setUsernameText(cur,info,curSem);
        Scene scene = new Scene(loader.getRoot());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void logout() throws IOException {
        App.changeScene("Login","");
    }

    @FXML
    private void back(ActionEvent e) throws IOException {
        FXMLLoader loader = App.loadFXML("TeacherFunc");
        loader.load();
        TeacherFuncController controller = loader.getController();
        controller.setUsername(cur,curSem);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.getRoot()));
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
    }

    @FXML
    private void search(ActionEvent e)
    {
        String res = searchText.getText();
        if(res.isEmpty()){ }
        else{
            courseList.clear();
            src = CourseDAO.getAllCourseInSemById(curSem.getSemId(),res);
            for(CourseInfo i : src)
                courseList.add(i);
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
    }
}
