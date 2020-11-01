package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BugBuilder;

public class EditTagByStateCommandTest {

    private Model model = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());
    private State initialStateBacklog = VALID_STATE_BUG2;
    private State initialStateToDo = VALID_STATE_BUG1;
    private Tag newTag = new Tag(VALID_TAG_LOGIC);
    private Tag oldTag = new Tag(VALID_TAG_COMPONENT);


    //Todo refactor this to pulll out all the declarations of new and old tag

    // tests the case where the user inputs the wrong column as well since it would be considered out of bounds.
    @Test
    public void execute_invalidBugIndexFilteredListByState_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBugList().size() + 1);
        EditTagByStateCommand editTagCommand = new EditTagByStateCommand(outOfBoundIndex, oldTag, newTag,
                initialStateToDo);

        assertCommandFailure(editTagCommand, model, Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validEditTagInput_success() {
        Bug initialBug = new BugBuilder().withTags(VALID_TAG_COMPONENT).build();

        try {
            Bug tagEditedBug = EditTagByStateCommand.updateTagInBug(initialBug, oldTag, newTag);
            EditTagByStateCommand editTagCommand = new EditTagByStateCommand(INDEX_FIRST_BUG, oldTag, newTag,
                    initialStateBacklog);
            String expectedMessage = String.format(EditTagCommand.MESSAGE_EDIT_BUG_SUCCESS, tagEditedBug);

            //set initial model
            Model initialModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
            initialModel.setBug(model.getFilteredBugListByState(initialStateBacklog).get(0), initialBug);

            //set expected model
            Model expectedModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
            expectedModel.setBug(model.getFilteredBugListByState(initialStateBacklog).get(0), tagEditedBug);

            assertCommandSuccess(editTagCommand, initialModel, expectedMessage, expectedModel);
        } catch (CommandException e) {
            assert false;
        }
    }

    @Test
    public void execute_invalidEditTagInputTagNotInBug_throwCommandException() {
        Bug initialBug = model.getFilteredBugListByState(initialStateToDo).get(0);
        EditTagByStateCommand editTagCommand = new EditTagByStateCommand(INDEX_FIRST_BUG, oldTag, newTag,
                initialStateToDo);
        String expectedMessage = String.format(EditTagCommand.MESSAGE_INVALID_OLD);

        //set initial model
        Model initialModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
        initialModel.setBug(model.getFilteredBugListByState(initialStateToDo).get(0), initialBug);

        //set expected model
        Model expectedModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
        expectedModel.setBug(model.getFilteredBugListByState(initialStateToDo).get(0), initialBug);
        assertCommandFailure(editTagCommand, initialModel, expectedMessage);
    }

    @Test
    public void equals() {
        Set<Tag> tagsToAddLogic = new HashSet<>();
        tagsToAddLogic.add(new Tag(VALID_TAG_COMPONENT));

        EditTagByStateCommand editTagByStateCommand = new EditTagByStateCommand(INDEX_FIRST_BUG,
                new Tag(VALID_TAG_LOGIC), new Tag(VALID_TAG_COMPONENT), VALID_STATE_BUG1);
        EditTagByStateCommand editTagByStateCommandDuplicate = new EditTagByStateCommand(INDEX_FIRST_BUG,
                new Tag(VALID_TAG_LOGIC), new Tag(VALID_TAG_COMPONENT), VALID_STATE_BUG1);
        EditTagByStateCommand editTagByStateCommandDifferentIndex = new EditTagByStateCommand(INDEX_SECOND_BUG,
                new Tag(VALID_TAG_LOGIC), new Tag(VALID_TAG_COMPONENT), VALID_STATE_BUG1);
        EditTagByStateCommand editTagByStateCommandDifferentTags = new EditTagByStateCommand(INDEX_FIRST_BUG,
                new Tag(VALID_TAG_COMPONENT), new Tag(VALID_TAG_LOGIC), VALID_STATE_BUG1);
        EditTagByStateCommand editTagByStateCommandDifferentState = new EditTagByStateCommand(INDEX_FIRST_BUG,
                new Tag(VALID_TAG_COMPONENT), new Tag(VALID_TAG_LOGIC), VALID_STATE_BUG2);
        AddTagByStateCommand addTagByStateCommand = new AddTagByStateCommand(INDEX_FIRST_BUG,
                tagsToAddLogic, VALID_STATE_BUG1);

        //same command
        assertTrue(editTagByStateCommand.equals(editTagByStateCommand));
        //null command
        assertFalse(editTagByStateCommand.equals(null));
        //different command types
        assertFalse(editTagByStateCommand.equals(addTagByStateCommand));
        //duplicate command
        assertTrue(editTagByStateCommand.equals(editTagByStateCommandDuplicate));
        //different indexes
        assertFalse(editTagByStateCommand.equals(editTagByStateCommandDifferentIndex));
        //different tags
        assertFalse(editTagByStateCommand.equals(editTagByStateCommandDifferentTags));
        //different STATE
        assertFalse(editTagByStateCommand.equals(editTagByStateCommandDifferentState));
    }
}
