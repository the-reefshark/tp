package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BugBuilder;

public class AddTagCommandTest {

    private Model defaultModel = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());

    @Test
    public void execute_validAddTagInput_success() {
        Index index = INDEX_FIRST_BUG;
        String newTag = VALID_TAG_COMPONENT;
        String newTagSecond = VALID_TAG_LOGIC;
        String expectedMessage = AddTagCommand.MESSAGE_ADD_BUG_SUCCESS;

        // One tag supplied
        assertExecuteSuccess(index, expectedMessage, newTag);

        // Two tags supplied
        assertExecuteSuccess(index, expectedMessage, newTag, newTagSecond);

        // Multiple repeated tags will only be added once
        assertExecuteSuccess(index, expectedMessage, newTag, newTag);
    }

    @Test
    public void execute_invalidAddTagInputInvalidIndex_failure() {
        Index index = Index.fromOneBased(defaultModel.getFilteredBugList().size() + 1);
        String existingTag = VALID_TAG_COMPONENT;
        String newTag = VALID_TAG_LOGIC;
        String expectedMessage = Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX;
        assertExecuteFailure(index, existingTag, expectedMessage, newTag);
    }

    @Test
    public void execute_invalidAddTagInputInvalidNewTags_failure() {
        Index index = INDEX_FIRST_BUG;
        String existingTag = VALID_TAG_COMPONENT;
        String newTag = VALID_TAG_LOGIC;
        String newTagSecond = VALID_TAG_COMPONENT;
        String expectedMessage = AddTagCommand.MESSAGE_INVALID_NEW;
        assertExecuteFailure(index, existingTag, expectedMessage, newTag, newTagSecond);
    }

    @Test
    public void equals() {
        Set<Tag> tagsToAddLogic = new HashSet<>();
        Set<Tag> tagsToAddComponent = new HashSet<>();

        tagsToAddLogic.add(new Tag(VALID_TAG_LOGIC));
        tagsToAddComponent.add(new Tag(VALID_TAG_COMPONENT));

        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_BUG, tagsToAddLogic);
        AddTagCommand addTagCommandDuplicate = new AddTagCommand(INDEX_FIRST_BUG, tagsToAddLogic);
        AddTagCommand addTagCommandDifferentIndex = new AddTagCommand(INDEX_SECOND_BUG, tagsToAddLogic);
        AddTagCommand addTagCommandDifferentTag = new AddTagCommand(INDEX_FIRST_BUG, tagsToAddComponent);
        EditTagCommand editTagCommand = new EditTagCommand(INDEX_FIRST_BUG, new Tag(VALID_TAG_LOGIC),
                new Tag(VALID_TAG_COMPONENT));

        //same command
        assertTrue(addTagCommand.equals(addTagCommand));

        //null command
        assertFalse(addTagCommand.equals(null));

        //different command types
        assertFalse(addTagCommand.equals(editTagCommand));

        //duplicate commands
        assertTrue(addTagCommand.equals(addTagCommandDuplicate));

        //different indexes
        assertFalse(addTagCommand.equals(addTagCommandDifferentIndex));

        //different tags
        assertFalse(addTagCommand.equals(addTagCommandDifferentTag));
    }

    private void assertExecuteSuccess(Index index, String expectedMessageContent, String... newTags) {

        Bug initialBug = new BugBuilder().withTags().build();
        Bug tagEditedBug = new BugBuilder().withTags(newTags).build();
        Set<Tag> tagsToAdd = getTagSet(newTags);

        //Set the first bug of the initial model to be the initial bug
        Model initialModel = new ModelManager(new KanBugTracker(defaultModel.getKanBugTracker()), new UserPrefs());
        initialModel.setBug(defaultModel.getFilteredBugList().get(0), initialBug);

        //Set the first bug of the expected model to be the expected edited bug
        Model expectedModel = new ModelManager(new KanBugTracker(initialModel.getKanBugTracker()), new UserPrefs());
        expectedModel.setBug(initialModel.getFilteredBugList().get(0), tagEditedBug);

        AddTagCommand addTagCommand = new AddTagCommand(index, tagsToAdd);
        String expectedMessage = String.format(expectedMessageContent, tagEditedBug);

        assertCommandSuccess(addTagCommand, initialModel, expectedMessage, expectedModel);
    }

    private void assertExecuteFailure(Index index, String existingTag, String expectedMessageContent,
                                      String... newTags) {

        String[] finalTags = getTagArray(existingTag, newTags);
        Bug initialBug = new BugBuilder().withTags(existingTag).build();
        Bug tagEditedBug = new BugBuilder().withTags(finalTags).build();
        Set<Tag> tagsToAdd = getTagSet(newTags);

        //Set the first bug of the initial model to be the initial bug
        Model initialModel = new ModelManager(new KanBugTracker(defaultModel.getKanBugTracker()), new UserPrefs());
        initialModel.setBug(defaultModel.getFilteredBugList().get(0), initialBug);

        AddTagCommand addTagCommand = new AddTagCommand(index, tagsToAdd);
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
