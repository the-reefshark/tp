package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.bug.Bug;

/**
 * Panel containing the list of bugs.
 */
public class FourBugListPanels extends UiPart<Region> {
    private static final String FXML = "FourBugListPanels.fxml";
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
    public FourBugListPanels(Logic logic) {
        super(FXML);
        this.logic = logic;
        fillInner();
    }

    private void fillInner() {
        BugListPanel bugListPanelBackLog = new BugListPanel(logic.getFilteredBugListByState("backlog"));
        bugListPanelPlaceholderBacklog.getChildren().add(bugListPanelBackLog.getRoot());

        BugListPanel bugListPanelTodo = new BugListPanel(logic.getFilteredBugListByState("todo"));
        bugListPanelPlaceholderTodo.getChildren().add(bugListPanelTodo.getRoot());

        BugListPanel bugListPanelOngoing = new BugListPanel(logic.getFilteredBugListByState("ongoing"));
        bugListPanelPlaceholderOngoing.getChildren().add(bugListPanelOngoing.getRoot());

        BugListPanel bugListPanelDone = new BugListPanel(logic.getFilteredBugListByState("done"));
        bugListPanelPlaceholderDone.getChildren().add(bugListPanelDone.getRoot());
    }

}
