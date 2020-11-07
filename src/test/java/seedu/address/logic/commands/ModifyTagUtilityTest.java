package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPONENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOGIC;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.bug.Bug;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BugBuilder;

public class ModifyTagUtilityTest {

    public static final String VALID_NEW_TAG_VALUE = "ValidNewTag";
    public static final String INVALID_NEW_TAG_VALUE = VALID_TAG_LOGIC;
    public static final String VALID_OLD_TAG_VALUE = VALID_TAG_COMPONENT;
    public static final String INVALID_OLD_TAG_VALUE = "InvalidOldTag";
    private Bug testBug = new BugBuilder().withTags(VALID_TAG_COMPONENT, VALID_TAG_LOGIC).build();

    @Test
    public void updateTagInBug_validInputs_success() throws CommandException {
        Tag validOldTag = new Tag(VALID_OLD_TAG_VALUE);
        Tag validNewTag = new Tag(VALID_NEW_TAG_VALUE);
        ModifyTagUtility modifyTagUtility = new ModifyTagUtility(testBug);

        Bug updatedBug = modifyTagUtility.updateTagInBug(validOldTag, validNewTag);
        Bug expectedBug = new BugBuilder().withTags(VALID_NEW_TAG_VALUE, VALID_TAG_LOGIC).build();
        assertEquals(updatedBug, expectedBug);
    }

    @Test void updateTagInBug_invalidOldTag_failure() {
        Tag invalidOldTag = new Tag(INVALID_OLD_TAG_VALUE);
        Tag validNewTag = new Tag(VALID_NEW_TAG_VALUE);
        ModifyTagUtility modifyTagUtility = new ModifyTagUtility(testBug);
        String expectedMessage = EditTagCommand.MESSAGE_INVALID_OLD;

        assertThrows(CommandException.class, expectedMessage, () -> modifyTagUtility.updateTagInBug(invalidOldTag,
                validNewTag));
    }

    @Test void updateTagInBut_invalidNewTag_failure() {
        Tag validTagOld = new Tag(VALID_OLD_TAG_VALUE);
        Tag invalidTagNew = new Tag(INVALID_NEW_TAG_VALUE);
        ModifyTagUtility modifyTagUtility = new ModifyTagUtility(testBug);
        String expectedMessage = EditTagCommand.MESSAGE_INVALID_NEW;

        assertThrows(CommandException.class, expectedMessage, () -> modifyTagUtility.updateTagInBug(validTagOld,
                invalidTagNew));
    }

    //-----------------------------------REDO---------------------------------
    @Test
    public void addTagToBug_validTag_success() {
//        Set<Tag> tagsToAdd = new HashSet<>();
//        tagsToAdd.add(newTagLogic);
//
//        try {
//            Bug bug = model.getFilteredBugList().get(0);
//
//            Name bugName = bug.getName();
//            State bugState = bug.getState();
//            Description bugDescription = bug.getDescription();
//            Priority bugPriority = bug.getPriority();
//            Optional<Note> bugOptionalNote = bug.getOptionalNote();
//            Set<Tag> tagsOfBug = new HashSet<Tag>(bug.getTags());
//            tagsOfBug.add(newTagLogic);
//
//            //copy bug details to reflect edited bug
//            Bug editedBug = new Bug(bugName, bugState, bugDescription, bugOptionalNote, tagsOfBug, bugPriority);
//            assertEquals(editedBug, AddTagCommand.addTagsToBug(bug, tagsToAdd));
//        } catch (CommandException e) {
//            assert false;
//        }
    }

    @Test
    public void addTagToBug_invalidTag_commandExceptionThrown() {
//        Bug validBug = model.getFilteredBugList().get(0);
//        String expectedString = AddTagCommand.MESSAGE_NOT_ADDED;
//
//        try {
//            addTagsToBug(validBug, null);
//            assert false;
//        } catch (CommandException e) {
//            assertEquals(expectedString, e.getMessage());
//        }
    }

    @Test
    public void addTagToBug_tagAlreadyExists_commandExceptionThrown() {
//        Bug validBug = new BugBuilder().withTags(VALID_TAG_LOGIC).build();
//        String expectedString = AddTagCommand.MESSAGE_INVALID_NEW;
//        Set<Tag> tagsToAdd = new HashSet<>();
//        tagsToAdd.add(newTagLogic);
//
//        try {
//            addTagsToBug(validBug, tagsToAdd);
//            assert false;
//        } catch (CommandException e) {
//            assertEquals(expectedString, e.getMessage());
//        }
    }

    @Test
    public void addTagToBug_invalidBug_commandExceptionThrown() {
//        String expectedString = AddTagCommand.MESSAGE_NOT_ADDED;
//        Set<Tag> tagsToAdd = new HashSet<>();
//        tagsToAdd.add(newTagLogic);
//
//        try {
//            addTagsToBug(null, tagsToAdd);
//            assert false;
//        } catch (CommandException e) {
//            assertEquals(expectedString, e.getMessage());
//        }
    }
}
