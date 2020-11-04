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

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.KanBugTracker;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.State;
import seedu.address.testutil.BugBuilder;

public class MoveByStateCommandTest {

    private Model model = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());

    private void execute_invalidBugIndexUnfilteredListFailure(State targetState, State newState) {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBugListByState(targetState).size() + 1);
        MoveByStateCommand moveCommand = new MoveByStateCommand(outOfBoundIndex, newState, targetState);
        assertCommandFailure(moveCommand, model, Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidBugIndexUnfilteredListTodo_failure() {
        execute_invalidBugIndexUnfilteredListFailure(VALID_STATE_TODO, VALID_STATE_DONE);
        execute_invalidBugIndexUnfilteredListFailure(VALID_STATE_BACKLOG, VALID_STATE_TODO);
        execute_invalidBugIndexUnfilteredListFailure(VALID_STATE_ONGOING, VALID_STATE_TODO);
        execute_invalidBugIndexUnfilteredListFailure(VALID_STATE_DONE, VALID_STATE_TODO);
    }
    private void executevalidBugIndex(State targetState, String newState) {
        Bug bugInFilteredList = model.getFilteredBugListByState(targetState).get(INDEX_FIRST_BUG.getZeroBased());
        Bug movedBug = new BugBuilder(bugInFilteredList).withState(newState).build();
        MoveByStateCommand moveCommand = new MoveByStateCommand(INDEX_FIRST_BUG, movedBug.getState(), targetState);

        String expectedMessage = String.format(MoveCommand.MESSAGE_MOVE_BUG_SUCCESS, movedBug);

        Model expectedModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
        expectedModel.setBug(model.getFilteredBugListByState(targetState).get(0),
            movedBug);

        assertCommandSuccess(moveCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_unfilteredList_success() {
        //testing move a bug from backlog to todo
        executevalidBugIndex(VALID_STATE_BACKLOG, "todo");
        //testing move a bug from todo to backlog
        executevalidBugIndex(VALID_STATE_TODO, "backlog");
        //testing move a bug from ongoing to todo
        executevalidBugIndex(VALID_STATE_ONGOING, "todo");
        //testing move a bug from done to doing
        executevalidBugIndex(VALID_STATE_DONE, "todo");
    }

    @Test
    public void execute_filterListSuccess() {
        //filter the list before executing the test
        showBugAtIndex(model, INDEX_FIRST_BUG);
        executevalidBugIndex(VALID_STATE_TODO, "backlog");
    }

    @Test
    public void equals() {
        MoveByStateCommand command = new MoveByStateCommand(INDEX_FIRST_BUG, VALID_STATE_TODO, VALID_STATE_BACKLOG);
        MoveByStateCommand sameCommand = new MoveByStateCommand(INDEX_FIRST_BUG, VALID_STATE_TODO,
            VALID_STATE_BACKLOG);

        //same object -> true
        assertTrue(command.equals(command));

        //same items -> true
        assertTrue(command.equals(sameCommand));

        //null -> false
        assertFalse(command.equals(null));

        //different command
        assertFalse(command.equals(new ExitCommand()));

        //different index
        assertFalse(command.equals(new MoveByStateCommand(INDEX_SECOND_BUG, VALID_STATE_TODO, VALID_STATE_BACKLOG)));

        //different new state
        assertFalse(command.equals(new MoveByStateCommand(INDEX_FIRST_BUG, VALID_STATE_BACKLOG,
            VALID_STATE_TODO)));

        //different target state
        assertFalse(command.equals(new MoveByStateCommand(INDEX_FIRST_BUG, VALID_STATE_TODO, VALID_STATE_TODO)));
    }

}
