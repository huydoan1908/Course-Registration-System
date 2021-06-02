package Main.Controller;

import Main.App;
import Main.DAO.ClassDAO;
import Main.DAO.StudentDAO;
import Main.DAO.UserDAO;
import Main.POJO.Clazz;
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
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClassInfoController implements Initializable {
    @FXML
    private TableView<Clazz> clazzTable;
    @FXML
    private TableColumn<Clazz, String> idCol;
    @FXML
    private TableColumn<Clazz, Integer> totalCol;
    @FXML
    private TableColumn<Clazz, Integer> maleCol;
    @FXML
    private TableColumn<Clazz, Integer> femaleCol;

    @FXML
    private Label usernameText;
    @FXML
    private TextField searchText;
    private User cur;
    private Semester sem;
    ObservableList<Clazz> clazzList= FXCollections.observableArrayList();
    List<Clazz> src;

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
        clazzList.clear();
        src = ClassDAO.getAllClass();
        clazzList.addAll(src);
        idCol.setCellValueFactory(new PropertyValueFactory<Clazz,String>("classId"));
        totalCol.setCellValueFactory(new PropertyValueFactory<Clazz,Integer>("total"));
        maleCol.setCellValueFactory(new PropertyValueFactory<Clazz,Integer>("male"));
        femaleCol.setCellValueFactory(new PropertyValueFactory<Clazz,Integer>("female"));
        clazzTable.setItems(clazzList);
    }

    @FXML
    private void add(ActionEvent e) throws IOException {
        Scene scene = new Scene(App.loadFXML("ClassInput").load());
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
        Clazz clazz = clazzTable.getSelectionModel().getSelectedItem();
        if(clazz == null)
            return;
        List<User> users = StudentDAO.getAllStudentInCLass(clazz.getClassId());
        if(users.isEmpty()){
            ClassDAO.deleteClass(clazz);
            refresh();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Tồn tại sinh viên thuộc lớp này!");
            alert.showAndWait();
        }
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
            clazzList.clear();
            src = ClassDAO.getClassById(res);
            clazzList.addAll(src);
            idCol.setCellValueFactory(new PropertyValueFactory<Clazz,String>("classId"));
            totalCol.setCellValueFactory(new PropertyValueFactory<Clazz,Integer>("total"));
            maleCol.setCellValueFactory(new PropertyValueFactory<Clazz,Integer>("male"));
            femaleCol.setCellValueFactory(new PropertyValueFactory<Clazz,Integer>("female"));
            clazzTable.setItems(clazzList);
        }
    }

    @FXML
    private void detail(ActionEvent e) throws IOException {
        Clazz clazz = clazzTable.getSelectionModel().getSelectedItem();
        if(clazz==null)
            return;
        FXMLLoader loader = App.loadFXML("ClassDetail");
        loader.load();
        ClassDetailController controller = loader.getController();
        controller.setUsernameText(cur,clazz,sem);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.getRoot()));
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
    }
}
