package seedu.address.model.bug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new State(null));
    }

    @Test
    public void constructor_invalidState_throwsIllegalArgumentException() {
        String invalidState = "";
        assertThrows(IllegalArgumentException.class, () -> new State(invalidState));
    }

    @Test
    public void isValidState() {
        // null state
        assertThrows(NullPointerException.class, () -> State.isValidState(null));

        // blank state
        assertFalse(State.isValidState("")); // empty string
        assertFalse(State.isValidState(" ")); // spaces only

        // missing parts
        assertFalse(State.isValidState("backlo")); // incomplete word
        assertFalse(State.isValidState("don")); // incomplete word
        assertFalse(State.isValidState("ongoin")); // incomplete word
        assertFalse(State.isValidState("tod")); // incomplete word

        // invalid inputs
        assertFalse(State.isValidState("backlog todo")); // multiple valid words should be invalid
        assertFalse(State.isValidState("backlog done")); // multiple valid words should be invalid
        assertFalse(State.isValidState("backlog ongoing")); // multiple valid words should be invalid
        assertFalse(State.isValidState("todo done")); // multiple valid words should be invalid
        assertFalse(State.isValidState("todo backlog")); // multiple valid words should be invalid
        assertFalse(State.isValidState("todo ongoing")); // multiple valid words should be invalid
        assertFalse(State.isValidState("done backlog")); // multiple valid words should be invalid
        assertFalse(State.isValidState("done todo")); // multiple valid words should be invalid
        assertFalse(State.isValidState("done ongoing")); // multiple valid words should be invalid
        assertFalse(State.isValidState("ongoing todo")); // multiple valid words should be invalid
        assertFalse(State.isValidState("ongoing done")); // multiple valid words should be invalid
        assertFalse(State.isValidState("ongoing backlog")); // multiple valid words should be invalid
        assertFalse(State.isValidState(" backlog")); //leading space
        assertFalse(State.isValidState("backlog ")); //trailing space
        assertFalse(State.isValidState(" backlog ")); //leading and trailing space
        assertFalse(State.isValidState("fillertextbacklog")); //leading filler text
        assertFalse(State.isValidState("backlogfillertext")); //trailing filler text
        assertFalse(State.isValidState("fillertextbacklogfillertext")); //sandwiched valid word
        assertFalse(State.isValidState("backlogtododoneongoing")); //valid words in invalid string


        // valid state
        assertTrue(State.isValidState("backlog")); // minimal valid word
        assertTrue(State.isValidState("todo")); // minimal valid word
        assertTrue(State.isValidState("ongoing")); // minimal valid word
        assertTrue(State.isValidState("done")); // minimal valid word
        assertTrue(State.isValidState("Backlog")); // valid word with upper case character
        assertTrue(State.isValidState("toDo")); // valid word with upper case character
        assertTrue(State.isValidState("onGoinG")); // valid word with upper case character
        assertTrue(State.isValidState("DonE")); // valid word with upper case character
    }

    @Test
    public void isEqualState() {
        State s1 = new State("backlog");
        State s2 = new State("backlog");
        State s3 = new State("todo");
        State s4 = new State("todo");
        State s5 = new State("ongoing");
        State s6 = new State("ongoing");
        State s7 = new State("done");
        State s8 = new State("done");
        assertEquals(s1, s1); // same object
        assertEquals(s1, s2); // same value
        assertEquals(s3, s3); // same object
        assertEquals(s3, s4); // same value
        assertEquals(s5, s5); // same object
        assertEquals(s5, s6); // same value
        assertEquals(s7, s7); // same object
        assertEquals(s7, s8); // same value
    }

    @Test
    public void isNotEqualState() {
        State s1 = new State("backlog");
        State s2 = new State("backlog");
        State s3 = new State("todo");
        State s4 = new State("todo");
        State s5 = new State("ongoing");
        State s6 = new State("ongoing");
        State s7 = new State("done");
        State s8 = new State("done");
        assertNotEquals(s1, s3); // different object
        assertNotEquals(s1, s6); // different object
        assertNotEquals(s1, s8); // different object
        assertNotEquals(s3, s8); // different object
        assertNotEquals(s3, s2); // different object
        assertNotEquals(s3, s5); // different object
        assertNotEquals(s5, s1); // different object
        assertNotEquals(s5, s4); // different object
        assertNotEquals(s5, s8); //// different object
    }

    @Test
    public void isCorrectStateValue() {
        State testState = new State("backlog"); // just to give me access to the method used below
        assertEquals(State.Value.BACKLOG, testState.getValueOfState("backlog"));
        assertEquals(State.Value.TODO, testState.getValueOfState("todo"));
        assertEquals(State.Value.ONGOING, testState.getValueOfState("ongoing"));
        assertEquals(State.Value.DONE, testState.getValueOfState("done"));
    }
}
