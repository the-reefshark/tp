package seedu.address.model.bug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPONENT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBugs.BUGELEVEN;
import static seedu.address.testutil.TypicalBugs.BUGONE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BugBuilder;

public class BugTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Bug bug = new BugBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> bug.getTags().remove(0));
    }

    @Test
    public void isSameBug() {
        // same object -> returns true
        assertTrue(BUGONE.isSameBug(BUGONE));

        // null -> returns false
        assertFalse(BUGONE.isSameBug(null));

        // different state -> returns false
        Bug editedBugOne = new BugBuilder(BUGONE).withState(VALID_STATE_HOMEPAGE).build();
        assertFalse(BUGONE.isSameBug(editedBugOne));

        // different name -> returns false
        editedBugOne = new BugBuilder(BUGONE).withName(VALID_NAME_HOMEPAGE).build();
        assertFalse(BUGONE.isSameBug(editedBugOne));

        // same name, same state, different attributes -> returns true
        editedBugOne = new BugBuilder(BUGONE).withDescription(VALID_DESCRIPTION_HOMEPAGE)
                .withTags(VALID_TAG_COMPONENT).withPriority(VALID_PRIORITY_HOMEPAGE).build();
        assertTrue(BUGONE.isSameBug(editedBugOne));

        // same name, state and attributes -> returns true
        editedBugOne = new BugBuilder(BUGONE).build();
        assertTrue(BUGONE.isSameBug(editedBugOne));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Bug aliceCopy = new BugBuilder(BUGONE).build();
        assertEquals(BUGONE, aliceCopy);

        // same object -> returns true
        assertEquals(BUGONE, BUGONE);

        // null -> returns false
        assertNotEquals(null, BUGONE);

        // different type -> returns false
        assertNotEquals(5, BUGONE);

        // different bug -> returns false
        assertNotEquals(BUGONE, BUGELEVEN);

        // different name -> returns false
        Bug editedBugOne = new BugBuilder(BUGONE).withName(VALID_NAME_HOMEPAGE).build();
        assertNotEquals(BUGONE, editedBugOne);

        // different state -> returns false
        editedBugOne = new BugBuilder(BUGONE).withState(VALID_STATE_HOMEPAGE).build();
        assertNotEquals(BUGONE, editedBugOne);

        // different address -> returns false
        editedBugOne = new BugBuilder(BUGONE).withDescription(VALID_DESCRIPTION_HOMEPAGE).build();
        assertNotEquals(BUGONE, editedBugOne);

        // different tags -> returns false
        editedBugOne = new BugBuilder(BUGONE).withTags(VALID_TAG_COMPONENT).build();
        assertNotEquals(BUGONE, editedBugOne);

        // different priority -> return false
        editedBugOne = new BugBuilder(BUGONE).withPriority(VALID_PRIORITY_HOMEPAGE).build();
        assertNotEquals(BUGONE, editedBugOne);
    }
}
