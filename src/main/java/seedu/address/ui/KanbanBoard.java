package seedu.address.ui;

import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Controller for a help page
 */
public class KanbanBoard extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "KanbanBoard.fxml";
    private Logic logic;
    private Stage stage;
    private Scene mainWindowScene;
    private Stage mainWindowStage;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane bugListPanelPlaceholderBacklog;

    @FXML
    private StackPane bugListPanelPlaceholderTodo;

    @FXML
    private StackPane bugListPanelPlaceholderOngoing;

    @FXML
    private StackPane bugListPanelPlaceholderDone;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private HBox board;

    /**
     * Creates a new BoardWindow
     *
     * @param root Stage to use as the root of the BoardWindow
     */
    public KanbanBoard(Stage root, Stage mainWindowStage, Logic logic) {
        super(FXML, root);
        this.logic = logic;
        stage = root;
        fillInner();
        setWindowDefaultSize(logic.getGuiSettings());
        helpWindow = new HelpWindow();
        this.mainWindowStage = mainWindowStage;
        this.mainWindowScene = mainWindowStage.getScene();

    }

    /**
     * Creates a new Board
     */
    public KanbanBoard(Stage mainWindowStage, Logic logic) {
        this(new Stage(), mainWindowStage, logic);
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

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getKanBugTrackerFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        stage.setHeight(guiSettings.getWindowHeight());
        stage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            stage.setX(guiSettings.getWindowCoordinates().getX());
            stage.setY(guiSettings.getWindowCoordinates().getY());
        }
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

    public void switchToMain() {
        mainWindowStage.setScene(mainWindowScene);
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isShowBoard()) {
                switchToMain();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(stage.getWidth(), stage.getHeight(),
            (int) stage.getX(), (int) stage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        stage.hide();
    }

    public Scene getScene() {
        return stage.getScene();
    }
}
