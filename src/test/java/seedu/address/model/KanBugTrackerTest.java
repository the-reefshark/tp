package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBugs.ALICE;
import static seedu.address.testutil.TypicalBugs.getTypicalKanBugTracker;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.exceptions.DuplicateBugException;
import seedu.address.testutil.BugBuilder;

public class KanBugTrackerTest {

    private final KanBugTracker kanBugTracker = new KanBugTracker();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), kanBugTracker.getBugList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> kanBugTracker.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyKanBugTracker_replacesData() {
        KanBugTracker newData = getTypicalKanBugTracker();
        kanBugTracker.resetData(newData);
        assertEquals(newData, kanBugTracker);
    }

    @Test
    public void resetData_withDuplicateBugs_throwsDuplicateBugException() {
        // Two bugs with the same identity fields
        Bug editedAlice = new BugBuilder(ALICE).withDescription(VALID_DESCRIPTION_HOMEPAGE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Bug> newBugs = Arrays.asList(ALICE, editedAlice);
        KanBugTrackerStub newData = new KanBugTrackerStub(newBugs);

        assertThrows(DuplicateBugException.class, () -> kanBugTracker.resetData(newData));
    }

    @Test
    public void hasBug_nullBug_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> kanBugTracker.hasBug(null));
    }

    @Test
    public void hasBug_bugNotInKanBugTracker_returnsFalse() {
        assertFalse(kanBugTracker.hasBug(ALICE));
    }

    @Test
    public void hasBug_bugInKanBugTracker_returnsTrue() {
        kanBugTracker.addBug(ALICE);
        assertTrue(kanBugTracker.hasBug(ALICE));
    }

    @Test
    public void hasBug_bugWithSameIdentityFieldsInKanBugTracker_returnsTrue() {
        kanBugTracker.addBug(ALICE);
        Bug editedAlice = new BugBuilder(ALICE).withDescription(VALID_DESCRIPTION_HOMEPAGE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(kanBugTracker.hasBug(editedAlice));
    }

    @Test
    public void getBugList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> kanBugTracker.getBugList().remove(0));
    }

    /**
     * A stub ReadOnlyKanBugTracker whose bugs list can violate interface constraints.
     */
    private static class KanBugTrackerStub implements ReadOnlyKanBugTracker {
        private final ObservableList<Bug> bugs = FXCollections.observableArrayList();

        KanBugTrackerStub(Collection<Bug> bugs) {
            this.bugs.setAll(bugs);
        }

        @Override
        public ObservableList<Bug> getBugList() {
            return bugs;
        }
    }

}
