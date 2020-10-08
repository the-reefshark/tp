package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBugAtIndex;
import static seedu.address.testutil.TypicalBugs.getTypicalKanBugTracker;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUG;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUG;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.bug.Bug;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Bug bugToDelete = model.getFilteredBugList().get(INDEX_FIRST_BUG.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_BUG);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_BUG_SUCCESS, bugToDelete);

        ModelManager expectedModel = new ModelManager(model.getKanBugTracker(), new UserPrefs());
        expectedModel.deleteBug(bugToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBugList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBugAtIndex(model, INDEX_FIRST_BUG);

        Bug bugToDelete = model.getFilteredBugList().get(INDEX_FIRST_BUG.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_BUG);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_BUG_SUCCESS, bugToDelete);

        Model expectedModel = new ModelManager(model.getKanBugTracker(), new UserPrefs());
        expectedModel.deleteBug(bugToDelete);
        showNoBug(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBugAtIndex(model, INDEX_FIRST_BUG);

        Index outOfBoundIndex = INDEX_SECOND_BUG;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getKanBugTracker().getBugList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_BUG);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_BUG);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_BUG);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different bug -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoBug(Model model) {
        model.updateFilteredBugList(p -> false);

        assertTrue(model.getFilteredBugList().isEmpty());
    }
}
