package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.bug.Bug;

/**
 * Unmodifiable view of an bug tracker
 */
public interface ReadOnlyKanBugTracker {

    /**
     * Returns an unmodifiable view of the bugs list.
     * This list will not contain any duplicate bugs.
     */
    ObservableList<Bug> getBugList();

}
