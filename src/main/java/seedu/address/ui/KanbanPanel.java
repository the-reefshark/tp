package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.bug.State;


/**
 * Panel containing the list of bugs.
 */
public class KanbanPanel extends UiPart<Region> {
    private static final String FXML = "KanbanPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BugListPanel.class);
    private final Logic logic;

    @FXML
    private VBox backlogColumn;

    @FXML
    private VBox todoColumn;

    @FXML
    private VBox ongoingColumn;

    @FXML
    private VBox doneColumn;


    /**
     * Creates a {@code BugListPanel} with the given {@code ObservableList}.
     */
    public KanbanPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        fillKanban();
    }

    private void fillKanban() {
        fillColumn(backlogColumn, "backlog");

        fillColumn(todoColumn, "todo");

        fillColumn(ongoingColumn, "ongoing");

        fillColumn(doneColumn, "done");
    }

    private void fillColumn(VBox column, String state) {
        Label label = new Label(state);
        label.getStyleClass().add("state");

        BugListPanelKanban bugListPanel = new BugListPanelKanban(logic.getFilteredBugListByState(state));
        VBox.setVgrow(bugListPanel.getRoot(), Priority.ALWAYS);

        column.getChildren().addAll(label, bugListPanel.getRoot());
    }

}
