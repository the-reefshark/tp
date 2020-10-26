package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_BUG1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_BUG2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBugAtIndex;
import static seedu.address.testutil.TypicalBugs.getTypicalKanBugTracker;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUG;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUG;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.KanBugTracker;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.bug.Bug;
import seedu.address.testutil.BugBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for MoveCommand.
 */
class MoveCommandTest {

    private Model model = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());

    @Test
    public void execute_fieldsSpecifiedUnfilteredList_success() {
        Bug movedBug = new BugBuilder(model.getFilteredBugList().get(0))
                .withState(VALID_STATE_PARSER).build();
        MoveCommand moveCommand = new MoveCommand(INDEX_FIRST_BUG, movedBug.getState());

        String expectedMessage = String.format(MoveCommand.MESSAGE_MOVE_BUG_SUCCESS, movedBug);

        Model expectedModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
        expectedModel.setBug(model.getFilteredBugList().get(0), movedBug);

        assertCommandSuccess(moveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showBugAtIndex(model, INDEX_FIRST_BUG);

        Bug bugInFilteredList = model.getFilteredBugList().get(INDEX_FIRST_BUG.getZeroBased());
        Bug movedBug = new BugBuilder(bugInFilteredList).withState(VALID_STATE_PARSER).build();
        MoveCommand moveCommand = new MoveCommand(INDEX_FIRST_BUG, movedBug.getState());

        String expectedMessage = String.format(MoveCommand.MESSAGE_MOVE_BUG_SUCCESS, movedBug);

        Model expectedModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
        expectedModel.setBug(model.getFilteredBugList().get(0), movedBug);

        assertCommandSuccess(moveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidBugIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBugList().size() + 1);
        MoveCommand moveCommand = new MoveCommand(outOfBoundIndex, VALID_STATE_BUG1);

        assertCommandFailure(moveCommand, model, Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
    }

    @Test
    public void execute_createDuplicateBugs_failure() {
        Bug bugInList = model.getFilteredBugList().get(INDEX_FIRST_BUG.getZeroBased());

        // make sure that this bug is different from a bug in the model only in state
        Bug bugWithDifferentState = new BugBuilder(bugInList)
                .withState(bugInList.getState().getStringOfValue().equals(VALID_STATE_HOMEPAGE)
                        ? VALID_STATE_PARSER : VALID_STATE_HOMEPAGE).build();

        // set the 2nd bug as the bugWithDifferentState
        Model newModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
        newModel.setBug(model.getFilteredBugList().get(1), bugWithDifferentState);

        MoveCommand moveCommand = new MoveCommand(Index.fromZeroBased(1), bugInList.getState());

        assertCommandFailure(moveCommand, model, MoveCommand.MESSAGE_DUPLICATE_BUG);
    }

    @Test
    public void equals() {
        final MoveCommand standardCommand = new MoveCommand(INDEX_FIRST_BUG, VALID_STATE_BUG1);

        // same values -> returns true
        MoveCommand commandWithSameValues = new MoveCommand(INDEX_FIRST_BUG, VALID_STATE_BUG1);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new MoveCommand(INDEX_SECOND_BUG, VALID_STATE_BUG1)));

        // different state -> returns false
        assertFalse(standardCommand.equals(new MoveCommand(INDEX_FIRST_BUG, VALID_STATE_BUG2)));
    }
}
