package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.AddTagCommand.addTagToBug;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_BUG1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_BUG2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPONENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOGIC;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBugs.getTypicalKanBugTracker;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUG;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUG;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.KanBugTracker;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.Note;
import seedu.address.model.bug.Priority;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BugBuilder;

public class AddTagByStateCommandTest {

    private Model model = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());
    private State initialState = VALID_STATE_BUG1;
    private Tag newTag = new Tag(VALID_TAG_LOGIC);

    // tests the case where the user inputs the wrong column as well since it would be considered out of bounds.
    @Test
    public void execute_invalidBugIndexFilteredListByState_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBugList().size() + 1);
        State newState = VALID_STATE_BUG1;
        AddTagByStateCommand addTagByStateCommand = new AddTagByStateCommand(outOfBoundIndex, newTag, newState);

        assertCommandFailure(addTagByStateCommand, model, Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
    }

    @Test
    public void execute_allFieldsSpecifiedFilteredListByState_success() {
        try {

            State initialState = VALID_STATE_BUG1;
            Bug bug = model.getFilteredBugListByState(initialState).get(0);

            Bug tagAddedBug = addTagToBug(bug, newTag);
            AddTagByStateCommand addTagByStateCommand = new AddTagByStateCommand(INDEX_FIRST_BUG, newTag, initialState);

            String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_BUG_SUCCESS, tagAddedBug);

            Model expectedModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
            expectedModel.setBug(model.getFilteredBugListByState(initialState).get(0), tagAddedBug);

            assertCommandSuccess(addTagByStateCommand, model, expectedMessage, expectedModel);
        } catch (CommandException e) {
            assert false;
        }
    }

    @Test
    public void addTagToBug_validTag_success() {
        try {
            Bug bug = model.getFilteredBugListByState(initialState).get(0);

            Name bugName = bug.getName();
            State bugState = bug.getState();
            Description bugDescription = bug.getDescription();
            Priority bugPriority = bug.getPriority();
            Optional<Note> optionalNote = bug.getOptionalNote();
            Set<Tag> tagsOfBug = new HashSet<Tag>(bug.getTags());
            tagsOfBug.add(newTag);

            //copy bug details to reflect edited bug
            Bug editedBug = new Bug(bugName, bugState, bugDescription, optionalNote, tagsOfBug, bugPriority);
            assertEquals(editedBug, AddTagCommand.addTagToBug(bug, newTag));
        } catch (CommandException e) {
            assert false;
        }
    }

    @Test
    public void addTagToBug_invalidTag_commandExceptionThrown() {
        Bug validBug = model.getFilteredBugListByState(initialState).get(0);
        String expectedString = AddTagCommand.MESSAGE_NOT_ADDED;

        try {
            addTagToBug(validBug, null);
            assert false;
        } catch (CommandException e) {
            assertEquals(expectedString, e.getMessage());
        }
    }

    @Test
    public void addTagToBug_tagAlreadyExists_commandExceptionThrown() {
        Bug validBug = new BugBuilder().withTags(VALID_TAG_LOGIC).build();
        String expectedString = AddTagCommand.MESSAGE_INVALID_NEW;

        try {
            addTagToBug(validBug, new Tag(VALID_TAG_LOGIC));
            assert false;
        } catch (CommandException e) {
            assertEquals(expectedString, e.getMessage());
        }
    }

    @Test
    public void addTagToBug_invalidBug_commandExceptionThrown() {
        String expectedString = AddTagCommand.MESSAGE_NOT_ADDED;
        try {
            addTagToBug(null, newTag);
            assert false;
        } catch (CommandException e) {
            assertEquals(expectedString, e.getMessage());
        }
    }

    @Test
    public void equals() {
        AddTagByStateCommand addTagByStateCommand = new AddTagByStateCommand(INDEX_FIRST_BUG,
                new Tag(VALID_TAG_LOGIC), VALID_STATE_BUG1);
        AddTagByStateCommand addTagByStateCommandDuplicate = new AddTagByStateCommand(INDEX_FIRST_BUG,
                new Tag(VALID_TAG_LOGIC), VALID_STATE_BUG1);
        AddTagByStateCommand addTagByStateCommandDifferentIndex = new AddTagByStateCommand(INDEX_SECOND_BUG,
                new Tag(VALID_TAG_LOGIC), VALID_STATE_BUG1);
        AddTagByStateCommand addTagByStateCommandDifferentTag = new AddTagByStateCommand(INDEX_FIRST_BUG,
                new Tag(VALID_TAG_COMPONENT), VALID_STATE_BUG1);
        AddTagByStateCommand addTagByStateCommandDifferentState = new AddTagByStateCommand(INDEX_FIRST_BUG,
                new Tag(VALID_TAG_COMPONENT), VALID_STATE_BUG2);
        EditTagByStateCommand editTagByStateCommand = new EditTagByStateCommand(INDEX_FIRST_BUG,
                new Tag(VALID_TAG_LOGIC), new Tag(VALID_TAG_COMPONENT), VALID_STATE_BUG1);

        //same command
        assertTrue(addTagByStateCommand.equals(addTagByStateCommand));
        //null command
        assertFalse(addTagByStateCommand.equals(null));
        //different command types
        assertFalse(addTagByStateCommand.equals(editTagByStateCommand));
        //duplicate commands
        assertTrue(addTagByStateCommand.equals(addTagByStateCommandDuplicate));
        //different indexes
        assertFalse(addTagByStateCommand.equals(addTagByStateCommandDifferentIndex));
        //different tags
        assertFalse(addTagByStateCommand.equals(addTagByStateCommandDifferentTag));
        //different state
        assertFalse(addTagByStateCommand.equals(addTagByStateCommandDifferentState));

    }



}
