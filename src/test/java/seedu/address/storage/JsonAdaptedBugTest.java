package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedBug.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBugs.BUGTWO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.State;

public class JsonAdaptedBugTest {
    private static final String INVALID_NAME = "C@mmand";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_STATE = "Invalid state";
    private static final String INVALID_TAG = "#Command";

    private static final String VALID_NAME = BUGTWO.getName().toString();
    private static final String VALID_STATE = BUGTWO.getState().toString();
    private static final String VALID_DESCRIPTION = BUGTWO.getDescription().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BUGTWO.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validBugDetails_returnsBug() throws Exception {
        JsonAdaptedBug bug = new JsonAdaptedBug(BUGTWO);
        assertEquals(BUGTWO, bug.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedBug bug =
                new JsonAdaptedBug(INVALID_NAME, VALID_STATE, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, bug::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedBug bug = new JsonAdaptedBug(null, VALID_STATE, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, bug::toModelType);
    }


    @Test
    public void toModelType_invalidState_throwsIllegalValueException() {
        JsonAdaptedBug bug =
                new JsonAdaptedBug(VALID_NAME, INVALID_STATE, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = State.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, bug::toModelType);
    }

    @Test
    public void toModelType_nullState_throwsIllegalValueException() {
        JsonAdaptedBug bug = new JsonAdaptedBug(VALID_NAME, null, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, State.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, bug::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedBug bug =
                new JsonAdaptedBug(VALID_NAME, VALID_STATE, INVALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, bug::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedBug bug = new JsonAdaptedBug(VALID_NAME, VALID_STATE, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, bug::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedBug bug =
                new JsonAdaptedBug(VALID_NAME, VALID_STATE, VALID_DESCRIPTION, invalidTags);
        assertThrows(IllegalValueException.class, bug::toModelType);
    }

}
