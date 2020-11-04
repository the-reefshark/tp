package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_BACKLOG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_DONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_ONGOING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_TODO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBugAtIndex;
import static seedu.address.testutil.TypicalBugs.getTypicalKanBugTracker;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUG;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUG;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_BUG;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.State;

public class DeleteByStateCommandTest {
    private Model model = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());

    private void executeInvalidIndex(State state) {
        ObservableList<Bug> list = model.getFilteredBugListByState(state);
        Index outOfBoundIndex = Index.fromOneBased(list.size() + 1);
        DeleteByStateCommand deleteCommand = new DeleteByStateCommand(outOfBoundIndex, state);
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
    }
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        //delete a bug from state todo
        executeInvalidIndex(VALID_STATE_TODO);
        //delete a bug from state ongoing
        executeInvalidIndex(VALID_STATE_ONGOING);
        //delete a bug from state done
        executeInvalidIndex(VALID_STATE_DONE);
        //delete a bug from state backlog
        executeInvalidIndex(VALID_STATE_BACKLOG);
    }

    private void executeValidIndex(State state) {
        Model model = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());
        Bug bugToDelete = model.getFilteredBugListByState(state).get(INDEX_FIRST_BUG.getZeroBased());
        DeleteByStateCommand deleteCommand = new DeleteByStateCommand(INDEX_FIRST_BUG, state);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_BUG_SUCCESS, bugToDelete);

        Model expectedModel = new ModelManager(model.getKanBugTracker(), new UserPrefs());
        expectedModel.deleteBug(bugToDelete);
        //showNoBug(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validUnfilteredListTodoState_success() {
        executeValidIndex(VALID_STATE_BACKLOG);
        executeValidIndex(VALID_STATE_DONE);
        executeValidIndex(VALID_STATE_TODO);
        executeValidIndex(VALID_STATE_ONGOING);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBugAtIndex(model, INDEX_FIRST_BUG);

        Index outOfBoundIndex = INDEX_SECOND_BUG;
        // ensures that outOfBoundIndex is still in bounds of bug tracker list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getKanBugTracker().getBugList().size());

        DeleteCommand deleteCommand = new DeleteByStateCommand(outOfBoundIndex, VALID_STATE_TODO);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredListDoneState_success() {
        showBugAtIndex(model, INDEX_THIRD_BUG);

        Bug bugToDelete = model.getFilteredBugListByState(new State("done")).get(INDEX_FIRST_BUG.getZeroBased());
        DeleteByStateCommand deleteCommand = new DeleteByStateCommand(INDEX_FIRST_BUG, new State("done"));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_BUG_SUCCESS, bugToDelete);

        Model expectedModel = new ModelManager(model.getKanBugTracker(), new UserPrefs());
        expectedModel.deleteBug(bugToDelete);
        showNoBug(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteByStateCommand deleteFirstStateBug1 = new DeleteByStateCommand(INDEX_FIRST_BUG,
            VALID_STATE_TODO);
        DeleteByStateCommand deleteFirstStateBug2 = new DeleteByStateCommand(INDEX_FIRST_BUG,
            VALID_STATE_BACKLOG);
        DeleteByStateCommand deleteSecondStateBug1 = new DeleteByStateCommand(INDEX_SECOND_BUG,
            VALID_STATE_TODO);

        // same object -> returns true
        assertTrue(deleteFirstStateBug1.equals(deleteFirstStateBug1));

        //null -> false
        assertFalse(deleteFirstStateBug1.equals(null));

        DeleteByStateCommand deleteFirstStateBug1Copy = new DeleteByStateCommand(INDEX_FIRST_BUG,
            VALID_STATE_TODO);

        //copy -> true
        assertTrue(deleteFirstStateBug1.equals(deleteFirstStateBug1Copy));

        //different Index -> false
        assertFalse(deleteFirstStateBug1.equals(deleteSecondStateBug1));

        //different target state -> flase
        assertFalse(deleteFirstStateBug1.equals(deleteFirstStateBug2));

    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoBug(Model model) {
        model.updateFilteredBugList(p -> false);

        assertTrue(model.getFilteredBugList().isEmpty());
    }
}
