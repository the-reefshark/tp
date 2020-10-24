package seedu.address.model.bug;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void isValidNote() {
        // null note
        assertThrows(NullPointerException.class, () -> Note.isValidNote(null));

        // invalid note
        assertFalse(Note.isValidNote("")); // empty string
        assertFalse(Note.isValidNote(" ")); // spaces only

        // valid name
        assertTrue(Note.isValidNote("this is a note")); // alphabets only
        assertTrue(Note.isValidNote("12345")); // numbers only
        assertTrue(Note.isValidNote("note the 2nd")); // alphanumeric characters
        assertTrue(Note.isValidNote("This Is Also A Note")); // with capital letters
        assertTrue(Note.isValidNote("This is an incredibly detailed and long note")); // long note
    }
}

