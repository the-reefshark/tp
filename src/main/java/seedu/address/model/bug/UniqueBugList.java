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
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Bug#isSamePerson(Bug)
 */
public class UniqueBugList implements Iterable<Bug> {

    private final ObservableList<Bug> internalList = FXCollections.observableArrayList();
    private final ObservableList<Bug> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Bug toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Bug toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateBugException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Bug target, Bug editedBug) {
        requireAllNonNull(target, editedBug);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BugNotFoundException();
        }

        if (!target.isSamePerson(editedBug) && contains(editedBug)) {
            throw new DuplicateBugException();
        }

        internalList.set(index, editedBug);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
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
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setBugs(List<Bug> bugs) {
        requireAllNonNull(bugs);
        if (!personsAreUnique(bugs)) {
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
     * Returns true if {@code persons} contains only unique persons.
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
