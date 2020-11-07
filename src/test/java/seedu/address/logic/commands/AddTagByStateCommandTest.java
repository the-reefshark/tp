package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_BACKLOG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_TODO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_VALUE_TODO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPONENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOGIC;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBugs.getTypicalKanBugTracker;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUG;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUG;

import java.util.ArrayList;
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

public class AddTagByStateCommandTest {

    private Model defaultModel = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());

    // tests the case where the user inputs the wrong column as well since it would be considered out of bounds.
    @Test
    public void execute_validAddTagByStateInput_success() {
        Index index = INDEX_FIRST_BUG;
        String newTag = VALID_TAG_COMPONENT;
        String newTagSecond = VALID_TAG_LOGIC;
        String expectedMessage = AddTagCommand.MESSAGE_ADD_BUG_SUCCESS;
        String todoState = VALID_STATE_VALUE_TODO;

        // One tag supplied
        assertExecuteSuccess(index, expectedMessage, todoState, newTag);

        // Two tags supplied
        assertExecuteSuccess(index, expectedMessage, todoState, newTag, newTagSecond);

        // Multiple repeated tags will only be added once
        assertExecuteSuccess(index, expectedMessage, todoState, newTag, newTag);

    }

    @Test
    public void execute_invalidBugIndexFilteredList_failure() {
        Index index = Index.fromOneBased(defaultModel.getFilteredBugList().size() + 1);
        String existingTag = VALID_TAG_COMPONENT;
        String newTag = VALID_TAG_LOGIC;
        String expectedMessage = Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX;
        String todoState = VALID_STATE_VALUE_TODO;

        assertExecuteFailure(index, existingTag, expectedMessage, todoState, newTag);
    }

    @Test
    public void execute_invalidEditTagByStateInputNewTagAlreadyInBug_failure() {
        Index index = INDEX_FIRST_BUG;
        String existingTag = VALID_TAG_COMPONENT;
        String newTag = VALID_TAG_LOGIC;
        String newTagSecond = VALID_TAG_COMPONENT;
        String expectedMessage = AddTagCommand.MESSAGE_INVALID_NEW;
        String todoState = VALID_STATE_VALUE_TODO;
        assertExecuteFailure(index, existingTag, expectedMessage, todoState, newTag, newTagSecond);
    }

    @Test
    public void equals() {
        Set<Tag> tagsToAddLogic = new HashSet<>();
        Set<Tag> tagsToAddComponent = new HashSet<>();

        tagsToAddLogic.add(new Tag(VALID_TAG_LOGIC));
        tagsToAddComponent.add(new Tag(VALID_TAG_COMPONENT));

        AddTagByStateCommand addTagByStateCommand = new AddTagByStateCommand(INDEX_FIRST_BUG,
                tagsToAddLogic, VALID_STATE_TODO);
        AddTagByStateCommand addTagByStateCommandDuplicate = new AddTagByStateCommand(INDEX_FIRST_BUG,
                tagsToAddLogic, VALID_STATE_TODO);
        AddTagByStateCommand addTagByStateCommandDifferentIndex = new AddTagByStateCommand(INDEX_SECOND_BUG,
                tagsToAddLogic, VALID_STATE_TODO);
        AddTagByStateCommand addTagByStateCommandDifferentTag = new AddTagByStateCommand(INDEX_FIRST_BUG,
                tagsToAddComponent, VALID_STATE_TODO);
        AddTagByStateCommand addTagByStateCommandDifferentState = new AddTagByStateCommand(INDEX_FIRST_BUG,
                tagsToAddComponent, VALID_STATE_BACKLOG);
        EditTagByStateCommand editTagByStateCommand = new EditTagByStateCommand(INDEX_FIRST_BUG,
                new Tag(VALID_TAG_LOGIC), new Tag(VALID_TAG_COMPONENT), VALID_STATE_TODO);

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

    private void assertExecuteSuccess(Index index, String expectedMessageContent, String stateValue,
                                      String... newTags) {

        Bug initialBug = new BugBuilder().withTags().withState(stateValue).build();
        Bug tagEditedBug = new BugBuilder().withTags(newTags).withState(stateValue).build();
        Set<Tag> tagsToAdd = getTagSet(newTags);
        State state = new State(stateValue);

        //Set the first bug of the initial model to be the initial bug
        Model initialModel = new ModelManager(new KanBugTracker(defaultModel.getKanBugTracker()), new UserPrefs());
        initialModel.setBug(defaultModel.getFilteredBugListByState(state).get(0), initialBug);

        //Set the first bug of the expected model to be the expected edited bug
        Model expectedModel = new ModelManager(new KanBugTracker(initialModel.getKanBugTracker()), new UserPrefs());
        expectedModel.setBug(initialModel.getFilteredBugListByState(state).get(0), tagEditedBug);

        AddTagByStateCommand addTagCommand = new AddTagByStateCommand(index, tagsToAdd, state);
        String expectedMessage = String.format(expectedMessageContent, tagEditedBug);

        assertCommandSuccess(addTagCommand, initialModel, expectedMessage, expectedModel);
    }

    private void assertExecuteFailure(Index index, String existingTag, String expectedMessageContent, String stateValue,
                                      String... newTags) {

        String[] finalTags = getTagArray(existingTag, newTags);
        Bug initialBug = new BugBuilder().withTags(existingTag).withState(stateValue).build();
        Bug tagEditedBug = new BugBuilder().withTags(finalTags).withState(stateValue).build();
        Set<Tag> tagsToAdd = getTagSet(newTags);
        State state = new State(stateValue);

        //Set the first bug of the initial model to be the initial bug
        Model initialModel = new ModelManager(new KanBugTracker(defaultModel.getKanBugTracker()), new UserPrefs());
        initialModel.setBug(defaultModel.getFilteredBugListByState(state).get(0), initialBug);

        AddTagByStateCommand addTagCommand = new AddTagByStateCommand(index, tagsToAdd, state);
        String expectedMessage = String.format(expectedMessageContent, tagEditedBug);

        assertCommandFailure(addTagCommand, initialModel, expectedMessage);

    }

    private Set<Tag> getTagSet(String... newTags) {
        Set<Tag> tagsToAdd = new HashSet<>();

        for (String tag : newTags) {
            tagsToAdd.add(new Tag(tag));
        }
        return tagsToAdd;
    }

    private String[] getTagArray(String oldTag, String... newTags) {
        ArrayList<String> finalTags = new ArrayList<>();
        for (String tag : newTags) {
            finalTags.add(tag);
        }
        finalTags.add(oldTag);
        return finalTags.toArray(new String[] {});
    }
}
