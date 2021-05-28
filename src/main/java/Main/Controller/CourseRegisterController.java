package Main.Controller;

import Main.App;
import Main.DAO.CourseRegisterDAO;
import Main.POJO.CourseRegister;
import Main.POJO.CourseRegisterInfo;
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

public class CourseRegisterController implements Initializable {
    @FXML
    private TableView<CourseRegisterInfo> registerTable;
    @FXML
    private TableColumn<CourseRegisterInfo, String> nameCol;
    @FXML
    private TableColumn<CourseRegisterInfo, Integer> yearCol;
    @FXML
    private TableColumn<CourseRegisterInfo, Date> startCol;
    @FXML
    private TableColumn<CourseRegisterInfo, Date> endCol;

    @FXML
    private Label usernameText;
    @FXML
    private TextField searchText;
    @FXML
    private Label curSemText;
    private CourseRegister register;
    private User cur;
    private Semester curSem = null;
    private ObservableList<CourseRegisterInfo> registerList= FXCollections.observableArrayList();
    private List<CourseRegisterInfo> src;

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
        registerList.clear();
        src = CourseRegisterDAO.getAllRegister();
        registerList.addAll(src);
        nameCol.setCellValueFactory(new PropertyValueFactory<CourseRegisterInfo,String>("semName"));
        yearCol.setCellValueFactory(new PropertyValueFactory<CourseRegisterInfo,Integer>("semYear"));
        startCol.setCellValueFactory(new PropertyValueFactory<CourseRegisterInfo,Date>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<CourseRegisterInfo,Date>("end"));
        registerTable.setItems(registerList);
    }

    @FXML
    private void add(ActionEvent e) throws IOException {
        CourseRegisterInfo info = registerTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = App.loadFXML("RegisterInput");
        loader.load();
        RegisterInputController controller = loader.getController();
        controller.setUpdate(false,curSem);
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
        CourseRegisterInfo info = registerTable.getSelectionModel().getSelectedItem();
        if(info == null)
            return;
        register=new CourseRegister();
        register.setRegistId(info.getId());
        register.setSemId(curSem.getSemId());
        register.setStartDate(info.getStart());
        register.setEndDate(info.getEnd());
        CourseRegisterDAO.deleteCourseRegister(register);
        refresh();
    }

    @FXML
    private void update(ActionEvent e) throws IOException {
        CourseRegisterInfo info = registerTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = App.loadFXML("RegisterInput");
        loader.load();
        RegisterInputController controller = loader.getController();
        controller.setUpdate(true,curSem);
        controller.setTextField(info);
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
            registerList.clear();
            src = CourseRegisterDAO.getAllRegisterById(res);
            registerList.addAll(src);
            nameCol.setCellValueFactory(new PropertyValueFactory<CourseRegisterInfo,String>("semName"));
            yearCol.setCellValueFactory(new PropertyValueFactory<CourseRegisterInfo,Integer>("semYear"));
            startCol.setCellValueFactory(new PropertyValueFactory<CourseRegisterInfo,Date>("startDate"));
            endCol.setCellValueFactory(new PropertyValueFactory<CourseRegisterInfo,Date>("endDate"));
            registerTable.setItems(registerList);
        }
    }

}
