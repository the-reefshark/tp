package seedu.address.model.bug;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidNote(null));

        // invalid name
        assertFalse(Name.isValidNote("")); // empty string
        assertFalse(Name.isValidNote(" ")); // spaces only
        assertFalse(Name.isValidNote("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidNote("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidNote("peter jack")); // alphabets only
        assertTrue(Name.isValidNote("12345")); // numbers only
        assertTrue(Name.isValidNote("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidNote("Capital Tan")); // with capital letters
        assertTrue(Name.isValidNote("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
