package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBugs.getTypicalKanBugTracker;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUG;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUG;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.KanBugTracker;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.State;
import seedu.address.testutil.BugBuilder;
import seedu.address.testutil.EditBugDescriptorBuilder;

public class EditByStateCommandTest {
    private Model model = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());

    @Test
    public void executeIncorrectTargetStateFailure() {
        EditByStateCommand editByStateCommandBacklog = new EditByStateCommand(INDEX_SECOND_BUG,
            new EditBugDescriptorBuilder().withName(VALID_NAME_HOMEPAGE).build(), new State("backlog"));
        assertThrows(CommandException.class, MESSAGE_INVALID_BUG_DISPLAYED_INDEX, ()-> editByStateCommandBacklog
                                                                                           .execute(model));
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Bug editedBug = new BugBuilder().build();
        EditCommand.EditBugDescriptor descriptor = new EditBugDescriptorBuilder(editedBug).build();
        EditByStateCommand editCommand = new EditByStateCommand(INDEX_FIRST_BUG, descriptor, new State("todo"));

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BUG_SUCCESS, editedBug);

        Model expectedModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
        expectedModel.setBug(model.getFilteredBugListByState(new State("todo")).get(0), editedBug);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final EditByStateCommand standardCommand = new EditByStateCommand(INDEX_FIRST_BUG,
                            DESC_PARSER, VALID_STATE_BUG1);
        EditCommand.EditBugDescriptor copyDescriptor = new EditCommand.EditBugDescriptor(DESC_PARSER);
        EditByStateCommand commandWithSameValues = new EditByStateCommand(INDEX_FIRST_BUG, copyDescriptor, new State("todo"));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // normal EditCommand -> false
        final EditCommand editCommand = new EditCommand(INDEX_FIRST_BUG, DESC_PARSER);
        assertFalse(standardCommand.equals(editCommand));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditByStateCommand(INDEX_SECOND_BUG, DESC_PARSER, new State("todo"))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditByStateCommand(INDEX_FIRST_BUG, DESC_HOMEPAGE, new State("todo"))));

        // different target state -> false
        assertFalse(standardCommand.equals(new EditByStateCommand(INDEX_FIRST_BUG, DESC_PARSER, new State("backlog"))));
    }
}
