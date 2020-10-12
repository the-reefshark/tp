package seedu.address.model.bug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPONENT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBugs.BUGELEVEN;
import static seedu.address.testutil.TypicalBugs.BUGONE;

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
        assertFalse(uniqueBugList.contains(BUGONE));
    }

    @Test
    public void contains_bugInList_returnsTrue() {
        uniqueBugList.add(BUGONE);
        assertTrue(uniqueBugList.contains(BUGONE));
    }

    @Test
    public void contains_bugWithSameIdentityFieldsInList_returnsTrue() {
        uniqueBugList.add(BUGONE);
        Bug editedAlice =
                new BugBuilder(BUGONE).withDescription(VALID_DESCRIPTION_HOMEPAGE).withTags(VALID_TAG_COMPONENT)
                .build();
        assertTrue(uniqueBugList.contains(editedAlice));
    }

    @Test
    public void add_nullBug_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.add(null));
    }

    @Test
    public void add_duplicateBug_throwsDuplicateBugException() {
        uniqueBugList.add(BUGONE);
        assertThrows(DuplicateBugException.class, () -> uniqueBugList.add(BUGONE));
    }

    @Test
    public void setBug_nullTargetBug_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.setBug(null, BUGONE));
    }

    @Test
    public void setBug_nullEditedBug_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.setBug(BUGONE, null));
    }

    @Test
    public void setBug_targetBugNotInList_throwsBugNotFoundException() {
        assertThrows(BugNotFoundException.class, () -> uniqueBugList.setBug(BUGONE, BUGONE));
    }

    @Test
    public void setBug_editedBugIsSameBug_success() {
        uniqueBugList.add(BUGONE);
        uniqueBugList.setBug(BUGONE, BUGONE);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        expectedUniqueBugList.add(BUGONE);
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBug_editedBugHasSameIdentity_success() {
        uniqueBugList.add(BUGONE);
        Bug editedAlice =
                new BugBuilder(BUGONE).withDescription(VALID_DESCRIPTION_HOMEPAGE).withTags(VALID_TAG_COMPONENT)
                .build();
        uniqueBugList.setBug(BUGONE, editedAlice);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        expectedUniqueBugList.add(editedAlice);
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBug_editedBugHasDifferentIdentity_success() {
        uniqueBugList.add(BUGONE);
        uniqueBugList.setBug(BUGONE, BUGELEVEN);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        expectedUniqueBugList.add(BUGELEVEN);
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBug_editedBugHasNonUniqueIdentity_throwsDuplicateBugException() {
        uniqueBugList.add(BUGONE);
        uniqueBugList.add(BUGELEVEN);
        assertThrows(DuplicateBugException.class, () -> uniqueBugList.setBug(BUGONE, BUGELEVEN));
    }

    @Test
    public void remove_nullBug_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.remove(null));
    }

    @Test
    public void remove_bugDoesNotExist_throwsBugNotFoundException() {
        assertThrows(BugNotFoundException.class, () -> uniqueBugList.remove(BUGONE));
    }

    @Test
    public void remove_existingBug_removesBug() {
        uniqueBugList.add(BUGONE);
        uniqueBugList.remove(BUGONE);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBugs_nullUniqueBugList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.setBugs((UniqueBugList) null));
    }

    @Test
    public void setBugs_uniqueBugList_replacesOwnListWithProvidedUniqueBugList() {
        uniqueBugList.add(BUGONE);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        expectedUniqueBugList.add(BUGELEVEN);
        uniqueBugList.setBugs(expectedUniqueBugList);
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBugs_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.setBugs((List<Bug>) null));
    }

    @Test
    public void setBugs_list_replacesOwnListWithProvidedList() {
        uniqueBugList.add(BUGONE);
        List<Bug> bugList = Collections.singletonList(BUGELEVEN);
        uniqueBugList.setBugs(bugList);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        expectedUniqueBugList.add(BUGELEVEN);
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBugs_listWithDuplicateBugs_throwsDuplicateBugException() {
        List<Bug> listWithDuplicateBugs = Arrays.asList(BUGONE, BUGONE);
        assertThrows(DuplicateBugException.class, () -> uniqueBugList.setBugs(listWithDuplicateBugs));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueBugList.asUnmodifiableObservableList().remove(0));
    }
}
