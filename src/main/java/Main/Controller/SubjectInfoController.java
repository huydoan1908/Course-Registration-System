package Main.Controller;

import Main.App;
import Main.DAO.SubjectDAO;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private User cur;
    ObservableList<Subject> subjectList= FXCollections.observableArrayList();
    List<Subject> src;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    public void setUsernameText(User user)
    {
        usernameText.setText(user.getName()+", ");
        cur=user;
    }

    @FXML
    private void refresh()
    {
        subjectList.clear();
        src = SubjectDAO.getAllSubject();
        for(Subject i : src)
            subjectList.add(i);
        idCol.setCellValueFactory(new PropertyValueFactory<Subject,String>("subjectId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Subject,String>("subjectName"));
        creditCol.setCellValueFactory(new PropertyValueFactory<Subject,Integer>("credits"));
        subjectTable.setItems(subjectList);
    }

    @FXML
    private void add(ActionEvent e) throws IOException {
        Scene scene = new Scene(App.loadFXML("SubjectInput").load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void delete(ActionEvent e)
    {
        Subject subject = subjectTable.getSelectionModel().getSelectedItem();
        if(subject == null)
            return;
        SubjectDAO.deleteSubject(subject);
        refresh();
    }

    @FXML
    private void update(ActionEvent e) throws IOException {
        Subject subject = subjectTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = App.loadFXML("SubjectInput");
        loader.load();
        SubjectInputController controller = loader.getController();
        controller.setUpdate(true);
        controller.setTextField(subject);
        Scene scene = new Scene(loader.getRoot());
        Stage stage = new Stage();
        stage.setScene(scene);
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
        controller.setUsername(cur);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.getRoot()));
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
    }
}
