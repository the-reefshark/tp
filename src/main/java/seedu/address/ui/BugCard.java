package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.bug.Bug;

/**
 * An UI component that displays information of a {@code Bug}.
 */
public class BugCard extends UiPart<Region> {

    private static final String FXML = "BugListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Bug bug;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label description;
    @FXML
    private Label state;
    @FXML
    private FlowPane tags;
    @FXML
    private Label priority;
    @FXML
    private Label note;

    /**
     * Creates a {@code BugCard} with the given {@code Bug} and index to display.
     */
    public BugCard(Bug bug, int displayedIndex) {
        super(FXML);
        this.bug = bug;
        id.setText(displayedIndex + ". ");
        name.setText(bug.getName().fullName);
        name.setMinWidth(Label.USE_PREF_SIZE);

        description.setText(bug.getDescription().value);
        description.setWrapText(true);
        state.setText(bug.getState().toString());

        if (!bug.getPriority().isNull()) {
            priority.setText("Priority " + bug.getPriority().getValue());
            switch (bug.getPriority().getValue()) {
            case "low":
                priority.setStyle("-fx-background-color: green;");
                break;
            case "medium":
                priority.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
                break;
            default:
                priority.setStyle("-fx-background-color: red;");
                break;
            }
        } else {
            priority.setVisible(false);
            priority.setManaged(false);
        }

        if (bug.getOptionalNote().isPresent()) {
            note.setText(bug.getOptionalNote().get().value);
            note.setWrapText(true);
        } else {
            note.setVisible(false);
            note.setManaged(false);
        }

        bug.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BugCard)) {
            return false;
        }

        // state check
        BugCard card = (BugCard) other;
        return id.getText().equals(card.id.getText())
                && bug.equals(card.bug);
    }
}
