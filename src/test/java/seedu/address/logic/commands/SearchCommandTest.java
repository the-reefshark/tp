package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_BUGS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_EIGHT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_FIVE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_FOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_NINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_SEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_SIX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_TWO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBugs.BUG_FIVE;
import static seedu.address.testutil.TypicalBugs.BUG_FOUR;
import static seedu.address.testutil.TypicalBugs.BUG_ONE;
import static seedu.address.testutil.TypicalBugs.BUG_SEVEN;
import static seedu.address.testutil.TypicalBugs.BUG_SIX;
import static seedu.address.testutil.TypicalBugs.BUG_THREE;
import static seedu.address.testutil.TypicalBugs.BUG_TWO;
import static seedu.address.testutil.TypicalBugs.getTypicalKanBugTracker;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.bug.BugContainsQueryStringPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalKanBugTracker(), new UserPrefs());

    @Test
    public void equals() {
        SearchCommand searchFirstCommand = getSearchCommand(VALID_QUERY_STRING_ONE);
        SearchCommand searchSecondCommand = getSearchCommand(VALID_QUERY_STRING_TWO);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        SearchCommand searchFirstCommandCopy = getSearchCommand(VALID_QUERY_STRING_ONE);
        assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different bug -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void execute_emptyData_noBugFound() {
        String expectedMessage = String.format(MESSAGE_BUGS_LISTED_OVERVIEW, 0);
        SearchCommand command = getSearchCommand(VALID_QUERY_STRING_THREE);
        expectedModel.updateFilteredBugList(getQueryStringPredicate(VALID_QUERY_STRING_THREE));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredBugList());
    }

    @Test
    public void execute_oneKeywordQueryString_oneBugFound() {
        String expectedMessage = String.format(MESSAGE_BUGS_LISTED_OVERVIEW, 1);
        SearchCommand command = getSearchCommand(VALID_QUERY_STRING_FOUR);
        expectedModel.updateFilteredBugList(getQueryStringPredicate(VALID_QUERY_STRING_FOUR));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BUG_SEVEN), model.getFilteredBugList());
    }

    @Test
    public void execute_oneMixedCaseKeywordQueryString_oneBugFound() {
        String expectedMessage = String.format(MESSAGE_BUGS_LISTED_OVERVIEW, 1);
        SearchCommand command = getSearchCommand(VALID_QUERY_STRING_FIVE);
        expectedModel.updateFilteredBugList(getQueryStringPredicate(VALID_QUERY_STRING_FIVE));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BUG_FIVE), model.getFilteredBugList());
    }

    @Test
    public void execute_multipleKeywordsQueryString_oneBugFound() {
        String expectedMessage = String.format(MESSAGE_BUGS_LISTED_OVERVIEW, 1);
        SearchCommand command = getSearchCommand(VALID_QUERY_STRING_SIX);
        expectedModel.updateFilteredBugList(getQueryStringPredicate(VALID_QUERY_STRING_SIX));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BUG_SIX), model.getFilteredBugList());
    }

    @Test
    public void execute_oneKeywordQueryString_multipleBugsFound() {
        String expectedMessage = String.format(MESSAGE_BUGS_LISTED_OVERVIEW, 4);
        SearchCommand command = getSearchCommand(VALID_QUERY_STRING_SEVEN);
        expectedModel.updateFilteredBugList(getQueryStringPredicate(VALID_QUERY_STRING_SEVEN));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BUG_ONE, BUG_TWO, BUG_FOUR, BUG_FIVE), model.getFilteredBugList());
    }

    @Test
    public void execute_oneMixedCaseKeywordQueryString_multipleBugsFound() {
        String expectedMessage = String.format(MESSAGE_BUGS_LISTED_OVERVIEW, 4);
        SearchCommand command = getSearchCommand(VALID_QUERY_STRING_EIGHT);
        expectedModel.updateFilteredBugList(getQueryStringPredicate(VALID_QUERY_STRING_EIGHT));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BUG_ONE, BUG_TWO, BUG_FOUR, BUG_FIVE), model.getFilteredBugList());
    }

    @Test
    public void execute_multipleMixedCaseKeywordsQueryString_multipleBugsFound() {
        String expectedMessage = String.format(MESSAGE_BUGS_LISTED_OVERVIEW, 2);
        SearchCommand command = getSearchCommand(VALID_QUERY_STRING_NINE);
        expectedModel.updateFilteredBugList(getQueryStringPredicate(VALID_QUERY_STRING_NINE));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BUG_THREE, BUG_SIX), model.getFilteredBugList());
    }

    /**
     * Parses {@code userInput} into a {@code BugContainsQueryStringPredicate}.
     */
    private BugContainsQueryStringPredicate getQueryStringPredicate(String userInput) {
        return new BugContainsQueryStringPredicate(userInput);
    }

    /**
     * Returns the search command taking in the particular user's input.
     * @param userInput user's input.
     */
    private SearchCommand getSearchCommand(String userInput) {
        return new SearchCommand(getQueryStringPredicate(userInput));
    }
}
