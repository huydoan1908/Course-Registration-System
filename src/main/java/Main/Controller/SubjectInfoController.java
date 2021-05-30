package Main.Controller;

import Main.App;
import Main.DAO.CourseDAO;
import Main.DAO.SubjectDAO;
import Main.POJO.Course;
import Main.POJO.Semester;
import Main.POJO.Subject;
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
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SubjectInfoController implements Initializable {
    @FXML
    private TableView<Subject> subjectTable;
    @FXML
    private TableColumn<Subject, String> idCol;
    @FXML
    private TableColumn<Subject, String> nameCol;
    @FXML
    private TableColumn<Subject, Integer> creditCol;

    @FXML
    private Label usernameText;
    @FXML
    private TextField searchText;
    private User cur;
    private Semester sem;
    ObservableList<Subject> subjectList= FXCollections.observableArrayList();
    List<Subject> src;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    public void setUsernameText(User user,Semester sem)
    {
        if(user != null)
            usernameText.setText(user.getName()+", ");
        cur=user;
        this.sem = sem;
    }

    @FXML
    private void refresh()
    {
        subjectList.clear();
        src = SubjectDAO.getAllSubject();
        subjectList.addAll(src);
        idCol.setCellValueFactory(new PropertyValueFactory<Subject,String>("subjectId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Subject,String>("subjectName"));
        creditCol.setCellValueFactory(new PropertyValueFactory<Subject,Integer>("credits"));
        subjectTable.setItems(subjectList);
    }

    @FXML
    private void add(ActionEvent e) throws IOException {
        Scene scene = new Scene(App.loadFXML("SubjectInput").load());
        Stage primary = (Stage)((Node)e.getSource()).getScene().getWindow();
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
        Subject subject = subjectTable.getSelectionModel().getSelectedItem();
        if(subject == null)
            return;
        List<Course> courses = CourseDAO.getAllBySubject(subject.getSubjectId());
        if(courses.isEmpty())
        {
            SubjectDAO.deleteSubject(subject);
            refresh();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Tồn tại học phần thuộc môn học này!");
            alert.showAndWait();
        }
    }

    @FXML
    private void update(ActionEvent e) throws IOException {
        Subject subject = subjectTable.getSelectionModel().getSelectedItem();
        if(subject == null)
            return;
        FXMLLoader loader = App.loadFXML("SubjectInput");
        loader.load();
        SubjectInputController controller = loader.getController();
        controller.setUpdate(true);
        controller.setTextField(subject);
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
    private void logout() throws IOException {
        App.changeScene("Login","");
    }

    @FXML
    private void back(ActionEvent e) throws IOException {
        FXMLLoader loader = App.loadFXML("TeacherFunc");
        loader.load();
        TeacherFuncController controller = loader.getController();
        controller.setUsername(cur,sem);
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
            subjectList.clear();
            src = SubjectDAO.getSubjectById(res);
            subjectList.addAll(src);
            idCol.setCellValueFactory(new PropertyValueFactory<Subject,String>("subjectId"));
            nameCol.setCellValueFactory(new PropertyValueFactory<Subject,String>("subjectName"));
            creditCol.setCellValueFactory(new PropertyValueFactory<Subject,Integer>("credits"));
            subjectTable.setItems(subjectList);
        }
    }
}
