package Main.Controller;

import Main.App;
import Main.DAO.ClassDAO;
import Main.DAO.StudentDAO;
import Main.DAO.UserDAO;
import Main.POJO.ClassInfo;
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
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ClassDetailController implements Initializable {
    @FXML
    private TableView<User> studentTable;
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
    @FXML
    private Label infoText;
    private User cur;
    private Semester sem;
    private ObservableList<User> studentList= FXCollections.observableArrayList();
    private List<User> src = null;
    private Clazz curClass;
    private List<ClassInfo> infoList = ClassDAO.getAllInfoClass();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        studentTable.setItems(studentList);
    }

    public void setUsernameText(User user, Clazz clazz, Semester sem)
    {
        if(user!= null)
            usernameText.setText(user.getName()+", ");
        cur=user;
        curClass=clazz;
        this.sem = sem;
        infoText.setText("Thông tin lớp "+clazz.getClassId());
        refresh();
    }

    @FXML
    private void refresh()
    {
        studentList.clear();
        src = StudentDAO.getAllStudentInCLass(curClass.getClassId());
        studentList.addAll(src);
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
        studentTable.setItems(studentList);
    }

    @FXML
    private void add(ActionEvent e) throws IOException {
        User user = studentTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = App.loadFXML("StudentInput");
        loader.load();
        StudentInputController controller = loader.getController();
        controller.setUpdate(false,curClass);
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
        User user = studentTable.getSelectionModel().getSelectedItem();
        if(user == null)
            return;

        ClassInfo info = new ClassInfo();
        for(ClassInfo i : infoList)
            if(i.getClassId().compareTo(curClass.getClassId())==0 && i.getStudentId().compareTo(user.getId())==0)
                info=i;

        curClass.setTotal(curClass.getTotal()-1);
        if(user.getGender())
            curClass.setFemale(curClass.getFemale()-1);
        else
            curClass.setMale(curClass.getMale()-1);

        ClassDAO.updateClass(curClass);
        ClassDAO.deleteStudentInClass(info);
        UserDAO.deleteUser(user);
        refresh();
    }

    @FXML
    private void update(ActionEvent e) throws IOException {
        User user = studentTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = App.loadFXML("StudentInput");
        loader.load();
        StudentInputController controller = loader.getController();
        controller.setUpdate(true,curClass);
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
    }

    @FXML
    private void logout() throws IOException {
        App.changeScene("Login","");
    }

    @FXML
    private void back(ActionEvent e) throws IOException {
        FXMLLoader loader = App.loadFXML("ClassInfo");
        loader.load();
        ClassInfoController controller = loader.getController();
        controller.setUsernameText(cur,sem);
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
            studentList.clear();
            src = StudentDAO.getAllStudentInCLassById(curClass.getClassId(),res);
            studentList.addAll(src);
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
            studentTable.setItems(studentList);
        }
    }

    @FXML
    private void reset(ActionEvent e)
    {
        User user = studentTable.getSelectionModel().getSelectedItem();
        user.setPassword(user.getUsername());
        UserDAO.updateUser(user);
        refresh();
    }
}
