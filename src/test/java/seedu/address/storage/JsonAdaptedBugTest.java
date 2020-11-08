package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedBug.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBugs.BUG_TEN;
import static seedu.address.testutil.TypicalBugs.BUG_TWO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.Note;
import seedu.address.model.bug.Priority;
import seedu.address.model.bug.State;

public class JsonAdaptedBugTest {
    private static final String INVALID_NAME = "C@mmand";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_STATE = "Invalid state";
    private static final String INVALID_TAG = "#Command";
    private static final String INVALID_PRIORITY = "loww";

    private static final String VALID_NAME = BUG_TWO.getName().toString();
    private static final String VALID_STATE = BUG_TWO.getState().toString();
    private static final String VALID_DESCRIPTION = BUG_TWO.getDescription().toString();
    private static final String VALID_NOTE = BUG_TWO.getOptionalNote().get().toString();
    private static final String VALID_PRIORITY = BUG_TEN.getPriority().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BUG_TWO.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validBugDetails_returnsBug() throws Exception {
        JsonAdaptedBug bug = new JsonAdaptedBug(BUG_TWO);
        assertEquals(BUG_TWO, bug.toModelType());

        bug = new JsonAdaptedBug(BUG_TEN);
        assertEquals(BUG_TEN, bug.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedBug bug =
                new JsonAdaptedBug(INVALID_NAME, VALID_STATE, VALID_DESCRIPTION, VALID_NOTE, VALID_PRIORITY,
                        VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, bug::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedBug bug = new JsonAdaptedBug(null, VALID_STATE, VALID_DESCRIPTION, VALID_NOTE, VALID_PRIORITY,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, bug::toModelType);
    }


    @Test
    public void toModelType_invalidState_throwsIllegalValueException() {
        JsonAdaptedBug bug =
                new JsonAdaptedBug(VALID_NAME, INVALID_STATE, VALID_DESCRIPTION, VALID_NOTE, VALID_PRIORITY,
                        VALID_TAGS);
        String expectedMessage = State.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, bug::toModelType);
    }

    @Test
    public void toModelType_nullState_throwsIllegalValueException() {
        JsonAdaptedBug bug = new JsonAdaptedBug(VALID_NAME, null, VALID_DESCRIPTION, VALID_NOTE, VALID_PRIORITY,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, State.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, bug::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedBug bug =
                new JsonAdaptedBug(VALID_NAME, VALID_STATE, INVALID_DESCRIPTION, VALID_NOTE, VALID_PRIORITY,
                        VALID_TAGS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, bug::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedBug bug = new JsonAdaptedBug(VALID_NAME, VALID_STATE, null, VALID_NOTE, VALID_PRIORITY,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, bug::toModelType);
    }

    @Test
    public void toModelType_invalidPriority_throwsIllegalValueException() {
        JsonAdaptedBug bug =
                new JsonAdaptedBug(VALID_NAME, VALID_STATE, VALID_DESCRIPTION, VALID_NOTE, INVALID_PRIORITY,
                        VALID_TAGS);
        String expectedMessage = Priority.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, bug::toModelType);
    }

    @Test
    public void toModelType_nullPriority_throwsIllegalValueException() {
        JsonAdaptedBug bug = new JsonAdaptedBug(VALID_NAME, VALID_STATE, VALID_DESCRIPTION, VALID_NOTE, null,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, bug::toModelType);
    }

    @Test
    public void toModelType_nullNote_throwsIllegalValueException() {
        JsonAdaptedBug bug = new JsonAdaptedBug(VALID_NAME, VALID_STATE, VALID_DESCRIPTION, null, VALID_PRIORITY,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, bug::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedBug bug =
                new JsonAdaptedBug(VALID_NAME, VALID_STATE, VALID_DESCRIPTION, VALID_NOTE, VALID_PRIORITY,
                        invalidTags);
        assertThrows(IllegalValueException.class, bug::toModelType);
    }

}
