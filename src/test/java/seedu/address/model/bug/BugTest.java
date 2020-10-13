package seedu.address.model.bug;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HOMEPAGE;
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

        // different phone and state -> returns false
        Bug editedAlice = new BugBuilder(BUGONE).withState(VALID_STATE_HOMEPAGE).build();
        assertFalse(BUGONE.isSameBug(editedAlice));

        // different name -> returns false
        editedAlice = new BugBuilder(BUGONE).withName(VALID_NAME_HOMEPAGE).build();
        assertFalse(BUGONE.isSameBug(editedAlice));

        // same name, same state, different attributes -> returns true
        editedAlice = new BugBuilder(BUGONE).withDescription(VALID_DESCRIPTION_HOMEPAGE)
                .withTags(VALID_TAG_COMPONENT).build();
        assertTrue(BUGONE.isSameBug(editedAlice));

        // same name, same phone, same state, different attributes -> returns true
        editedAlice = new BugBuilder(BUGONE)
                          .withDescription(VALID_DESCRIPTION_HOMEPAGE).withTags(VALID_TAG_COMPONENT).build();
        assertTrue(BUGONE.isSameBug(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Bug aliceCopy = new BugBuilder(BUGONE).build();
        assertTrue(BUGONE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(BUGONE.equals(BUGONE));

        // null -> returns false
        assertFalse(BUGONE.equals(null));

        // different type -> returns false
        assertFalse(BUGONE.equals(5));

        // different bug -> returns false
        assertFalse(BUGONE.equals(BUGELEVEN));

        // different name -> returns false
        Bug editedAlice = new BugBuilder(BUGONE).withName(VALID_NAME_HOMEPAGE).build();
        assertFalse(BUGONE.equals(editedAlice));

        // different state -> returns false
        editedAlice = new BugBuilder(BUGONE).withState(VALID_STATE_HOMEPAGE).build();
        assertFalse(BUGONE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new BugBuilder(BUGONE).withDescription(VALID_DESCRIPTION_HOMEPAGE).build();
        assertFalse(BUGONE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new BugBuilder(BUGONE).withTags(VALID_TAG_COMPONENT).build();
        assertFalse(BUGONE.equals(editedAlice));
    }
}
