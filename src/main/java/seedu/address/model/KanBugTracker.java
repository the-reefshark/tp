package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.UniqueBugList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameBug comparison)
 */
public class KanBugTracker implements ReadOnlyKanBugTracker {

    private final UniqueBugList bugs;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        bugs = new UniqueBugList();
    }

    public KanBugTracker() {}

    /**
     * Creates an KanBugTracker using the Bugs in the {@code toBeCopied}
     */
    public KanBugTracker(ReadOnlyKanBugTracker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the bug list with {@code bugs}.
     * {@code bugs} must not contain duplicate bugs.
     */
    public void setBugs(List<Bug> bugs) {
        this.bugs.setBugs(bugs);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyKanBugTracker newData) {
        requireNonNull(newData);

        setBugs(newData.getBugList());
    }

    //// bug-level operations

    /**
     * Returns true if a bug with the same identity as {@code bug} exists in the address book.
     */
    public boolean hasBug(Bug bug) {
        requireNonNull(bug);
        return bugs.contains(bug);
    }

    /**
     * Adds a bug to the KanBug Tracker.
     * The bug must not already exist in the KanBug Tracker.
     */
    public void addBug(Bug p) {
        bugs.add(p);
    }

    /**
     * Replaces the given bug {@code target} in the list with {@code editedBug}.
     * {@code target} must exist in the KanBug Tracker.
     * The bug identity of {@code editedBug} must not be the same as another existing bug in the KanBug Tracker.
     */
    public void setBug(Bug target, Bug editedBug) {
        requireNonNull(editedBug);

        bugs.setBug(target, editedBug);
    }

    /**
     * Removes {@code key} from this {@code KanBugTracker}.
     * {@code key} must exist in the KanBug Tracker.
     */
    public void removeBug(Bug key) {
        bugs.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return bugs.asUnmodifiableObservableList().size() + " bugs";
        // TODO: refine later
    }

    @Override
    public ObservableList<Bug> getBugList() {
        return bugs.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof KanBugTracker // instanceof handles nulls
                && bugs.equals(((KanBugTracker) other).bugs));
    }

    @Override
    public int hashCode() {
        return bugs.hashCode();
    }
}
