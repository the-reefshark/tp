package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.bug.Bug;

/**
 * Panel containing the list of persons.
 */
public class BugListPanel extends UiPart<Region> {
    private static final String FXML = "BugListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BugListPanel.class);

    @FXML
    private ListView<Bug> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public BugListPanel(ObservableList<Bug> bugList) {
        super(FXML);
        personListView.setItems(bugList);
        personListView.setCellFactory(listView -> new BugListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class BugListViewCell extends ListCell<Bug> {
        @Override
        protected void updateItem(Bug bug, boolean empty) {
            super.updateItem(bug, empty);

            if (empty || bug == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new BugCard(bug, getIndex() + 1).getRoot());
            }
        }
    }

}
