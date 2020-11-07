package seedu.address.model.bug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_BACKLOG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_DONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_ONGOING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_TODO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_VALUE_BACKLOG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_VALUE_DONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_VALUE_ONGOING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_VALUE_TODO;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StateTest {

    private State stateBacklogOne = VALID_STATE_BACKLOG;
    private State stateBacklogTwo = new State(VALID_STATE_VALUE_BACKLOG);
    private State stateTodoOne = VALID_STATE_TODO;
    private State stateTodoTwo = new State(VALID_STATE_VALUE_TODO);
    private State stateOngoingOne = VALID_STATE_ONGOING;
    private State stateOngoingTwo = new State(VALID_STATE_VALUE_ONGOING);
    private State stateDoneOne = VALID_STATE_DONE;
    private State stateDoneTwo = new State(VALID_STATE_VALUE_DONE);

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

        // valid stateS
        assertTrue(State.isValidState(VALID_STATE_VALUE_BACKLOG)); // minimal valid word
        assertTrue(State.isValidState(VALID_STATE_VALUE_TODO)); // minimal valid word
        assertTrue(State.isValidState(VALID_STATE_VALUE_ONGOING)); // minimal valid word
        assertTrue(State.isValidState(VALID_STATE_VALUE_DONE)); // minimal valid word
        assertTrue(State.isValidState("Backlog")); // valid word with upper case character
        assertTrue(State.isValidState("toDo")); // valid word with upper case character
        assertTrue(State.isValidState("onGoinG")); // valid word with upper case character
        assertTrue(State.isValidState("DonE")); // valid word with upper case character

        // ------------------------------------- invalid inputs --------------------------------------

        // multiple valid words should be invalid
        assertFalse(State.isValidState(VALID_STATE_VALUE_BACKLOG + " " + VALID_STATE_VALUE_TODO));
        assertFalse(State.isValidState(VALID_STATE_VALUE_BACKLOG + " " + VALID_STATE_VALUE_DONE));
        assertFalse(State.isValidState(VALID_STATE_VALUE_BACKLOG + " " + VALID_STATE_VALUE_ONGOING));
        assertFalse(State.isValidState(VALID_STATE_VALUE_BACKLOG + " " + VALID_STATE_VALUE_BACKLOG));
        assertFalse(State.isValidState(VALID_STATE_VALUE_TODO + " " + VALID_STATE_VALUE_DONE));
        assertFalse(State.isValidState(VALID_STATE_VALUE_TODO + " " + VALID_STATE_VALUE_BACKLOG));
        assertFalse(State.isValidState(VALID_STATE_VALUE_TODO + " " + VALID_STATE_VALUE_ONGOING));
        assertFalse(State.isValidState(VALID_STATE_VALUE_TODO + " " + VALID_STATE_VALUE_TODO));
        assertFalse(State.isValidState(VALID_STATE_VALUE_DONE + " " + VALID_STATE_VALUE_BACKLOG));
        assertFalse(State.isValidState(VALID_STATE_VALUE_DONE + " " + VALID_STATE_VALUE_TODO));
        assertFalse(State.isValidState(VALID_STATE_VALUE_DONE + " " + VALID_STATE_VALUE_ONGOING));
        assertFalse(State.isValidState(VALID_STATE_VALUE_DONE + " " + VALID_STATE_VALUE_DONE));
        assertFalse(State.isValidState(VALID_STATE_VALUE_ONGOING + " " + VALID_STATE_VALUE_TODO));
        assertFalse(State.isValidState(VALID_STATE_VALUE_ONGOING + " " + VALID_STATE_VALUE_DONE));
        assertFalse(State.isValidState(VALID_STATE_VALUE_ONGOING + " " + VALID_STATE_VALUE_BACKLOG));
        assertFalse(State.isValidState(VALID_STATE_VALUE_ONGOING + " " + VALID_STATE_VALUE_ONGOING));

        assertFalse(State.isValidState(" backlog")); //leading space
        assertFalse(State.isValidState("backlog ")); //trailing space
        assertFalse(State.isValidState(" backlog ")); //leading and trailing space
        assertFalse(State.isValidState("fillertextbacklog")); //leading filler text
        assertFalse(State.isValidState("backlogfillertext")); //trailing filler text
        assertFalse(State.isValidState("fillertextbacklogfillertext")); //sandwiched valid word
        assertFalse(State.isValidState("backlogtododoneongoing")); //valid words in invalid string

    }

    @Test
    public void isEqualState() {
        assertEquals(stateBacklogOne, stateBacklogOne); // same object
        assertEquals(stateBacklogOne, stateBacklogTwo); // same value
        assertEquals(stateTodoOne, stateTodoOne); // same object
        assertEquals(stateTodoOne, stateTodoTwo); // same value
        assertEquals(stateOngoingOne, stateOngoingOne); // same object
        assertEquals(stateOngoingOne, stateOngoingTwo); // same value
        assertEquals(stateDoneOne, stateDoneOne); // same object
        assertEquals(stateDoneOne, stateDoneTwo); // same value
    }

    @Test
    public void isNotEqualState() {
        assertNotEquals(stateBacklogOne, stateTodoOne); // different object value
        assertNotEquals(stateBacklogOne, stateOngoingTwo); // different object value
        assertNotEquals(stateBacklogOne, stateDoneTwo); // different object value
        assertNotEquals(stateTodoOne, stateBacklogTwo); // different object value
        assertNotEquals(stateTodoOne, stateOngoingOne); // different object value
        assertNotEquals(stateTodoOne, stateDoneTwo); // different object value
        assertNotEquals(stateOngoingOne, stateBacklogOne); // different object value
        assertNotEquals(stateOngoingOne, stateTodoTwo); // different object value
        assertNotEquals(stateOngoingOne, stateDoneOne); // different object value
    }

    @Test
    public void isCorrectStateValue() {
        String expectedErrorMessage = State.MESSAGE_CONSTRAINTS;

        //Valid States
        assertEquals(State.Value.BACKLOG, State.getValueOfState(VALID_STATE_VALUE_BACKLOG));
        assertEquals(State.Value.TODO, State.getValueOfState(VALID_STATE_VALUE_TODO));
        assertEquals(State.Value.ONGOING, State.getValueOfState(VALID_STATE_VALUE_ONGOING));
        assertEquals(State.Value.DONE, State.getValueOfState(VALID_STATE_VALUE_DONE));

        //Invalid State
        assertThrows(IllegalArgumentException.class, expectedErrorMessage, () -> State.getValueOfState("invalidState"));
    }

    @Test
    public void isCorrectStateValueString() {
        assertEquals(VALID_STATE_VALUE_BACKLOG, stateBacklogOne.toString());
        assertEquals(VALID_STATE_VALUE_TODO, stateTodoOne.toString());
        assertEquals(VALID_STATE_VALUE_ONGOING, stateOngoingOne.toString());
        assertEquals(VALID_STATE_VALUE_DONE, stateDoneOne.toString());
    }

}
