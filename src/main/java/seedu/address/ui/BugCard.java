package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.bug.Bug;

/**
 * An UI component that displays information of a {@code Bug}.
 */
public class BugCard extends BugCardKanban {

    private static final String FXML = "BugListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private Label description;
    @FXML
    private Label state;
    @FXML
    private Label note;
    @FXML
    private HBox noteContainer;

    /**
     * Creates a {@code BugCard} with the given {@code Bug} and index to display.
     */
    public BugCard(Bug bug, int displayedIndex) {
        super(bug, displayedIndex, FXML);

        setDifferedFields();
    }

    private void setDifferedFields() {
        setDescription();
        setState();
        setNote();
    }

    @Override
    protected void setDescription() {
        description.setText(bug.getDescription().value);
        description.setWrapText(true);
    }

    protected void setState() {
        state.setText(super.bug.getState().toString());
    }

    protected void setNote() {
        if (super.bug.getOptionalNote().isPresent()) {
            note.setText(bug.getOptionalNote().get().value);
            note.setWrapText(true);
        } else {
            noteContainer.setManaged(false);
            note.setManaged(false);
        }
    }
}
