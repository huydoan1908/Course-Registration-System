package Main.Controller;

import Main.App;
import Main.DAO.ClassDAO;
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
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CourseDetailController implements Initializable {
    @FXML
    private TableView<User> studentTable;
    @FXML
    private TableColumn<User, String> idCol;
    @FXML
    private TableColumn<User, String> nameCol;
    @FXML
    private TableColumn<User, Boolean> genderCol;
    @FXML
    private TextField searchText;
    @FXML
    private Label infoText;

    private ObservableList<User> studentList= FXCollections.observableArrayList();
    private List<User> src;
    private CourseInfo curCourse;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<User,String>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        Callback<TableColumn<User, Boolean>, TableCell<User,Boolean>> booleanCellFactory = new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
            @Override
            public TableCell<User, Boolean> call(TableColumn<User, Boolean> userBooleanTableColumn) {
                return new BooleanCell();
            }
        };
        genderCol.setCellValueFactory(new PropertyValueFactory<User, Boolean>("gender"));
        genderCol.setCellFactory(booleanCellFactory);
        studentTable.setItems(studentList);
    }

    public void setUsernameText(User user, CourseInfo info, Semester sem)
    {
        curCourse = info;
        infoText.setText("Lớp " + info.getSubjectName() +", "+info.getRoom() +", " + info.getDayOfWeek()+" ("+info.getSession()+")");
        refresh();
    }

    @FXML
    private void refresh()
    {
        studentList.clear();
        src = StudentDAO.getAllStudentInCourse(curCourse.getCourseId());
        studentList.addAll(src);
        idCol.setCellValueFactory(new PropertyValueFactory<User,String>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<User,String>("name"));

        Callback<TableColumn<User, Boolean>, TableCell<User,Boolean>> booleanCellFactory = new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
            @Override
            public TableCell<User, Boolean> call(TableColumn<User, Boolean> userBooleanTableColumn) {
                return new BooleanCell();
            }
        };
        genderCol.setCellValueFactory(new PropertyValueFactory<User, Boolean>("gender"));
        genderCol.setCellFactory(booleanCellFactory);
        studentTable.setItems(studentList);
    }

    @FXML
    private void search(ActionEvent e)
    {
        String res = searchText.getText();
        if(res.isEmpty()){ }
        else{
            studentList.clear();
            src = StudentDAO.getAllStudentInCourseById(curCourse.getCourseId(),res);
            studentList.addAll(src);
            idCol.setCellValueFactory(new PropertyValueFactory<User,String>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
            Callback<TableColumn<User, Boolean>, TableCell<User,Boolean>> booleanCellFactory = new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
                @Override
                public TableCell<User, Boolean> call(TableColumn<User, Boolean> userBooleanTableColumn) {
                    return new BooleanCell();
                }
            };
            genderCol.setCellValueFactory(new PropertyValueFactory<User, Boolean>("gender"));
            genderCol.setCellFactory(booleanCellFactory);
            studentTable.setItems(studentList);
        }
    }
}
