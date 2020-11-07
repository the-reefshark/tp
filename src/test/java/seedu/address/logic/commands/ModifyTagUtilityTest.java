package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPONENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOGIC;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

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
    public void addTagToBug_validTag_success() throws CommandException {
        ModifyTagUtility modifyTagUtility = new ModifyTagUtility(testBug);

        String firstTagValue = "validNewTag";
        String secondTagValue = "validNewTagSecond";
        String thirdTagValue = "validNewTagThird";

        Tag newTagFirst = new Tag("validNewTag");
        Tag newTagSecond = new Tag("validNewTagSecond");
        Tag newTagThird = new Tag("validNewTagThird");

        Set<Tag> newTags = new HashSet<>();


        // add one tag
        newTags.add(newTagFirst);
        Bug updatedBug = modifyTagUtility.addTagsToBug(newTags);
        Bug expectedBug = new BugBuilder().withTags(VALID_TAG_COMPONENT, VALID_TAG_LOGIC, firstTagValue).build();
        assertEquals(updatedBug, expectedBug);

        // add two tags
        newTags.add(newTagSecond);
        Bug updatedBugSecond = modifyTagUtility.addTagsToBug(newTags);
        Bug expectedBugSecond = new BugBuilder().withTags(VALID_TAG_COMPONENT, VALID_TAG_LOGIC, firstTagValue,
                secondTagValue).build();
        assertEquals(updatedBugSecond, expectedBugSecond);


        // add three tags
        newTags.add(newTagThird);
        Bug updatedBugThird = modifyTagUtility.addTagsToBug(newTags);
        Bug expectedBugThird = new BugBuilder().withTags(VALID_TAG_COMPONENT, VALID_TAG_LOGIC, firstTagValue,
                secondTagValue, thirdTagValue).build();
        assertEquals(updatedBugThird, expectedBugThird);

    }


    @Test
    public void addTagToBug_newTagAlreadyExists_commandExceptionThrown() {
        ModifyTagUtility modifyTagUtility = new ModifyTagUtility(testBug);
        Tag duplicateTag = new Tag(VALID_TAG_COMPONENT);
        Set<Tag> newTags = new HashSet<>();
        newTags.add(duplicateTag);
        String expectedMessage = AddTagCommand.MESSAGE_INVALID_NEW;

        assertThrows(CommandException.class, expectedMessage, () -> modifyTagUtility.addTagsToBug(newTags));
    }

}
