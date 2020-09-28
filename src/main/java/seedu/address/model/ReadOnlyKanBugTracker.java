package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.bug.Bug;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyKanBugTracker {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Bug> getBugList();

}
