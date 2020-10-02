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
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new State(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> State.isValidEmail(null));

        // blank email
        assertFalse(State.isValidEmail("")); // empty string
        assertFalse(State.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(State.isValidEmail("@example.com")); // missing local part
        assertFalse(State.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(State.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(State.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(State.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(State.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(State.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(State.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(State.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(State.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(State.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(State.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(State.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(State.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(State.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(State.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(State.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(State.isValidEmail("a@bc")); // minimal
        assertTrue(State.isValidEmail("test@localhost")); // alphabets only
        assertTrue(State.isValidEmail("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(State.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(State.isValidEmail("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(State.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(State.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
