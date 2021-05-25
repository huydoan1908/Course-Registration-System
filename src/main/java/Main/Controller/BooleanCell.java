package Main.Controller;

import Main.POJO.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;

class BooleanCell extends TableCell<User, Boolean> {
    private CheckBox checkBox;
    public BooleanCell() {
        checkBox = new CheckBox();
        checkBox.setDisable(true);
        this.setGraphic(checkBox);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
    @Override
    public void startEdit() {
    }
    @Override
    public void cancelEdit() {
        super.cancelEdit();
    }
    public void commitEdit(Boolean value) {
        super.commitEdit(value);
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
