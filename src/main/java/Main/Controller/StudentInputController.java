package Main.Controller;

import Main.DAO.ClassDAO;
import Main.DAO.StudentDAO;
import Main.DAO.UserDAO;
import Main.POJO.ClassInfo;
import Main.POJO.Clazz;
import Main.POJO.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class StudentInputController {
    @FXML
    private TextField idText;
    @FXML
    private TextField nameText;
    @FXML
    private DatePicker dateText;
    @FXML
    private TextField usernameText;
    @FXML
    private TextField passwordText;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;

    private User user;
    Clazz clazz;
    ClassInfo classInfo;
    private boolean update = false;

    public void setUpdate(boolean u, Clazz clazz)
    {
        update = u;
        this.clazz=clazz;
    }
    public void setTextField(User user)
    {
        idText.setDisable(true);
        idText.setText(user.getId());
        if(user.getName() != null)
            nameText.setText(user.getName());
        if(user.getBirthday() != null)
            dateText.setValue(user.getBirthday().toLocalDate());
        if(user.getUsername() != null)
            usernameText.setText(user.getUsername());
        if(user.getPassword() != null)
            passwordText.setText(user.getPassword());
        if(user.getGender())
            female.setSelected(true);
        else
            male.setSelected(true);
    }

    @FXML
    private void submit(ActionEvent e)
    {
        String id = idText.getText();
        String name = nameText.getText();
        String username = usernameText.getText();
        String password = passwordText.getText();
        LocalDate date = dateText.getValue();
        if (id.isEmpty() || name.isEmpty() || username.isEmpty() || password.isEmpty() || date == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Hãy điền tất cả thông tin");
            alert.showAndWait();
        } else {
            user = new User();
            classInfo = new ClassInfo();
            user.setId(id);
            classInfo.setClassId(clazz.getClassId());
            classInfo.setStudentId(id);
            user.setRoleId("STU");
            user.setName(name);
            user.setBirthday(Date.valueOf(date));
            user.setUsername(username);
            user.setPassword(password);
            if (male.isSelected()) {
                user.setGender(false);
            }
            if (female.isSelected()) {
                user.setGender(true);
            }

        }
        if(!update) {
            if(findUser(user))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Mã đã tồn tại!");
                alert.showAndWait();
            }else {
                UserDAO.addUser(user);
                clazz.setTotal(clazz.getTotal()+1);
                clazz.setMale(ClassDAO.getMale(clazz.getClassId()));
                clazz.setFemale(clazz.getTotal()-clazz.getMale());
                ClassDAO.updateClass(clazz);
                ClassDAO.addStudentInClass(classInfo);

                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.close();
            }
        }
        else
        {
            UserDAO.updateUser(user);
            clazz.setMale(ClassDAO.getMale(clazz.getClassId()));
            clazz.setFemale(clazz.getTotal()-clazz.getMale());
            ClassDAO.updateClass(clazz);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    private boolean findUser(User user) {
        List<User> list = UserDAO.getAllUser();
        for (User i : list)
            if (i.getId().compareTo(user.getId())==0)
                return true;
        return false;
    }
}
