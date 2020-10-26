package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;


/**
 * Panel containing the list of bugs.
 */
public class KanbanPanel extends UiPart<Region> {
    private static final String FXML = "KanbanPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BugListPanel.class);
    private final Logic logic;

    @FXML
    private StackPane bugListPanelPlaceholderBacklog;

    @FXML
    private StackPane bugListPanelPlaceholderTodo;

    @FXML
    private StackPane bugListPanelPlaceholderOngoing;

    @FXML
    private StackPane bugListPanelPlaceholderDone;

    /**
     * Creates a {@code BugListPanel} with the given {@code ObservableList}.
     */
    public KanbanPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        fillInner();
    }

    private void fillInner() {
        BugListPanelKanban bugListPanelBackLog = new BugListPanelKanban(logic.getFilteredBugListByState("backlog"));
        bugListPanelPlaceholderBacklog.getChildren().add(bugListPanelBackLog.getRoot());

        BugListPanelKanban bugListPanelTodo = new BugListPanelKanban(logic.getFilteredBugListByState("todo"));
        bugListPanelPlaceholderTodo.getChildren().add(bugListPanelTodo.getRoot());

        BugListPanelKanban bugListPanelOngoing = new BugListPanelKanban(logic.getFilteredBugListByState("ongoing"));
        bugListPanelPlaceholderOngoing.getChildren().add(bugListPanelOngoing.getRoot());

        BugListPanelKanban bugListPanelDone = new BugListPanelKanban(logic.getFilteredBugListByState("done"));
        bugListPanelPlaceholderDone.getChildren().add(bugListPanelDone.getRoot());
    }

}
