package seedu.address.model.bug;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.bug.exceptions.DuplicatePersonException;
import seedu.address.model.bug.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A bug is considered unique by comparing using {@code Bug#isSamePerson(Bug)}. As such, adding and updating of
 * persons uses Bug#isSamePerson(Bug) for equality so as to ensure that the bug being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a bug uses Bug#equals(Object) so
 * as to ensure that the bug with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Bug#isSamePerson(Bug)
 */
public class UniquePersonList implements Iterable<Bug> {

    private final ObservableList<Bug> internalList = FXCollections.observableArrayList();
    private final ObservableList<Bug> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent bug as the given argument.
     */
    public boolean contains(Bug toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a bug to the list.
     * The bug must not already exist in the list.
     */
    public void add(Bug toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the bug {@code target} in the list with {@code editedBug}.
     * {@code target} must exist in the list.
     * The bug identity of {@code editedBug} must not be the same as another existing bug in the list.
     */
    public void setPerson(Bug target, Bug editedBug) {
        requireAllNonNull(target, editedBug);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedBug) && contains(editedBug)) {
            throw new DuplicatePersonException();
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
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code bugs}.
     * {@code bugs} must not contain duplicate bugs.
     */
    public void setPersons(List<Bug> bugs) {
        requireAllNonNull(bugs);
        if (!personsAreUnique(bugs)) {
            throw new DuplicatePersonException();
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
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code bugs} contains only unique bugs.
     */
    private boolean personsAreUnique(List<Bug> bugs) {
        for (int i = 0; i < bugs.size() - 1; i++) {
            for (int j = i + 1; j < bugs.size(); j++) {
                if (bugs.get(i).isSamePerson(bugs.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
