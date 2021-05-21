package Main.Controller;

import Main.App;
import Main.DAO.UserDAO;
import Main.POJO.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TeacherInfoController implements Initializable {
    @FXML
    TableView<User> teacherTable;
    @FXML
    TableColumn<User, String> idCol;
    @FXML
    TableColumn<User, String> nameCol;
    @FXML
    TableColumn<User, Date> dateCol;
    @FXML
    TableColumn<User, Boolean> genderCol;
    @FXML
    TableColumn<User, String> usernameCol;
    @FXML
    TableColumn<User, String> passwordCol;
    @FXML
    Label usernameText;


    ObservableList<User> teacherList= FXCollections.observableArrayList();
    List<User> src;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    public void setUsernameText(String text)
    {
        usernameText.setText(text);
    }

    @FXML
    private void refresh()
    {
        teacherList.clear();
        src = UserDAO.getAllTeacher();
        for(User i : src)
            teacherList.add(i);
        idCol.setCellValueFactory(new PropertyValueFactory<User,String>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        dateCol.setCellValueFactory(new PropertyValueFactory<User,Date>("birthday"));
        //genderCol.setCellValueFactory(new PropertyValueFactory<User,Byte>("gender"));
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
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void delete(ActionEvent e)
    {
        User user = teacherTable.getSelectionModel().getSelectedItem();
        if(user == null)
            return;
        UserDAO.deleteUser(user);
        refresh();

    }

    @FXML
    private void update(ActionEvent e) throws IOException {
        User user = teacherTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = App.loadFXML("TeacherInput");
        loader.load();
        TeacherInputController controller = loader.getController();
        controller.setUpdate(true);
        controller.setTextField(user);
        Scene scene = new Scene(loader.getRoot());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}

class BooleanCell extends TableCell<User, Boolean> {
    private CheckBox checkBox;
    public BooleanCell() {
        checkBox = new CheckBox();
        checkBox.setDisable(true);
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(isEditing())
                    commitEdit(newValue == null ? false : newValue);
            }
        });
        this.setGraphic(checkBox);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setEditable(true);
    }
    @Override
    public void startEdit() {
        super.startEdit();
        if (isEmpty()) {
            return;
        }
        checkBox.setDisable(false);
        checkBox.requestFocus();
    }
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        checkBox.setDisable(true);
    }
    public void commitEdit(Boolean value) {
        super.commitEdit(value);
        checkBox.setDisable(true);
    }
    @Override
    public void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if(empty)
        {
            setGraphic(null);
            setText(null);
        }
        if (!isEmpty()) {
            checkBox.setSelected(item);
        }
    }
}
