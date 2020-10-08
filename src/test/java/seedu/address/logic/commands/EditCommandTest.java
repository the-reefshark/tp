package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBugAtIndex;
import static seedu.address.testutil.TypicalBugs.getTypicalKanBugTracker;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUG;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUG;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditBugDescriptor;
import seedu.address.model.KanBugTracker;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.bug.Bug;
import seedu.address.testutil.BugBuilder;
import seedu.address.testutil.EditBugDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Bug editedBug = new BugBuilder().build();
        EditCommand.EditBugDescriptor descriptor = new EditBugDescriptorBuilder(editedBug).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BUG, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BUG_SUCCESS, editedBug);

        Model expectedModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
        expectedModel.setBug(model.getFilteredBugList().get(0), editedBug);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastBug = Index.fromOneBased(model.getFilteredBugList().size());
        Bug lastBug = model.getFilteredBugList().get(indexLastBug.getZeroBased());

        BugBuilder bugInList = new BugBuilder(lastBug);
        Bug editedBug = bugInList.withName(VALID_NAME_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditBugDescriptor descriptor = new EditBugDescriptorBuilder().withName(VALID_NAME_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastBug, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BUG_SUCCESS, editedBug);

        Model expectedModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
        expectedModel.setBug(lastBug, editedBug);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BUG, new EditBugDescriptor());
        Bug editedBug = model.getFilteredBugList().get(INDEX_FIRST_BUG.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BUG_SUCCESS, editedBug);

        Model expectedModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showBugAtIndex(model, INDEX_FIRST_BUG);

        Bug bugInFilteredList = model.getFilteredBugList().get(INDEX_FIRST_BUG.getZeroBased());
        Bug editedBug = new BugBuilder(bugInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BUG,
                new EditBugDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BUG_SUCCESS, editedBug);

        Model expectedModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
        expectedModel.setBug(model.getFilteredBugList().get(0), editedBug);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateBugUnfilteredList_failure() {
        Bug firstBug = model.getFilteredBugList().get(INDEX_FIRST_BUG.getZeroBased());
        EditBugDescriptor descriptor = new EditBugDescriptorBuilder(firstBug).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_BUG, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_BUG);
    }

    @Test
    public void execute_duplicateBugFilteredList_failure() {
        showBugAtIndex(model, INDEX_FIRST_BUG);

        // edit bug in filtered list into a duplicate in address book
        Bug bugInList = model.getKanBugTracker().getBugList().get(INDEX_SECOND_BUG.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BUG,
                new EditBugDescriptorBuilder(bugInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_BUG);
    }

    @Test
    public void execute_invalidBugIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBugList().size() + 1);
        EditBugDescriptor descriptor = new EditBugDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidBugIndexFilteredList_failure() {
        showBugAtIndex(model, INDEX_FIRST_BUG);
        Index outOfBoundIndex = INDEX_SECOND_BUG;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getKanBugTracker().getBugList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditBugDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_BUG, DESC_AMY);

        // same values -> returns true
        EditCommand.EditBugDescriptor copyDescriptor = new EditBugDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_BUG, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_BUG, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_BUG, DESC_BOB)));
    }

}
