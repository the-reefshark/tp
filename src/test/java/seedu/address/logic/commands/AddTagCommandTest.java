package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import static seedu.address.logic.commands.AddTagCommand.addTagToBug;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPONENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import seedu.address.model.KanBugTracker;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.Priority;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;
import static seedu.address.testutil.TypicalBugs.getTypicalKanBugTracker;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUG;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUG;

import java.util.HashSet;
import java.util.Set;

public class AddTagCommandTest {

    private Model model = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());

    @Test
    public void execute_invalidBugIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBugList().size() + 1);
        Tag newTag = new Tag(VALID_TAG_FRIEND);
        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, newTag);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Tag newTag = new Tag(VALID_TAG_FRIEND);
        Bug bug = model.getFilteredBugList().get(0);
        Bug tagAddedBug = addTagToBug(bug, newTag);

        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_BUG, newTag);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_BUG_SUCCESS, tagAddedBug);

        Model expectedModel = new ModelManager(new KanBugTracker(model.getKanBugTracker()), new UserPrefs());
        expectedModel.setBug(model.getFilteredBugList().get(0), tagAddedBug);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void addTagToBug_validTag_success(){
        Tag newTag = new Tag(VALID_TAG_FRIEND);
        Bug bug = model.getFilteredBugList().get(0);

        Name bugName = bug.getName();
        State bugState = bug.getState();
        Description bugDescription = bug.getDescription();
        Priority bugPriority = bug.getPriority();
        Set<Tag> tagsOfBug = new HashSet<Tag>(bug.getTags());
        tagsOfBug.add(newTag);

        //copy bug details to reflect edited bug
        Bug editedBug = new Bug(bugName, bugState, bugDescription, tagsOfBug, bugPriority);

        assertEquals(editedBug, AddTagCommand.addTagToBug(bug, newTag));
    }

    @Test
    public void addTagToBug_invalidTag_illegalArgumentExceptionThrown(){
        Bug validBug = model.getFilteredBugList().get(0);
        String expectedString = AddTagCommand.MESSAGE_NOT_ADDED;

        try {
            addTagToBug(validBug, null);
            assert false;
        } catch (IllegalArgumentException e) {
            assertEquals(expectedString, e.getMessage());
        }
    }

    @Test
    public void addTagToBug_invalidBug_illegalArgumentExceptionThrown(){
        Tag validTag = new Tag(VALID_TAG_FRIEND);
        String expectedString = AddTagCommand.MESSAGE_NOT_ADDED;

        try {
            addTagToBug(null, validTag);
            assert false;
        } catch (IllegalArgumentException e) {
            assertEquals(expectedString, e.getMessage());
        }
    }

    @Test
    public void equals() {
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_BUG, new Tag(VALID_TAG_FRIEND));
        AddTagCommand addTagCommandDuplicate = new AddTagCommand(INDEX_FIRST_BUG, new Tag(VALID_TAG_FRIEND));
        AddTagCommand addTagCommandDifferentIndex = new AddTagCommand(INDEX_SECOND_BUG, new Tag(VALID_TAG_FRIEND));
        AddTagCommand addTagCommandDifferentTag = new AddTagCommand(INDEX_FIRST_BUG, new Tag(VALID_TAG_COMPONENT));
        EditTagCommand editTagCommand = new EditTagCommand(INDEX_FIRST_BUG, new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_COMPONENT));

        assertTrue(addTagCommand.equals(addTagCommand));
        assertFalse(addTagCommand.equals(null));
        assertFalse(addTagCommand.equals(editTagCommand));
        assertTrue(addTagCommand.equals(addTagCommandDuplicate));
        assertFalse(addTagCommand.equals(addTagCommandDifferentIndex));
        assertFalse(addTagCommand.equals(addTagCommandDifferentTag));

    }
}
