package seedu.address.model.bug;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
        assertFalse(State.isValidState("@example.com")); // missing local part
        assertFalse(State.isValidState("peterjackexample.com")); // missing '@' symbol
        assertFalse(State.isValidState("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(State.isValidState("peterjack@-")); // invalid domain name
        assertFalse(State.isValidState("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(State.isValidState("peter jack@example.com")); // spaces in local part
        assertFalse(State.isValidState("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(State.isValidState(" peterjack@example.com")); // leading space
        assertFalse(State.isValidState("peterjack@example.com ")); // trailing space
        assertFalse(State.isValidState("peterjack@@example.com")); // double '@' symbol
        assertFalse(State.isValidState("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(State.isValidState("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(State.isValidState("peterjack@.example.com")); // domain name starts with a period
        assertFalse(State.isValidState("peterjack@example.com.")); // domain name ends with a period
        assertFalse(State.isValidState("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(State.isValidState("peterjack@example.com-")); // domain name ends with a hyphen

        // valid state
        assertTrue(State.isValidState("PeterJack_1190@example.com"));
        assertTrue(State.isValidState("a@bc")); // minimal
        assertTrue(State.isValidState("test@localhost")); // alphabets only
        assertTrue(State.isValidState("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(State.isValidState("123@145")); // numeric local part and domain name
        assertTrue(State.isValidState("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(State.isValidState("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(State.isValidState("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
