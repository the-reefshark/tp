package seedu.address.model.bug;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.bug.exceptions.BugNotFoundException;
import seedu.address.model.bug.exceptions.DuplicateBugException;

/**
 * A list of bugs that enforces uniqueness between its elements and does not allow nulls.
 * A bug is considered unique by comparing using {@code Bug#isSameBug(Bug)}. As such, adding and updating of
 * bugs uses Bug#isSameBug(Bug) for equality so as to ensure that the bug being added or updated is
 * unique in terms of identity in the UniqueBugList. However, the removal of a bug uses Bug#equals(Object) so
 * as to ensure that the bug with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Bug#isSameBug(Bug)
 */
public class UniqueBugList implements Iterable<Bug> {

    private final ObservableList<Bug> internalList = FXCollections.observableArrayList();
    private final ObservableList<Bug> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent bug as the given argument.
     */
    public boolean contains(Bug toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameBug);
    }

    /**
     * Adds a bug to the list.
     * The bug must not already exist in the list.
     */
    public void add(Bug toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateBugException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the bug {@code target} in the list with {@code editedBug}.
     * {@code target} must exist in the list.
     * The bug identity of {@code editedBug} must not be the same as another existing bug in the list.
     */
    public void setBug(Bug target, Bug editedBug) {
        requireAllNonNull(target, editedBug);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BugNotFoundException();
        }

        if (!target.isSameBug(editedBug) && contains(editedBug)) {
            throw new DuplicateBugException();
        }

        internalList.set(index, editedBug);
    }

    /**
     * Removes the equivalent bug from the list.
     * The bug must exist in the list.
     */
    public void remove(Bug toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new BugNotFoundException();
        }
    }

    public void setBugs(UniqueBugList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code bugs}.
     * {@code bugs} must not contain duplicate bugs.
     */
    public void setBugs(List<Bug> bugs) {
        requireAllNonNull(bugs);
        if (!bugsAreUnique(bugs)) {
            throw new DuplicateBugException();
        }

        internalList.setAll(bugs);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Bug> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Bug> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueBugList // instanceof handles nulls
                        && internalList.equals(((UniqueBugList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code bugs} contains only unique bugs.
     */
    private boolean bugsAreUnique(List<Bug> bugs) {
        for (int i = 0; i < bugs.size() - 1; i++) {
            for (int j = i + 1; j < bugs.size(); j++) {
                if (bugs.get(i).isSameBug(bugs.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
