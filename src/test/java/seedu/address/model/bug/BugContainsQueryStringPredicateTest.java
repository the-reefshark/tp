package seedu.address.model.bug;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_EIGHT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_ELEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_FOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_NINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_TEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_TWELVE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_TWO;
import static seedu.address.testutil.TypicalBugs.BUGFOUR;
import static seedu.address.testutil.TypicalBugs.BUGONE;
import static seedu.address.testutil.TypicalBugs.BUGSEVEN;
import static seedu.address.testutil.TypicalBugs.BUGSIX;
import static seedu.address.testutil.TypicalBugs.BUGTHREE;
import static seedu.address.testutil.TypicalBugs.BUGTWO;

import org.junit.jupiter.api.Test;

public class BugContainsQueryStringPredicateTest {

    @Test
    public void equals() {
        String firstPredicateQueryString = VALID_QUERY_STRING_ONE;
        String secondPredicateQueryString = VALID_QUERY_STRING_TWO;

        BugContainsQueryStringPredicate firstPredicate = getQueryStringPredicate(firstPredicateQueryString);
        BugContainsQueryStringPredicate secondPredicate = getQueryStringPredicate(secondPredicateQueryString);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BugContainsQueryStringPredicate firstPredicateCopy = getQueryStringPredicate(firstPredicateQueryString);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different bug -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsQueryString_returnsTrue() {
        // One keyword query-string
        BugContainsQueryStringPredicate predicate = getQueryStringPredicate(VALID_QUERY_STRING_EIGHT);
        assertTrue(predicate.test(BUGONE));

        // Multiple keywords query-string
        predicate = new BugContainsQueryStringPredicate(VALID_QUERY_STRING_NINE);
        assertTrue(predicate.test(BUGTHREE));
    }

    @Test
    public void test_descriptionContainsQueryString_returnsTrue() {
        // One keyword query-string
        BugContainsQueryStringPredicate predicate = getQueryStringPredicate(VALID_QUERY_STRING_TEN);
        assertTrue(predicate.test(BUGSEVEN));

        // Multiple keywords query-string
        predicate = new BugContainsQueryStringPredicate(VALID_QUERY_STRING_ELEVEN);
        assertTrue(predicate.test(BUGFOUR));
    }

    @Test
    public void test_tagContainsQueryString_returnsTrue() {
        // One keyword query-string
        BugContainsQueryStringPredicate predicate = getQueryStringPredicate(VALID_QUERY_STRING_TWELVE);
        assertTrue(predicate.test(BUGSIX));
    }

    @Test
    public void test_nameDoesNotContainQueryString_returnsFalse() {
        // Non-matching keyword query-string
        BugContainsQueryStringPredicate predicate = getQueryStringPredicate(VALID_QUERY_STRING_ONE);
        assertFalse(predicate.test(BUGTWO));
    }

    @Test
    public void test_descriptionDoesNotContainQueryString_returnsFalse() {
        // Non-matching keyword query-string
        BugContainsQueryStringPredicate predicate = getQueryStringPredicate(VALID_QUERY_STRING_FOUR);
        assertFalse(predicate.test(BUGFOUR));
    }

    /**
     * Parses {@code userInput} into a {@code BugContainsQueryStringPredicate}.
     */
    private BugContainsQueryStringPredicate getQueryStringPredicate(String userInput) {
        return new BugContainsQueryStringPredicate(userInput);
    }
}
