package lab8.client.gui.table;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import lab8.app.commands.Command;
import lab8.app.labwork.LabWork;
import lab8.client.ClientApp;

public class ButtonTableCell extends TableCell<LabWork, Void> {
    private final Button button;

    public ButtonTableCell(String text, Command command) {
        this.button = new Button(text);
        this.button.getStyleClass().add("command-tablebutton");
        this.button.setOnAction(event -> {
            LabWork labWork = getTableView().getItems().get(getTableRow().getIndex());
            command.setLabWorkNew(labWork);
            command.setId(Long.valueOf(labWork.getId()).toString());
            ClientApp.createCommandStage(command, true);
        });
        setGraphic(button);
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(button);
        }
    }


}
