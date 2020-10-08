package seedu.address.model.bug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBugs.ALICE;
import static seedu.address.testutil.TypicalBugs.BOB;

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
        assertFalse(uniqueBugList.contains(ALICE));
    }

    @Test
    public void contains_bugInList_returnsTrue() {
        uniqueBugList.add(ALICE);
        assertTrue(uniqueBugList.contains(ALICE));
    }

    @Test
    public void contains_bugWithSameIdentityFieldsInList_returnsTrue() {
        uniqueBugList.add(ALICE);
        Bug editedAlice = new BugBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueBugList.contains(editedAlice));
    }

    @Test
    public void add_nullBug_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.add(null));
    }

    @Test
    public void add_duplicateBug_throwsDuplicateBugException() {
        uniqueBugList.add(ALICE);
        assertThrows(DuplicateBugException.class, () -> uniqueBugList.add(ALICE));
    }

    @Test
    public void setBug_nullTargetBug_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.setBug(null, ALICE));
    }

    @Test
    public void setBug_nullEditedBug_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.setBug(ALICE, null));
    }

    @Test
    public void setBug_targetBugNotInList_throwsBugNotFoundException() {
        assertThrows(BugNotFoundException.class, () -> uniqueBugList.setBug(ALICE, ALICE));
    }

    @Test
    public void setBug_editedBugIsSameBug_success() {
        uniqueBugList.add(ALICE);
        uniqueBugList.setBug(ALICE, ALICE);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        expectedUniqueBugList.add(ALICE);
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBug_editedBugHasSameIdentity_success() {
        uniqueBugList.add(ALICE);
        Bug editedAlice = new BugBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueBugList.setBug(ALICE, editedAlice);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        expectedUniqueBugList.add(editedAlice);
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBug_editedBugHasDifferentIdentity_success() {
        uniqueBugList.add(ALICE);
        uniqueBugList.setBug(ALICE, BOB);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        expectedUniqueBugList.add(BOB);
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBug_editedBugHasNonUniqueIdentity_throwsDuplicateBugException() {
        uniqueBugList.add(ALICE);
        uniqueBugList.add(BOB);
        assertThrows(DuplicateBugException.class, () -> uniqueBugList.setBug(ALICE, BOB));
    }

    @Test
    public void remove_nullBug_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.remove(null));
    }

    @Test
    public void remove_bugDoesNotExist_throwsBugNotFoundException() {
        assertThrows(BugNotFoundException.class, () -> uniqueBugList.remove(ALICE));
    }

    @Test
    public void remove_existingBug_removesBug() {
        uniqueBugList.add(ALICE);
        uniqueBugList.remove(ALICE);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBugs_nullUniqueBugList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.setBugs((UniqueBugList) null));
    }

    @Test
    public void setBugs_uniqueBugList_replacesOwnListWithProvidedUniqueBugList() {
        uniqueBugList.add(ALICE);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        expectedUniqueBugList.add(BOB);
        uniqueBugList.setBugs(expectedUniqueBugList);
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBugs_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBugList.setBugs((List<Bug>) null));
    }

    @Test
    public void setBugs_list_replacesOwnListWithProvidedList() {
        uniqueBugList.add(ALICE);
        List<Bug> bugList = Collections.singletonList(BOB);
        uniqueBugList.setBugs(bugList);
        UniqueBugList expectedUniqueBugList = new UniqueBugList();
        expectedUniqueBugList.add(BOB);
        assertEquals(expectedUniqueBugList, uniqueBugList);
    }

    @Test
    public void setBugs_listWithDuplicateBugs_throwsDuplicateBugException() {
        List<Bug> listWithDuplicateBugs = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateBugException.class, () -> uniqueBugList.setBugs(listWithDuplicateBugs));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueBugList.asUnmodifiableObservableList().remove(0));
    }
}
