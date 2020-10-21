package seedu.address.model.bug;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BugBuilder;

public class BugContainsQueryStringPredicateTest {

    @Test
    public void equals() {
        String firstPredicateQueryString = "first";
        String secondPredicateQueryString = "second";

        BugContainsQueryStringPredicate firstPredicate =
                new BugContainsQueryStringPredicate(firstPredicateQueryString);
        BugContainsQueryStringPredicate secondPredicate =
                new BugContainsQueryStringPredicate(secondPredicateQueryString);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BugContainsQueryStringPredicate firstPredicateCopy =
                new BugContainsQueryStringPredicate(firstPredicateQueryString);
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
        BugContainsQueryStringPredicate predicate = new BugContainsQueryStringPredicate("message");
        assertTrue(predicate.test(new BugBuilder().withName("Print wrong messages").build()));

        // One query-string
        predicate = new BugContainsQueryStringPredicate("Ui display");
        assertTrue(predicate.test(new BugBuilder().withName("Ui display").build()));

        // Mixed-case keyword query-string
        predicate = new BugContainsQueryStringPredicate("ui");
        assertTrue(predicate.test(new BugBuilder().withName("Ui Bug").build()));

        // Mixed-case query-string
        predicate = new BugContainsQueryStringPredicate("uI bUG");
        assertTrue(predicate.test(new BugBuilder().withName("Ui Bug").build()));
    }

    @Test
    public void test_descriptionContainsQueryString_returnsTrue() {
        // One keyword query-string
        BugContainsQueryStringPredicate predicate = new BugContainsQueryStringPredicate("message");
        assertTrue(predicate.test(new BugBuilder().withDescription("Print wrong messages").build()));

        // One query-string
        predicate = new BugContainsQueryStringPredicate("Ui display");
        assertTrue(predicate.test(new BugBuilder().withDescription("Ui display").build()));

        // Mixed-case keyword query-string
        predicate = new BugContainsQueryStringPredicate("ui");
        assertTrue(predicate.test(new BugBuilder().withDescription("Ui Bug").build()));

        // Mixed-case query-string
        predicate = new BugContainsQueryStringPredicate("uI bUG");
        assertTrue(predicate.test(new BugBuilder().withDescription("Ui Bug").build()));
    }

    @Test
    public void test_tagContainsQueryString_returnsTrue() {
        // One keyword query-string
        String[] tags = {"Ui", "frontend", "JavaFX"};
        BugContainsQueryStringPredicate predicate = new BugContainsQueryStringPredicate("frontend");
        assertTrue(predicate.test(new BugBuilder().withTags(tags).build()));

        // Mixed-case keyword query-string
        predicate = new BugContainsQueryStringPredicate("uI");
        assertTrue(predicate.test(new BugBuilder().withTags(tags).build()));

        predicate = new BugContainsQueryStringPredicate("Java");
        assertTrue(predicate.test(new BugBuilder().withTags(tags).build()));

        predicate = new BugContainsQueryStringPredicate("fx");
        assertTrue(predicate.test(new BugBuilder().withTags(tags).build()));
    }

    @Test
    public void test_nameDoesNotContainQueryString_returnsFalse() {
        // Non-matching keyword query-string
        BugContainsQueryStringPredicate predicate = new BugContainsQueryStringPredicate("Ui");
        assertFalse(predicate.test(new BugBuilder().withName("Print wrong messages").build()));
    }

    @Test
    public void test_descriptionDoesNotContainQueryString_returnsFalse() {
        // Non-matching keyword query-string
        String[] tags = {"Ui", "frontend", "JavaFX"};
        BugContainsQueryStringPredicate predicate = new BugContainsQueryStringPredicate("backend");
        assertFalse(predicate.test(new BugBuilder().withTags(tags).build()));
    }
}
