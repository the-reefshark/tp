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

public class EditTagCommandTest {

    private Model defaultModel = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());


    @Test
    public void execute_validEditTagInput_success() {
        Index index = INDEX_FIRST_BUG;
        String oldTagInBug = VALID_TAG_COMPONENT;
        String newTagInBug = VALID_TAG_LOGIC;
        String commandOldTag = VALID_TAG_COMPONENT;
        String commandNewTag = VALID_TAG_LOGIC;
        String expectedMessageContent = EditTagCommand.MESSAGE_EDIT_BUG_SUCCESS;


        assertExecuteSuccess(index, newTagInBug, commandOldTag, commandNewTag, expectedMessageContent,
                 oldTagInBug);
    }

    @Test
    public void execute_invalidEditTagInputTagNotInBug_throwCommandException() {
        Index index = INDEX_FIRST_BUG;
        String oldTagInBug = VALID_TAG_COMPONENT;
        String newTagInBug = VALID_TAG_LOGIC;
        String commandOldTag = "InvalidTag";
        String commandNewTag = VALID_TAG_LOGIC;
        String expectedMessageContent = EditTagCommand.MESSAGE_INVALID_OLD;

        assertExecuteFailure(index, newTagInBug, commandOldTag, commandNewTag, expectedMessageContent,
                oldTagInBug);
    }

    @Test
    public void execute_invalidEditTagInputNewTagAlreadyInBug_throwCommandException() {
        Index index = INDEX_FIRST_BUG;
        String oldTagInBug = VALID_TAG_COMPONENT;
        String oldTagClashWithNewTag = VALID_TAG_LOGIC;
        String newTagInBug = VALID_TAG_LOGIC;
        String commandOldTag = VALID_TAG_COMPONENT;
        String commandNewTag = VALID_TAG_LOGIC;
        String expectedMessageContent = EditTagCommand.MESSAGE_INVALID_NEW;

        assertExecuteFailure(index, newTagInBug, commandOldTag, commandNewTag, expectedMessageContent,
                 oldTagInBug, oldTagClashWithNewTag);
    }

    @Test
    public void execute_invalidBugIndexUnfilteredList_throwCommandException() {
        Index outOfBoundIndexHigherThanValid = Index.fromOneBased(defaultModel.getFilteredBugList().size() + 1);
        String oldTagInBug = VALID_TAG_COMPONENT;
        String newTagInBug = VALID_TAG_LOGIC;
        String commandOldTag = VALID_TAG_COMPONENT;
        String commandNewTag = VALID_TAG_LOGIC;
        String expectedMessageContent = Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX;

        assertExecuteFailure(outOfBoundIndexHigherThanValid, newTagInBug, commandOldTag, commandNewTag,
                expectedMessageContent, oldTagInBug);
    }

    @Test
    public void equals() {
        Set<Tag> tagsToAddLogic = new HashSet<>();
        tagsToAddLogic.add(new Tag(VALID_TAG_COMPONENT));

        EditTagCommand editTagCommand = new EditTagCommand(INDEX_FIRST_BUG, new Tag(VALID_TAG_LOGIC),
                new Tag(VALID_TAG_COMPONENT));
        EditTagCommand editTagCommandDuplicate = new EditTagCommand(INDEX_FIRST_BUG, new Tag(VALID_TAG_LOGIC),
                new Tag(VALID_TAG_COMPONENT));
        EditTagCommand editTagCommandDifferentIndex = new EditTagCommand(INDEX_SECOND_BUG, new Tag(VALID_TAG_LOGIC),
                new Tag(VALID_TAG_COMPONENT));
        EditTagCommand editTagCommandDifferentTags = new EditTagCommand(INDEX_FIRST_BUG, new Tag(VALID_TAG_COMPONENT),
                new Tag(VALID_TAG_LOGIC));
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_BUG, tagsToAddLogic);

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

    private void assertExecuteSuccess(Index index, String newTagName, String commandOldTag, String commandNewTag,
                                        String expectedMessageContent, String... oldTags) {

        Bug initialBug = new BugBuilder().withTags(oldTags).build();
        Bug tagEditedBug = new BugBuilder().withTags(newTagName).build();
        Tag oldTag = new Tag(commandOldTag);
        Tag newTag = new Tag(commandNewTag);

        //Set the first bug of the initial model to be the initial bug
        Model initialModel = new ModelManager(new KanBugTracker(defaultModel.getKanBugTracker()), new UserPrefs());
        initialModel.setBug(defaultModel.getFilteredBugList().get(0), initialBug);

        //Set the first bug of the expected model to be the expected edited bug
        Model expectedModel = new ModelManager(new KanBugTracker(initialModel.getKanBugTracker()), new UserPrefs());
        expectedModel.setBug(initialModel.getFilteredBugList().get(0), tagEditedBug);

        EditTagCommand editTagCommand = new EditTagCommand(index, oldTag, newTag);
        String expectedMessage = String.format(expectedMessageContent, tagEditedBug);

        assertCommandSuccess(editTagCommand, initialModel, expectedMessage, expectedModel);
    }

    private void assertExecuteFailure(Index index, String newTagName, String commandOldTag, String commandNewTag,
                                      String expectedMessageContent, String... oldTags) {

        Bug initialBug = new BugBuilder().withTags(oldTags).build();
        Bug tagEditedBug = new BugBuilder().withTags(newTagName).build();
        Tag oldTag = new Tag(commandOldTag);
        Tag newTag = new Tag(commandNewTag);

        //Set the first bug of the initial model to be the initial bug
        Model initialModel = new ModelManager(new KanBugTracker(defaultModel.getKanBugTracker()), new UserPrefs());
        initialModel.setBug(defaultModel.getFilteredBugList().get(0), initialBug);

        EditTagCommand editTagCommand = new EditTagCommand(index, oldTag, newTag);
        String expectedMessage = String.format(expectedMessageContent, tagEditedBug);

        assertCommandFailure(editTagCommand, initialModel, expectedMessage);

    }
}
