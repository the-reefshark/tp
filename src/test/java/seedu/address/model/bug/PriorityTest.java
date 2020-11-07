package seedu.address.model.bug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class PriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        String invalidPriority = "";
        assertThrows(IllegalArgumentException.class, () -> new Priority(invalidPriority));
    }

    @Test
    public void isValidPriority() {
        // null state
        assertThrows(NullPointerException.class, () -> Priority.isValidPriority(null));

        // blank state
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only

        // incomplete word
        assertFalse(Priority.isValidPriority("hig"));
        assertFalse(Priority.isValidPriority("lo"));

        // invalid priority
        assertFalse(Priority.isValidPriority("very high")); // one valid word
        assertFalse(Priority.isValidPriority("low high")); // multiple valid words
        assertFalse(Priority.isValidPriority("medium medium")); // multiple valid words
        assertFalse(Priority.isValidPriority("highlow")); // multiple valid words concatenated
        assertFalse(Priority.isValidPriority("fillertext")); // only filler text
        assertFalse(Priority.isValidPriority("mediumfiller")); // trailing filler text
        assertFalse(Priority.isValidPriority("fillerhigh")); // leading filler text
        assertFalse(Priority.isValidPriority("fillerlowtext")); // sandwiched valid word
        assertFalse(Priority.isValidPriority("  medium")); // leading space
        assertFalse(Priority.isValidPriority("medium  ")); // trailing space
        assertFalse(Priority.isValidPriority("  medium  ")); // leading and trailing space

        // valid priority
        assertTrue(Priority.isValidPriority("low"));
        assertTrue(Priority.isValidPriority("medium"));
        assertTrue(Priority.isValidPriority("high"));
        assertTrue(Priority.isValidPriority("Low")); // uppercase is allowed
        assertTrue(Priority.isValidPriority("HIGH")); // uppercase is allowed
        assertTrue(Priority.isValidPriority("meDiUm")); // uppercase is allowed
    }

    @Test
    public void isEqualPriority() {
        Priority p1 = new Priority("low");
        Priority p2 = new Priority("low");
        Priority p3 = new Priority("medium");
        Priority p4 = new Priority("meDIuM");
        Object p5 = new Priority("medium");

        assertEquals(p1, p1); // same object
        assertEquals(p1, p2); // same value
        assertEquals(p3, p4); // same value
        assertEquals(p3, p5); // same value and runtime type but different compile time types
    }

    @Test
    public void isNotEqualPriority() {
        Priority p1 = new Priority("low");
        Priority p2 = new Priority("medium");
        Priority p3 = new Priority("meDIuM");
        Priority p4 = new Priority("high");
        Priority p5 = new Priority();

        assertNotEquals(p1, p2); // different values
        assertNotEquals(p3, p4); // different values
        assertNotEquals(p3, null); // compare to null
        assertNotEquals(p1, "low"); // different types
        assertNotEquals(p1, p5);
    }

    @Test
    public void isEqualHashCodePriority() {
        Priority p1 = new Priority("low");
        Priority p2 = new Priority("low");
        Priority p3 = new Priority("LOw");

        assertEquals(p1.hashCode(), p2.hashCode());
        assertEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    public void isNull() {
        assertFalse(new Priority("low").isNotIndicated());
        assertFalse(new Priority("medium").isNotIndicated());
        assertFalse(new Priority("high").isNotIndicated());

        assertTrue(new Priority().isNotIndicated());
    }
}
