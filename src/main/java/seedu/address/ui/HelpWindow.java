package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;


/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2021s1-cs2103t-w17-1.github.io/tp/UserGuide.html";
    public static final String FEATURES_ONE = "Words in UPPER_CASE are parameters to be supplied by the user";
    public static final String FEATURES_TWO = "Items in [...] are optional";

    public static final String LIST_ACTION = "list";
    public static final String LIST_FORMAT = "list";
    public static final String SEARCH_ACTION = "search";
    public static final String SEARCH_FORMAT = "search q/QUERYSTRING";
    public static final String ADD_ACTION = "add";
    public static final String ADD_FORMAT = "add n/NAME d/DESCRIPTION [s/STATE] [note/NOTE] [t/TAG]";
    public static final String DELETE_ACTION = "delete";
    public static final String DELETE_FORMAT = "delete INDEX [c/COLUMN]";
    public static final String EDIT_ACTION = "edit";
    public static final String EDIT_FORMAT = "edit INDEX [c/COLUMN] [n/NEW_NAME] [d/NEW_DESCRIPTION] [note/NOTE] "
                                                     + "[t/TAG]";
    public static final String EDIT_TAG_ACTION = "editTag";
    public static final String EDIT_TAG_FORMAT = "editTag INDEX [c/COLUMN] ot/OLD_TAG nt/NEW_TAG";
    public static final String ADD_TAG_ACTION = "addTag";
    public static final String ADD_TAG_FORMAT = "addTag INDEX [c/COLUMN] nt/NEW_TAG";
    public static final String CLEAR_ACTION = "clear";
    public static final String CLEAR_FORMAT = "clear";
    public static final String MOVE_ACTION = "move";
    public static final String MOVE_FORMAT = "move INDEX [c/COLUMN] s/STATE";
    public static final String EXIT_ACTION = "exit";
    public static final String EXIT_FORMAT = "exit";

    public static final String HELP_MESSAGE_HEADER = FEATURES_ONE + "\n" + FEATURES_TWO;

    public static final String HELP_MESSAGE_FOOTER =
            "For a more detailed guide, refer to: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpHeader;

    @FXML
    private Label helpFooter;

    @FXML
    private TableColumn<Table, String> action;

    @FXML
    private TableColumn<Table, String> format;

    @FXML
    private TableView<Table> tableID;


    private ObservableList<Table> data =
            FXCollections.observableArrayList(
                    new Table(LIST_ACTION, LIST_FORMAT),
                    new Table(SEARCH_ACTION, SEARCH_FORMAT),
                    new Table(ADD_ACTION, ADD_FORMAT),
                    new Table(DELETE_ACTION, DELETE_FORMAT),
                    new Table(EDIT_ACTION, EDIT_FORMAT),
                    new Table(EDIT_TAG_ACTION, EDIT_TAG_FORMAT),
                    new Table(ADD_TAG_ACTION, ADD_TAG_FORMAT),
                    new Table(CLEAR_ACTION, CLEAR_FORMAT),
                    new Table(MOVE_ACTION, MOVE_FORMAT),
                    new Table(EXIT_ACTION, EXIT_FORMAT));

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        action.setCellValueFactory(new PropertyValueFactory<Table, String>("action"));
        format.setCellValueFactory(new PropertyValueFactory<Table, String>("format"));
        helpHeader.setText(HELP_MESSAGE_HEADER);
        tableID.setItems(data);
        helpFooter.setText(HELP_MESSAGE_FOOTER);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
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
        logger.fine("Showing help page about the application.");
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

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
