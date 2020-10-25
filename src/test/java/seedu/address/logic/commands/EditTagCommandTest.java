package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPONENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOGIC;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBugs.getTypicalKanBugTracker;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUG;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUG;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.KanBugTracker;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.bug.Bug;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BugBuilder;



public class EditTagCommandTest {

    //Todo refactor this to pulll out all the declarations of new and old tag

    private Model model = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());
    private Tag newTag = new Tag(VALID_TAG_LOGIC);
    private Tag oldTag = new Tag(VALID_TAG_COMPONENT);

    @Test
    public void execute_invalidBugIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBugList().size() + 1);
        EditTagCommand editTagCommand = new EditTagCommand(outOfBoundIndex, oldTag, newTag);

        assertCommandFailure(editTagCommand, model, Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validEditTagInput_success() {
        Bug initialBug = new BugBuilder().withTags(VALID_TAG_COMPONENT).build();

        try {
            Bug tagEditedBug = EditTagCommand.updateTagInBug(initialBug, oldTag, newTag);
            EditTagCommand editTagCommand = new EditTagCommand(INDEX_FIRST_BUG, oldTag, newTag);
            String expectedMessage = String.format(EditTagCommand.MESSAGE_EDIT_BUG_SUCCESS, tagEditedBug);

            //set initial model
            Model initialModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
            initialModel.setBug(model.getFilteredBugList().get(0), initialBug);

            //set expected model
            Model expectedModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
            expectedModel.setBug(model.getFilteredBugList().get(0), tagEditedBug);

            assertCommandSuccess(editTagCommand, initialModel, expectedMessage, expectedModel);
        } catch (CommandException e) {
            assert false;
        }
    }

    @Test
    public void execute_invalidEditTagInputTagNotInBug_throwCommandException() {
        Bug initialBug = model.getFilteredBugList().get(0);

        EditTagCommand editTagCommand = new EditTagCommand(INDEX_FIRST_BUG, oldTag, newTag);
        String expectedMessage = String.format(EditTagCommand.MESSAGE_INVALID_OLD);

        //set initial model
        Model initialModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
        initialModel.setBug(model.getFilteredBugList().get(0), initialBug);

        //set expected model
        Model expectedModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
        expectedModel.setBug(model.getFilteredBugList().get(0), initialBug);
        assertCommandFailure(editTagCommand, initialModel, expectedMessage);
    }

    @Test
    public void updateTagInBug_validInputs_success() {
        Bug initialBug = new BugBuilder().withTags(VALID_TAG_COMPONENT).build();
        Bug expectedFinalBug = new BugBuilder().withTags(VALID_TAG_LOGIC).build();
        try {
            Bug updatedBug = EditTagCommand.updateTagInBug(initialBug, oldTag, newTag);
            assertEquals(expectedFinalBug, updatedBug);
        } catch (CommandException e) {
            assert false;
        }
    }

    @Test
    public void updateTagInBug_oldTagDoesNotExist_throwCommandException() {
        Bug initialBug = model.getFilteredBugList().get(0);
        String expectedMessage = String.format(EditTagCommand.MESSAGE_INVALID_OLD);

        try {
            EditTagCommand.updateTagInBug(initialBug, oldTag, newTag);
            assert false;
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void updateTagInBug_newTagAlreadyExists_throwCommandException() {
        Bug initialBug = new BugBuilder().withTags(VALID_TAG_LOGIC, VALID_TAG_COMPONENT).build();
        String expectedMessage = String.format(EditTagCommand.MESSAGE_INVALID_NEW);
        try {
            EditTagCommand.updateTagInBug(initialBug, oldTag, newTag);
            assert false;
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

    }

    @Test
    public void updateTagInBug_nullNewTag_throwIllegalArgumentException() {
        Bug initialBug = new BugBuilder().withTags(VALID_TAG_LOGIC, VALID_TAG_COMPONENT).build();
        String expectedMessage = String.format(EditTagCommand.MESSAGE_NOT_UPDATED);
        try {
            EditTagCommand.updateTagInBug(initialBug, oldTag, null);
            assert false;
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

    }


    @Test
    public void updateTagInBug_nullOldTag_throwIllegalArgumentException() {
        Bug initialBug = new BugBuilder().withTags(VALID_TAG_LOGIC, VALID_TAG_COMPONENT).build();
        String expectedMessage = String.format(EditTagCommand.MESSAGE_NOT_UPDATED);
        try {
            EditTagCommand.updateTagInBug(initialBug, null, newTag);
            assert false;
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

    }


    @Test
    public void updateTagInBug_nullInitialBug_throwIllegalArgumentException() {
        String expectedMessage = String.format(EditTagCommand.MESSAGE_NOT_UPDATED);
        try {
            EditTagCommand.updateTagInBug(null, oldTag, newTag);
            assert false;
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

    }

    @Test
    public void equals() {
        EditTagCommand editTagCommand = new EditTagCommand(INDEX_FIRST_BUG, new Tag(VALID_TAG_LOGIC),
                new Tag(VALID_TAG_COMPONENT));
        EditTagCommand editTagCommandDuplicate = new EditTagCommand(INDEX_FIRST_BUG, new Tag(VALID_TAG_LOGIC),
                new Tag(VALID_TAG_COMPONENT));
        EditTagCommand editTagCommandDifferentIndex = new EditTagCommand(INDEX_SECOND_BUG, new Tag(VALID_TAG_LOGIC),
                new Tag(VALID_TAG_COMPONENT));
        EditTagCommand editTagCommandDifferentTags = new EditTagCommand(INDEX_FIRST_BUG, new Tag(VALID_TAG_COMPONENT),
                new Tag(VALID_TAG_LOGIC));
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_BUG, new Tag(VALID_TAG_COMPONENT));

        //same command
        assertTrue(editTagCommand.equals(editTagCommand));
        //null command
        assertFalse(editTagCommand.equals(null));
        //different command types
        assertFalse(editTagCommand.equals(addTagCommand));
        //duplicate command
        assertTrue(editTagCommand.equals(editTagCommandDuplicate));
        //different indexes
        assertFalse(editTagCommand.equals(editTagCommandDifferentIndex));
        //different tags
        assertFalse(editTagCommand.equals(editTagCommandDifferentTags));
    }
}
