package Main.Controller;

import Main.App;
import Main.DAO.CourseDAO;
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

public class CourseRegistController implements Initializable {
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
    ObservableList<CourseInfo> courseList= FXCollections.observableArrayList();
    List<CourseInfo> src;
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
        semText.setText("H???c k??? hi???n t???i: "+sem.getSemName()+" - " +sem.getSemYear().toString());
        isRegisted = CourseDAO.getAllCourseOfStudent(curSem.getSemId(),cur.getId());
        totalText.setText("S??? m??n ???? ????ng k??: "+ isRegisted.size());
        allCourse = CourseDAO.getAllInSem(curSem.getSemId());
        refresh();
    }

    @FXML
    private void refresh()
    {
        courseList.clear();
        src = CourseDAO.getAllCourseInSem(curSem.getSemId());
        for(CourseInfo i : src) {
            if(i.getCurrent().equals(i.getMaxSlot()))
                i.getSelect().setDisable(true);
            for(CourseInfo j : isRegisted)
                if(i.getCourseId().equals(j.getCourseId())) {
                    i.getSelect().setSelected(true);
                    i.getSelect().setDisable(true);
                }
            courseList.add(i);
        }
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
    private void confirm(ActionEvent e)
    {
        if(isRegisted.size() == 8)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("S??? l?????ng h???c ph???n ???? ?????t t???i ??a!");
            alert.showAndWait();
            return;
        }
        List<CourseInfo> selected = getSelected();
        if(isRegisted.size() + selected.size() > 8)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("B???n ch??? ???????c ????ng k?? t???i ??a 8 h???c ph???n!");
            alert.showAndWait();
            return;
        }
        for(int i =0;i<selected.size();i++){
            //Ki???m tra c??c h???c ph???n ??c ch???n h???p l???
            for(int j = i+1;j<selected.size();j++)
            {
                if(selected.get(i).getSubjectId().compareTo(selected.get(j).getSubjectId())==0 || (selected.get(i).getDayOfWeek().compareTo(selected.get(j).getDayOfWeek())==0 && selected.get(i).getSession().compareTo(selected.get(j).getSession())==0))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("H???c ph???n kh??ng h???p l???!");
                    alert.showAndWait();
                    return;
                }
            }
            //Ki???m tra h???c ph???n ???? ??k v?? hp ??c ch???n h???p l???
            for(CourseInfo j : isRegisted)
            {
                if(selected.get(i).getSubjectId().compareTo(j.getSubjectId())==0 || (selected.get(i).getDayOfWeek().compareTo(j.getDayOfWeek())==0 && selected.get(i).getSession().compareTo(j.getSession())==0))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("H???c ph???n kh??ng h???p l???!");
                    alert.showAndWait();
                    return;
                }
            }
        }
        for(CourseInfo i : selected)
        {
            isRegisted.add(i);
            for(Course j : allCourse)
            {
                if(i.getCourseId().equals(j.getCourseId())){
                    j.setCurrent(j.getCurrent()+1);
                    CourseDAO.updateCourse(j);
                }
            }
            Attend attend = new Attend();
            attend.setStudentId(cur.getId());
            attend.setCourseId(i.getCourseId());
            CourseDAO.addAttend(attend);
        }
        totalText.setText("S??? m??n ???? ????ng k??: "+ isRegisted.size());
        refresh();
    }
}
