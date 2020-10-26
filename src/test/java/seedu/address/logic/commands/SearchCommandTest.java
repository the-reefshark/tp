package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_BUGS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBugs.BUGFIVE;
import static seedu.address.testutil.TypicalBugs.BUGFOUR;
import static seedu.address.testutil.TypicalBugs.BUGONE;
import static seedu.address.testutil.TypicalBugs.BUGSEVEN;
import static seedu.address.testutil.TypicalBugs.BUGSIX;
import static seedu.address.testutil.TypicalBugs.BUGTHREE;
import static seedu.address.testutil.TypicalBugs.BUGTWO;
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
        BugContainsQueryStringPredicate firstPredicate =
                new BugContainsQueryStringPredicate("first");
        BugContainsQueryStringPredicate secondPredicate =
                new BugContainsQueryStringPredicate("second");

        SearchCommand searchFirstCommand = new SearchCommand(firstPredicate);
        SearchCommand searchSecondCommand = new SearchCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        SearchCommand searchFirstCommandCopy = new SearchCommand(firstPredicate);
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
        BugContainsQueryStringPredicate predicate = preparePredicate("No data");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredBugList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredBugList());
    }

    @Test
    public void execute_oneKeywordQueryString_oneBugFound() {
        String expectedMessage = String.format(MESSAGE_BUGS_LISTED_OVERVIEW, 1);
        BugContainsQueryStringPredicate predicate = preparePredicate("jar");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredBugList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BUGSEVEN), model.getFilteredBugList());
    }

    @Test
    public void execute_oneMixedCaseKeywordQueryString_oneBugFound() {
        String expectedMessage = String.format(MESSAGE_BUGS_LISTED_OVERVIEW, 1);
        BugContainsQueryStringPredicate predicate = preparePredicate("eXiT");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredBugList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BUGFIVE), model.getFilteredBugList());
    }

    @Test
    public void execute_multipleKeywordsQueryString_oneBugFound() {
        String expectedMessage = String.format(MESSAGE_BUGS_LISTED_OVERVIEW, 1);
        BugContainsQueryStringPredicate predicate = preparePredicate("Note rendering");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredBugList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BUGSIX), model.getFilteredBugList());
    }

    @Test
    public void execute_oneKeywordQueryString_multipleBugsFound() {
        String expectedMessage = String.format(MESSAGE_BUGS_LISTED_OVERVIEW, 4);
        BugContainsQueryStringPredicate predicate = preparePredicate("command");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredBugList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BUGONE, BUGTWO, BUGFOUR, BUGFIVE), model.getFilteredBugList());
    }

    @Test
    public void execute_oneMixedCaseKeywordQueryString_multipleBugsFound() {
        String expectedMessage = String.format(MESSAGE_BUGS_LISTED_OVERVIEW, 4);
        BugContainsQueryStringPredicate predicate = preparePredicate("cOmMaND");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredBugList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BUGONE, BUGTWO, BUGFOUR, BUGFIVE), model.getFilteredBugList());
    }

    @Test
    public void execute_multipleMixedCaseKeywordsQueryString_multipleBugsFound() {
        String expectedMessage = String.format(MESSAGE_BUGS_LISTED_OVERVIEW, 2);
        BugContainsQueryStringPredicate predicate = preparePredicate("mAiN wINDow");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredBugList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BUGTHREE, BUGSIX), model.getFilteredBugList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private BugContainsQueryStringPredicate preparePredicate(String userInput) {
        return new BugContainsQueryStringPredicate(userInput);
    }
}