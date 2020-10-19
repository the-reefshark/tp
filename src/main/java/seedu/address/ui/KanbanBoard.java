package seedu.address.ui;

import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

/**
 * Controller for a help page
 */
public class KanbanBoard extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "KanbanBoard.fxml";
    private Logic logic;

    @FXML
    private StackPane bugListPanelPlaceholderBacklog;
    @FXML
    private StackPane bugListPanelPlaceholderTodo;
    @FXML
    private StackPane bugListPanelPlaceholderOngoing;
    @FXML
    private StackPane bugListPanelPlaceholderDone;

    /**
     * Creates a new BoardWindow
     *
     * @param root Stage to use as the root of the BoardWindow
     */
    public KanbanBoard(Stage root, Logic logic) {
        super(FXML, root);
        this.logic = logic;
        fillInner();
    }

    /**
     * Creates a new Board
     */
    public KanbanBoard(Logic logic) {
        this(new Stage(), logic);
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

    /**
     * Shows the board
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing kanbanBoard");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}