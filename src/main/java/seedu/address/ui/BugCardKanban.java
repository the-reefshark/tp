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
public class BugCardKanban extends UiPart<Region> {

    protected static final String FXML = "BugListCardKanban.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Bug bug;
    public final int displayedIndex;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;
    @FXML
    private Label priority;

    /**
     * Creates a {@code BugCardKanban} with the given {@code Bug} and index to display.
     */
    public BugCardKanban(Bug bug, int displayedIndex) {
        super(FXML);
        this.bug = bug;
        this.displayedIndex = displayedIndex;
        setFields();
    }

    protected BugCardKanban(Bug bug, int displayedIndex, String fxml) {
        super(fxml);
        this.bug = bug;
        this.displayedIndex = displayedIndex;
        setFields();
    }

    private void setFields() {
        setIndex();
        setName();
        setDescription();
        setPriority();
        setTags();
    }

    protected void setIndex() {
        id.setText(displayedIndex + ". ");
    }

    //@@author PhongTran98
    protected void setName() {
        name.setText(bug.getName().fullName);
        name.setWrapText(true);
    }

    protected void setDescription() {
        description.setText(bug.getDescription().value);
        description.setWrapText(true);
        description.setMaxHeight(60);
    }

    protected void setPriority() {
        if (bug.getPriority().isNotIndicated()) {
            priority.setManaged(false);
        } else {
            priority.setText("  " + bug.getPriority().getValue().toUpperCase() + "  ");

            String styleClassName = bug.getPriority().getValue() + "-priority";

            priority.getStyleClass().add(styleClassName);
        }
    }
    //@@author

    protected void setTags() {
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
        if (!(other instanceof BugCardKanban)) {
            return false;
        }

        // state check
        BugCardKanban card = (BugCardKanban) other;
        return id.getText().equals(card.id.getText())
                && bug.equals(card.bug);
    }
}

