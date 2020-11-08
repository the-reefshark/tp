package seedu.address.model.bug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPONENT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBugs.BUG_ELEVEN;
import static seedu.address.testutil.TypicalBugs.BUG_ONE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.bug.exceptions.BugNotFoundException;
import seedu.address.model.bug.exceptions.DuplicateBugException;
import seedu.address.testutil.BugBuilder;

public class UniqueBugListTest {


    private final UniqueBugList uniqueBugList = new UniqueBugList();

    @Test
    public void contains_nullBug_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.contains(null));
    }

    @Test
    public void contains_bugNotInList_returnsFalse() {
        assertFalse(uniqueBugList.contains(BUG_ONE));
    }

    @Test
    public void contains_bugInList_returnsTrue() {
        uniqueBugList.add(BUG_ONE);
        assertTrue(uniqueBugList.contains(BUG_ONE));
    }

    @Test
    public void contains_bugWithSameIdentityFieldsInList_returnsTrue() {
        uniqueBugList.add(BUG_ONE);
        Bug editedAlice =
                new BugBuilder(BUG_ONE).withDescription(VALID_DESCRIPTION_HOMEPAGE).withTags(VALID_TAG_COMPONENT)
                .build();
        assertTrue(uniqueBugList.contains(editedAlice));
    }

    @Test
    public void add_nullBug_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.add(null));
    }

    @Test
    public void add_duplicateBug_throwsDuplicateBugException() {
        uniqueBugList.add(BUG_ONE);
        assertThrows(DuplicateBugException.class, () -> uniqueBugList.add(BUG_ONE));
    }

    @Test
    public void setBug_nullTargetBug_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.setBug(null, BUG_ONE));
    }

    @Test
    public void setBug_nullEditedBug_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.setBug(BUG_ONE, null));
    }

    @Test
    public void setBug_targetBugNotInList_throwsBugNotFoundException() {
        assertThrows(BugNotFoundException.class, () -> uniqueBugList.setBug(BUG_ONE, BUG_ONE));
    }

    @Test
    public void setBug_editedBugIsSameBug_success() {
        uniqueBugList.add(BUG_ONE);
        uniqueBugList.setBug(BUG_ONE, BUG_ONE);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        expectedUniqueBugList.add(BUG_ONE);
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBug_editedBugHasSameIdentity_success() {
        uniqueBugList.add(BUG_ONE);
        Bug editedAlice =
                new BugBuilder(BUG_ONE).withDescription(VALID_DESCRIPTION_HOMEPAGE).withTags(VALID_TAG_COMPONENT)
                .build();
        uniqueBugList.setBug(BUG_ONE, editedAlice);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        expectedUniqueBugList.add(editedAlice);
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBug_editedBugHasDifferentIdentity_success() {
        uniqueBugList.add(BUG_ONE);
        uniqueBugList.setBug(BUG_ONE, BUG_ELEVEN);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        expectedUniqueBugList.add(BUG_ELEVEN);
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBug_editedBugHasNonUniqueIdentity_throwsDuplicateBugException() {
        uniqueBugList.add(BUG_ONE);
        uniqueBugList.add(BUG_ELEVEN);
        assertThrows(DuplicateBugException.class, () -> uniqueBugList.setBug(BUG_ONE, BUG_ELEVEN));
    }

    @Test
    public void remove_nullBug_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.remove(null));
    }

    @Test
    public void remove_bugDoesNotExist_throwsBugNotFoundException() {
        assertThrows(BugNotFoundException.class, () -> uniqueBugList.remove(BUG_ONE));
    }

    @Test
    public void remove_existingBug_removesBug() {
        uniqueBugList.add(BUG_ONE);
        uniqueBugList.remove(BUG_ONE);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBugs_nullUniqueBugList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.setBugs((UniqueBugList) null));
    }

    @Test
    public void setBugs_uniqueBugList_replacesOwnListWithProvidedUniqueBugList() {
        uniqueBugList.add(BUG_ONE);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        expectedUniqueBugList.add(BUG_ELEVEN);
        uniqueBugList.setBugs(expectedUniqueBugList);
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBugs_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.setBugs((List<Bug>) null));
    }

    @Test
    public void setBugs_list_replacesOwnListWithProvidedList() {
        uniqueBugList.add(BUG_ONE);
        List<Bug> bugList = Collections.singletonList(BUG_ELEVEN);
        uniqueBugList.setBugs(bugList);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        expectedUniqueBugList.add(BUG_ELEVEN);
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBugs_listWithDuplicateBugs_throwsDuplicateBugException() {
        List<Bug> listWithDuplicateBugs = Arrays.asList(BUG_ONE, BUG_ONE);
        assertThrows(DuplicateBugException.class, () -> uniqueBugList.setBugs(listWithDuplicateBugs));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueBugList.setUnmodifiableObservableList().remove(0));
    }
}
