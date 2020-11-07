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

public class EditBugTagsDescriptorTest {

    public static final String VALID_NEW_TAG_VALUE = "ValidNewTag";
    public static final String INVALID_NEW_TAG_VALUE = VALID_TAG_LOGIC;
    public static final String VALID_OLD_TAG_VALUE = VALID_TAG_COMPONENT;
    public static final String INVALID_OLD_TAG_VALUE = "InvalidOldTag";
    private Bug testBug = new BugBuilder().withTags(VALID_TAG_COMPONENT, VALID_TAG_LOGIC).build();

    @Test
    public void updateTagInBug_validInputs_success() throws CommandException {
        Tag validOldTag = new Tag(VALID_OLD_TAG_VALUE);
        Tag validNewTag = new Tag(VALID_NEW_TAG_VALUE);
        EditTagCommand.EditBugTagsDescriptor editBugTagsDescriptor = new EditTagCommand.EditBugTagsDescriptor(testBug,
                validOldTag, validNewTag);

        Bug updatedBug = editBugTagsDescriptor.updateTagInBug();
        Bug expectedBug = new BugBuilder().withTags(VALID_NEW_TAG_VALUE, VALID_TAG_LOGIC).build();
        assertEquals(updatedBug, expectedBug);
    }

    @Test void updateTagInBut_invalidOldTag_failure() {
        Tag invalidOldTag = new Tag(INVALID_OLD_TAG_VALUE);
        Tag validNewTag = new Tag(VALID_NEW_TAG_VALUE);
        EditTagCommand.EditBugTagsDescriptor editBugTagsDescriptor = new EditTagCommand.EditBugTagsDescriptor(testBug,
                invalidOldTag, validNewTag);
        String expectedMessage = EditTagCommand.MESSAGE_INVALID_OLD;

        assertThrows(CommandException.class, expectedMessage, editBugTagsDescriptor::updateTagInBug);
    }

    @Test void updateTagInBut_invalidNewTag_failure() {
        Tag validTagOld = new Tag(VALID_OLD_TAG_VALUE);
        Tag invalidTagNew = new Tag(INVALID_NEW_TAG_VALUE);
        EditTagCommand.EditBugTagsDescriptor editBugTagsDescriptor = new EditTagCommand.EditBugTagsDescriptor(testBug,
                validTagOld, invalidTagNew);
        String expectedMessage = EditTagCommand.MESSAGE_INVALID_NEW;

        assertThrows(CommandException.class, expectedMessage, editBugTagsDescriptor::updateTagInBug);
    }
}
