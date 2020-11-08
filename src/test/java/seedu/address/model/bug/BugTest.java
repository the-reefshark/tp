package seedu.address.model.bug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPONENT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBugs.BUG_ELEVEN;
import static seedu.address.testutil.TypicalBugs.BUG_ONE;

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
        assertTrue(BUG_ONE.isSameBug(BUG_ONE));

        // null -> returns false
        assertFalse(BUG_ONE.isSameBug(null));

        // different name -> returns false
        Bug editedBugOne = new BugBuilder(BUG_ONE).withName(VALID_NAME_HOMEPAGE).build();
        assertFalse(BUG_ONE.isSameBug(editedBugOne));

        // same name, different attributes -> returns true
        editedBugOne = new BugBuilder(BUG_ONE).withState(VALID_STATE_HOMEPAGE)
                .withDescription(VALID_DESCRIPTION_HOMEPAGE).withTags(VALID_TAG_COMPONENT)
                .withPriority(VALID_PRIORITY_HOMEPAGE).build();
        assertTrue(BUG_ONE.isSameBug(editedBugOne));

        // same name and attributes -> returns true
        editedBugOne = new BugBuilder(BUG_ONE).build();
        assertTrue(BUG_ONE.isSameBug(editedBugOne));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Bug aliceCopy = new BugBuilder(BUG_ONE).build();
        assertEquals(BUG_ONE, aliceCopy);

        // same object -> returns true
        assertEquals(BUG_ONE, BUG_ONE);

        // null -> returns false
        assertNotEquals(null, BUG_ONE);

        // different type -> returns false
        assertNotEquals(5, BUG_ONE);

        // different bug -> returns false
        assertNotEquals(BUG_ONE, BUG_ELEVEN);

        // different name -> returns false
        Bug editedBugOne = new BugBuilder(BUG_ONE).withName(VALID_NAME_HOMEPAGE).build();
        assertNotEquals(BUG_ONE, editedBugOne);

        // different state -> returns false
        editedBugOne = new BugBuilder(BUG_ONE).withState(VALID_STATE_HOMEPAGE).build();
        assertNotEquals(BUG_ONE, editedBugOne);

        // different description -> returns false
        editedBugOne = new BugBuilder(BUG_ONE).withDescription(VALID_DESCRIPTION_HOMEPAGE).build();
        assertNotEquals(BUG_ONE, editedBugOne);

        // different tags -> returns false
        editedBugOne = new BugBuilder(BUG_ONE).withTags(VALID_TAG_COMPONENT).build();
        assertNotEquals(BUG_ONE, editedBugOne);

        // different note -> returns false
        editedBugOne = new BugBuilder(BUG_ONE).withNote(VALID_NOTE_HOMEPAGE).build();
        assertNotEquals(BUG_ONE, editedBugOne);

        // different priority -> returns false
        editedBugOne = new BugBuilder(BUG_ONE).withPriority(VALID_PRIORITY_HOMEPAGE).build();
        assertNotEquals(BUG_ONE, editedBugOne);
    }
}
