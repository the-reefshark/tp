package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_BACKLOG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_TODO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_VALUE_BACKLOG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_VALUE_DONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_VALUE_ONGOING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_VALUE_TODO;
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
import seedu.address.model.KanBugTracker;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BugBuilder;

public class EditTagByStateCommandTest {

    private Model defaultModel = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());

    @Test
    public void execute_validEditTagByStateInput_success() {
        Index index = INDEX_FIRST_BUG;
        String oldTagInBug = VALID_TAG_COMPONENT;
        String newTagInBug = VALID_TAG_LOGIC;
        String commandOldTag = VALID_TAG_COMPONENT;
        String commandNewTag = VALID_TAG_LOGIC;
        String expectedMessageContent = EditTagCommand.MESSAGE_EDIT_BUG_SUCCESS;

        assertExecuteSuccess(index, newTagInBug, commandOldTag, commandNewTag, expectedMessageContent,
                VALID_STATE_VALUE_BACKLOG, oldTagInBug);
        assertExecuteSuccess(index, newTagInBug, commandOldTag, commandNewTag, expectedMessageContent,
                VALID_STATE_VALUE_TODO, oldTagInBug);
        assertExecuteSuccess(index, newTagInBug, commandOldTag, commandNewTag, expectedMessageContent,
                VALID_STATE_VALUE_ONGOING, oldTagInBug);
        assertExecuteSuccess(index, newTagInBug, commandOldTag, commandNewTag, expectedMessageContent,
                VALID_STATE_VALUE_DONE, oldTagInBug);
    }

    @Test
    public void execute_invalidBugIndexFilteredList_throwCommandException() {
        Index outOfBoundIndexHigherThanValid = Index.fromOneBased(defaultModel.getFilteredBugList().size() + 1);
        String oldTagInBug = VALID_TAG_COMPONENT;
        String newTagInBug = VALID_TAG_LOGIC;
        String commandOldTag = VALID_TAG_COMPONENT;
        String commandNewTag = VALID_TAG_LOGIC;
        String expectedMessageContent = Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX;

        assertExecuteFailure(outOfBoundIndexHigherThanValid, newTagInBug, commandOldTag, commandNewTag,
                expectedMessageContent, VALID_STATE_VALUE_BACKLOG, oldTagInBug);
    }

    @Test
    public void execute_invalidEditTagByStateInputTagNotInBug_throwCommandException() {
        Index index = INDEX_FIRST_BUG;
        String oldTagInBug = VALID_TAG_COMPONENT;
        String newTagInBug = VALID_TAG_LOGIC;
        String commandOldTag = "InvalidTag";
        String commandNewTag = VALID_TAG_LOGIC;
        String expectedMessageContent = EditTagCommand.MESSAGE_INVALID_OLD;

        assertExecuteFailure(index, newTagInBug, commandOldTag, commandNewTag, expectedMessageContent,
                VALID_STATE_VALUE_TODO, oldTagInBug);
    }

    @Test
    public void execute_invalidEditTagByStateInputNewTagAlreadyInBug_throwCommandException() {
        Index index = INDEX_FIRST_BUG;
        String oldTagInBug = VALID_TAG_COMPONENT;
        String oldTagClashWithNewTag = VALID_TAG_LOGIC;
        String newTagInBug = VALID_TAG_LOGIC;
        String commandOldTag = VALID_TAG_COMPONENT;
        String commandNewTag = VALID_TAG_LOGIC;
        String expectedMessageContent = EditTagCommand.MESSAGE_INVALID_NEW;

        assertExecuteFailure(index, newTagInBug, commandOldTag, commandNewTag, expectedMessageContent,
                VALID_STATE_VALUE_DONE, oldTagInBug, oldTagClashWithNewTag);
    }

    @Test
    public void equals() {
        Set<Tag> tagsToAddLogic = new HashSet<>();
        tagsToAddLogic.add(new Tag(VALID_TAG_COMPONENT));

        EditTagByStateCommand editTagByStateCommand = new EditTagByStateCommand(INDEX_FIRST_BUG,
                new Tag(VALID_TAG_LOGIC), new Tag(VALID_TAG_COMPONENT), VALID_STATE_TODO);
        EditTagByStateCommand editTagByStateCommandDuplicate = new EditTagByStateCommand(INDEX_FIRST_BUG,
                new Tag(VALID_TAG_LOGIC), new Tag(VALID_TAG_COMPONENT), VALID_STATE_TODO);
        EditTagByStateCommand editTagByStateCommandDifferentIndex = new EditTagByStateCommand(INDEX_SECOND_BUG,
                new Tag(VALID_TAG_LOGIC), new Tag(VALID_TAG_COMPONENT), VALID_STATE_TODO);
        EditTagByStateCommand editTagByStateCommandDifferentTags = new EditTagByStateCommand(INDEX_FIRST_BUG,
                new Tag(VALID_TAG_COMPONENT), new Tag(VALID_TAG_LOGIC), VALID_STATE_TODO);
        EditTagByStateCommand editTagByStateCommandDifferentState = new EditTagByStateCommand(INDEX_FIRST_BUG,
                new Tag(VALID_TAG_COMPONENT), new Tag(VALID_TAG_LOGIC), VALID_STATE_BACKLOG);
        AddTagByStateCommand addTagByStateCommand = new AddTagByStateCommand(INDEX_FIRST_BUG,
                tagsToAddLogic, VALID_STATE_TODO);

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

    private void assertExecuteSuccess(Index index, String newTagName, String commandOldTag, String commandNewTag,
                                      String expectedMessageContent, String stateValue, String... oldTags) {

        Bug initialBug = new BugBuilder().withTags(oldTags).withState(stateValue).build();
        Bug tagEditedBug = new BugBuilder().withTags(newTagName).withState(stateValue).build();
        Tag oldTag = new Tag(commandOldTag);
        Tag newTag = new Tag(commandNewTag);
        State state = new State(stateValue);

        //Set the first bug of the initial model to be the initial bug
        Model initialModel = new ModelManager(new KanBugTracker(defaultModel.getKanBugTracker()), new UserPrefs());
        initialModel.setBug(defaultModel.getFilteredBugListByState(state).get(0), initialBug);

        //Set the first bug of the expected model to be the expected edited bug
        Model expectedModel = new ModelManager(new KanBugTracker(initialModel.getKanBugTracker()), new UserPrefs());
        expectedModel.setBug(initialModel.getFilteredBugListByState(state).get(0), tagEditedBug);

        EditTagByStateCommand editTagCommand = new EditTagByStateCommand(index, oldTag, newTag, state);
        String expectedMessage = String.format(expectedMessageContent, tagEditedBug);

        assertCommandSuccess(editTagCommand, initialModel, expectedMessage, expectedModel);
    }

    private void assertExecuteFailure(Index index, String newTagName, String commandOldTag, String commandNewTag,
                                      String expectedMessageContent, String stateValue, String... oldTags) {

        Bug initialBug = new BugBuilder().withTags(oldTags).withState(stateValue).build();
        Bug tagEditedBug = new BugBuilder().withTags(newTagName).withState(stateValue).build();
        Tag oldTag = new Tag(commandOldTag);
        Tag newTag = new Tag(commandNewTag);
        State state = new State(stateValue);

        //Set the first bug of the initial model to be the initial bug
        Model initialModel = new ModelManager(new KanBugTracker(defaultModel.getKanBugTracker()), new UserPrefs());
        initialModel.setBug(defaultModel.getFilteredBugListByState(state).get(0), initialBug);

        EditTagByStateCommand editTagCommand = new EditTagByStateCommand(index, oldTag, newTag, state);
        String expectedMessage = String.format(expectedMessageContent, tagEditedBug);

        assertCommandFailure(editTagCommand, initialModel, expectedMessage);

    }
}
