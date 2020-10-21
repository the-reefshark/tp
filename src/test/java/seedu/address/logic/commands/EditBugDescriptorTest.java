package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPONENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditBugDescriptor;
import seedu.address.testutil.EditBugDescriptorBuilder;

public class EditBugDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditBugDescriptor descriptorWithSameValues = new EditCommand.EditBugDescriptor(DESC_PARSER);
        assertTrue(DESC_PARSER.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_PARSER.equals(DESC_PARSER));

        // null -> returns false
        assertFalse(DESC_PARSER.equals(null));

        // different types -> returns false
        assertFalse(DESC_PARSER.equals(5));

        // different values -> returns false
        assertFalse(DESC_PARSER.equals(DESC_HOMEPAGE));

        // different name -> returns false
        EditBugDescriptor editedBug = new EditBugDescriptorBuilder(DESC_PARSER).withName(VALID_NAME_HOMEPAGE).build();
        assertFalse(DESC_PARSER.equals(editedBug));

        // different state -> returns false
        editedBug = new EditBugDescriptorBuilder(DESC_PARSER).withState(VALID_STATE_HOMEPAGE).build();
        assertFalse(DESC_PARSER.equals(editedBug));

        // different description -> returns false
        editedBug = new EditBugDescriptorBuilder(DESC_PARSER).withDescription(VALID_DESCRIPTION_HOMEPAGE).build();
        assertFalse(DESC_PARSER.equals(editedBug));

        // different tags -> returns false
        editedBug = new EditBugDescriptorBuilder(DESC_PARSER).withTags(VALID_TAG_COMPONENT).build();
        assertFalse(DESC_PARSER.equals(editedBug));

        // different priority -> returns false
        editedBug = new EditBugDescriptorBuilder(DESC_PARSER).withPriority(VALID_PRIORITY_HOMEPAGE).build();
        assertFalse(DESC_PARSER.equals(editedBug));
    }
}
