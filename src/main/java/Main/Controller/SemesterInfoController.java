package Main.Controller;

import Main.App;
import Main.DAO.SemesterDAO;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class SemesterInfoController implements Initializable {
    @FXML
    private TableView<Semester> semesterTable;
    @FXML
    private TableColumn<Semester, String> nameCol;
    @FXML
    private TableColumn<Semester, Integer> yearCol;
    @FXML
    private TableColumn<Semester, Date> startCol;
    @FXML
    private TableColumn<Semester, Date> endCol;

    @FXML
    private Label usernameText;
    @FXML
    private TextField searchText;
    @FXML
    private Label curSemText;
    private User cur;
    private Semester curSem = null;
    private ObservableList<Semester> semesterList= FXCollections.observableArrayList();
    private List<Semester> src;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    public void setUsernameText(User user,Semester sem)
    {
        if(user != null)
            usernameText.setText(user.getName()+", ");
        cur=user;
        if(sem != null)
        {
            curSem = sem;
            curSemText.setText("Học kỳ hiện tại: "+sem.getSemName()+" - "+sem.getSemYear());
        }
    }

    @FXML
    private void refresh()
    {
        semesterList.clear();
        src = SemesterDAO.getAllSemester();
        semesterList.addAll(src);
        nameCol.setCellValueFactory(new PropertyValueFactory<Semester,String>("semName"));
        yearCol.setCellValueFactory(new PropertyValueFactory<Semester,Integer>("semYear"));
        startCol.setCellValueFactory(new PropertyValueFactory<Semester,Date>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<Semester,Date>("endDate"));
        semesterTable.setItems(semesterList);
    }

    @FXML
    private void add(ActionEvent e) throws IOException {
        Scene scene = new Scene(App.loadFXML("SemesterInput").load());
        Stage stage = new Stage();
        Stage primary = (Stage)((Node)e.getSource()).getScene().getWindow();
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
        Semester semester = semesterTable.getSelectionModel().getSelectedItem();
        if(semester == null)
            return;
        SemesterDAO.deleteSemester(semester);
        refresh();
    }

    @FXML
    private void update(ActionEvent e) throws IOException {
        Semester Semester = semesterTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = App.loadFXML("SemesterInput");
        loader.load();
        SemesterInputController controller = loader.getController();
        controller.setUpdate(true);
        controller.setTextField(Semester);
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
            semesterList.clear();
            src = SemesterDAO.getSemesterById(res);
            semesterList.addAll(src);
            nameCol.setCellValueFactory(new PropertyValueFactory<Semester,String>("semName"));
            yearCol.setCellValueFactory(new PropertyValueFactory<Semester,Integer>("semYear"));
            startCol.setCellValueFactory(new PropertyValueFactory<Semester,Date>("startDate"));
            endCol.setCellValueFactory(new PropertyValueFactory<Semester,Date>("endDate"));
            semesterTable.setItems(semesterList);
        }
    }

    @FXML
    private void setCurrentSem(ActionEvent e)
    {
        Semester semester = semesterTable.getSelectionModel().getSelectedItem();
        if(semester != null) {
            curSem = semester;
            curSemText.setText("Học kỳ hiện tại: "+semester.getSemName()+" - "+semester.getSemYear());
        }
    }
}
