package Main.Controller;

import Main.App;
import Main.DAO.UserDAO;
import Main.POJO.Semester;
import Main.POJO.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TeacherInfoController implements Initializable {
    @FXML
    private TableView<User> teacherTable;
    @FXML
    private TableColumn<User, String> idCol;
    @FXML
    private TableColumn<User, String> nameCol;
    @FXML
    private TableColumn<User, Date> dateCol;
    @FXML
    private TableColumn<User, Boolean> genderCol;
    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private TableColumn<User, String> passwordCol;
    @FXML
    private Label usernameText;
    @FXML
    private TextField searchText;
    private User cur;
    private Semester sem;

    ObservableList<User> teacherList= FXCollections.observableArrayList();
    List<User> src;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    public void setUsernameText(User user, Semester sem)
    {
        if(user != null)
            usernameText.setText(user.getName()+", ");
        cur=user;
        this.sem = sem;
    }

    @FXML
    private void refresh()
    {
        teacherList.clear();
        src = UserDAO.getAllTeacher();
        teacherList.addAll(src);
        idCol.setCellValueFactory(new PropertyValueFactory<User,String>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        dateCol.setCellValueFactory(new PropertyValueFactory<User,Date>("birthday"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<User,String>("password"));
        Callback<TableColumn<User, Boolean>, TableCell<User,Boolean>> booleanCellFactory = new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
            @Override
            public TableCell<User, Boolean> call(TableColumn<User, Boolean> userBooleanTableColumn) {
                return new BooleanCell();
            }
        };
        genderCol.setCellValueFactory(new PropertyValueFactory<User, Boolean>("gender"));
        genderCol.setCellFactory(booleanCellFactory);
        teacherTable.setItems(teacherList);
    }

    @FXML
    private void add(ActionEvent e) throws IOException {
        Scene scene = new Scene(App.loadFXML("TeacherInput").load());
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
    private void delete(ActionEvent e) throws IOException {
        User user = teacherTable.getSelectionModel().getSelectedItem();
        if(user == null)
            return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Bạn muốn xóa giáo viên " + user.getId()+" ?");
        if(alert.showAndWait().get()== ButtonType.OK) {
            UserDAO.deleteUser(user);
            refresh();
            if(user.equals(cur)) {
                logout();
            }
        }
    }

    @FXML
    private void update(ActionEvent e) throws IOException {
        User user = teacherTable.getSelectionModel().getSelectedItem();
        if(user == null)
            return;
        FXMLLoader loader = App.loadFXML("TeacherInput");
        loader.load();
        TeacherInputController controller = loader.getController();
        controller.setUpdate(true);
        controller.setTextField(user);
        Stage primary = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.getRoot());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initOwner(primary);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        refresh();
        for(User i:teacherList)
            if(i.getId().compareTo(cur.getId())==0)
                cur=i;
        usernameText.setText(user.getName()+", ");
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
            teacherList.clear();
            src = UserDAO.getTeacherById(res);
            teacherList.addAll(src);
            idCol.setCellValueFactory(new PropertyValueFactory<User,String>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
            dateCol.setCellValueFactory(new PropertyValueFactory<User,Date>("birthday"));
            usernameCol.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
            passwordCol.setCellValueFactory(new PropertyValueFactory<User,String>("password"));
            Callback<TableColumn<User, Boolean>, TableCell<User,Boolean>> booleanCellFactory = new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
                @Override
                public TableCell<User, Boolean> call(TableColumn<User, Boolean> userBooleanTableColumn) {
                    return new BooleanCell();
                }
            };
            genderCol.setCellValueFactory(new PropertyValueFactory<User, Boolean>("gender"));
            genderCol.setCellFactory(booleanCellFactory);
            teacherTable.setItems(teacherList);
        }
    }

    @FXML
    private void reset(ActionEvent e)
    {
        User user = teacherTable.getSelectionModel().getSelectedItem();
        user.setPassword(user.getUsername());
        UserDAO.updateUser(user);
        refresh();
    }
}


