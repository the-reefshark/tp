package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_BACKLOG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_TODO;
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


    /**
     * Check if moving the bug at {@code index} to {@code validState} in the {@code model} is success.
     * @param model A Model that can be either filtered or unfiltered.
     * @param index A valid index in the {@code model}.
     * @param validState A valid State.
     */
    private void execute_anyList_success(Model model, Index index, String validState) {
        Bug bugAtIndex = model.getFilteredBugList().get(index.getZeroBased());

        Bug movedBug = new BugBuilder(bugAtIndex).withState(validState).build();

        MoveCommand moveCommand = new MoveCommand(index, movedBug.getState());

        String expectedMessage = String.format(MoveCommand.MESSAGE_MOVE_BUG_SUCCESS, movedBug);

        Model expectedModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
        expectedModel.setBug(model.getFilteredBugList().get(index.getZeroBased()), movedBug);

        assertCommandSuccess(moveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_fieldsSpecifiedUnfilteredList_success() {
        execute_anyList_success(model, INDEX_FIRST_BUG, VALID_STATE_PARSER);
    }

    @Test
    public void execute_filteredList_success() {
        showBugAtIndex(model, INDEX_FIRST_BUG);
        execute_anyList_success(model, INDEX_FIRST_BUG, VALID_STATE_PARSER);
    }

    @Test
    public void execute_invalidBugIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBugList().size() + 1);
        MoveCommand moveCommand = new MoveCommand(outOfBoundIndex, VALID_STATE_TODO);

        assertCommandFailure(moveCommand, model, Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
    }

    @Test
    public void execute_moveSameState_failure() {
        Bug bugInList = model.getFilteredBugList().get(INDEX_FIRST_BUG.getZeroBased());

        MoveCommand moveCommand = new MoveCommand(Index.fromZeroBased(1), bugInList.getState());

        assertCommandFailure(moveCommand, model, MoveCommand.MESSAGE_DUPLICATE_STATE);
    }

    @Test
    public void equals() {
        final MoveCommand standardCommand = new MoveCommand(INDEX_FIRST_BUG, VALID_STATE_TODO);

        // same values -> returns true
        MoveCommand commandWithSameValues = new MoveCommand(INDEX_FIRST_BUG, VALID_STATE_TODO);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new MoveCommand(INDEX_SECOND_BUG, VALID_STATE_TODO)));

        // different state -> returns false
        assertFalse(standardCommand.equals(new MoveCommand(INDEX_FIRST_BUG, VALID_STATE_BACKLOG)));
    }
}
