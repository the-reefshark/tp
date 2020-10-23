package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_BUG1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_BUG2;
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

public class MoveByStateCommandTest {

    private Model model = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());

    @Test
    public void execute_invalidBugIndexUnfilteredListTodo_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBugListByState(VALID_STATE_BUG1).size() + 1);
        MoveByStateCommand moveCommand = new MoveByStateCommand(outOfBoundIndex, VALID_STATE_BUG1, VALID_STATE_BUG2);
        assertCommandFailure(moveCommand, model, Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
    }

    @Test
    public void execute_filteredList_success() {
        showBugAtIndex(model, INDEX_SECOND_BUG);

        Bug bugInFilteredList = model.getFilteredBugListByState(VALID_STATE_BUG1).get(INDEX_FIRST_BUG.getZeroBased());
        Bug movedBug = new BugBuilder(bugInFilteredList).withState(VALID_STATE_PARSER).build();
        MoveByStateCommand moveCommand = new MoveByStateCommand(INDEX_FIRST_BUG, movedBug.getState(), VALID_STATE_BUG1);

        String expectedMessage = String.format(MoveCommand.MESSAGE_MOVE_BUG_SUCCESS, movedBug);

        Model expectedModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
        expectedModel.setBug(model.getFilteredBugListByState(VALID_STATE_BUG1).get(0),
            movedBug);

        assertCommandSuccess(moveCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void equals() {
        MoveByStateCommand command = new MoveByStateCommand(INDEX_FIRST_BUG, VALID_STATE_BUG1, VALID_STATE_BUG2);
        MoveByStateCommand sameCommand = new MoveByStateCommand(INDEX_FIRST_BUG, VALID_STATE_BUG1,
            VALID_STATE_BUG2);

        //same object -> true
        assertTrue(command.equals(command));

        //same items -> true
        assertTrue(command.equals(sameCommand));

        //null -> false
        assertFalse(command.equals(null));

        //different command
        assertFalse(command.equals(new ExitCommand()));

        //different index
        assertFalse(command.equals(new MoveByStateCommand(INDEX_SECOND_BUG, VALID_STATE_BUG1, VALID_STATE_BUG2)));

        //different new state
        assertFalse(command.equals(new MoveByStateCommand(INDEX_FIRST_BUG, VALID_STATE_BUG2,
            VALID_STATE_BUG1)));

        //different target state
        assertFalse(command.equals(new MoveByStateCommand(INDEX_FIRST_BUG, VALID_STATE_BUG1, VALID_STATE_BUG1)));
    }
}
