package Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.TextField;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.List;

public class AutoCompleteTextField extends TextField {
    private final ObservableList<String> entries = FXCollections.observableArrayList();
    private final ContextMenu entriesPopup = new ContextMenu();

    public AutoCompleteTextField(List<String> possibleEntries) {
        entries.addAll(possibleEntries);

        textProperty().addListener((obs, oldText, newText) -> {
            if (newText.isEmpty()) {
                entriesPopup.hide();
            } else {
                ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();
                for (String entry : entries) {
                    if (entry.toLowerCase().contains(newText.toLowerCase())) {
                        MenuItem item = new MenuItem(entry);
                        item.setOnAction(e -> {
                            setText(entry);
                            positionCaret(entry.length());
                            entriesPopup.hide();
                        });
                        menuItems.add(item);
                    }
                }
                if (!menuItems.isEmpty()) {
                    entriesPopup.getItems().clear();
                    entriesPopup.getItems().addAll(menuItems);
                    if (!entriesPopup.isShowing()) {
                        entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                    }
                } else {
                    entriesPopup.hide();
                }
            }
        });
    }
}
