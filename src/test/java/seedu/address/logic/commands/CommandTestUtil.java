package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLUMN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLDTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.KanBugTracker;
import seedu.address.model.Model;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.NameContainsKeywordsPredicate;
import seedu.address.model.bug.State;
import seedu.address.testutil.EditBugDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_PARSER = "Program cannot start up";
    public static final String VALID_NAME_HOMEPAGE = "Duplicate items in list";
    public static final String VALID_NAME_UI = "ArrayOutOfBounds Error";
    public static final String VALID_STATE_PARSER = "backlog";
    public static final String VALID_STATE_HOMEPAGE = "done";
    public static final String VALID_DESCRIPTION_PARSER = "Blank screen on launching application";
    public static final String VALID_DESCRIPTION_HOMEPAGE = "Duplicates in list when searching for a specific bug";
    public static final String VALID_DESCRIPTION_UI = "No input validation for invalid index";
    public static final String VALID_NOTE_PARSER = "this bug has been encountered on numerous occasions in the parser";
    public static final String VALID_NOTE_HOMEPAGE = "this is a very rare problem that has not been encountered before";
    public static final String VALID_NOTE_BLANK = "";
    public static final String VALID_PRIORITY_PARSER = "medium";
    public static final String VALID_PRIORITY_HOMEPAGE = "high";
    public static final String VALID_TAG_COMPONENT = "UI";
    public static final String VALID_TAG_FRIEND = "Logic";
    public static final String VALID_COLUMN_TODO = "todo";
    public static final State VALID_STATE_BUG1 = new State("todo");
    public static final State VALID_STATE_BUG2 = new State("backlog");

    public static final String NAME_DESC_PARSER = " " + PREFIX_NAME + VALID_NAME_PARSER;
    public static final String NAME_DESC_HOMEPAGE = " " + PREFIX_NAME + VALID_NAME_HOMEPAGE;
    public static final String NAME_DESC_UI = " " + PREFIX_NAME + VALID_NAME_UI;
    public static final String STATE_DESC_PARSER = " " + PREFIX_STATE + VALID_STATE_PARSER;
    public static final String STATE_DESC_HOMEPAGE = " " + PREFIX_STATE + VALID_STATE_HOMEPAGE;
    public static final String DESCRIPTION_DESC_PARSER = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_PARSER;
    public static final String DESCRIPTION_DESC_HOMEPAGE = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_HOMEPAGE;
    public static final String DESCRIPTION_DESC_UI = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_UI;
    public static final String NOTE_DESC_PARSER = " " + PREFIX_NOTE + VALID_NOTE_PARSER;
    public static final String NOTE_DESC_HOMEPAGE = " " + PREFIX_NOTE + VALID_NOTE_HOMEPAGE;
    public static final String TAG_DESC_FRONTEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_BACKEND = " " + PREFIX_TAG + VALID_TAG_COMPONENT;
    public static final String TAG_DESC_OLD = " " + PREFIX_OLDTAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_NEW = " " + PREFIX_NEWTAG + VALID_TAG_COMPONENT;
    public static final String PRIORITY_DESC_PARSER = " " + PREFIX_PRIORITY + VALID_PRIORITY_PARSER;
    public static final String PRIORITY_DESC_HOMEPAGE = " " + PREFIX_PRIORITY + VALID_PRIORITY_HOMEPAGE;
    public static final String COLUMN_DESC_TODO = " " + PREFIX_COLUMN + VALID_COLUMN_TODO;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_STATE_DESC = " " + PREFIX_STATE + "backklog"; // typo of backog
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION; // descriptions cannot be empty
    public static final String INVALID_NOTE_DESC = " " + PREFIX_NOTE; // note cannot be empty when bug is being added
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_TAG_OLD = " " + PREFIX_OLDTAG + "prints.java"; // '.' not allowed in tags
    public static final String INVALID_TAG_NEW = " " + PREFIX_NEWTAG + "hubs("; // '(' not allowed in tags
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "hig"; // typo of high
    public static final String INVALID_COLUMN_DESC = " " + PREFIX_COLUMN + "todos"; // typo of todo

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditBugDescriptor DESC_PARSER;
    public static final EditCommand.EditBugDescriptor DESC_HOMEPAGE;

    static {
        DESC_PARSER = new EditBugDescriptorBuilder().withName(VALID_NAME_PARSER)
                .withState(VALID_STATE_PARSER).withDescription(VALID_DESCRIPTION_PARSER)
                .withNote(VALID_NOTE_PARSER).withTags(VALID_TAG_FRIEND)
                .withPriority(VALID_PRIORITY_PARSER).build();
        DESC_HOMEPAGE = new EditBugDescriptorBuilder().withName(VALID_NAME_HOMEPAGE)
                .withState(VALID_STATE_HOMEPAGE).withDescription(VALID_DESCRIPTION_HOMEPAGE)
                .withNote(VALID_NOTE_HOMEPAGE).withTags(VALID_TAG_COMPONENT, VALID_TAG_FRIEND)
                .withPriority(VALID_PRIORITY_HOMEPAGE).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the bug tracker, filtered bug list and selected  bug in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        KanBugTracker expectedKanBugTracker = new KanBugTracker(actualModel.getKanBugTracker());
        List<Bug> expectedFilteredList = new ArrayList<>(actualModel.getFilteredBugList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedKanBugTracker, actualModel.getKanBugTracker());
        assertEquals(expectedFilteredList, actualModel.getFilteredBugList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the bug at the given {@code targetIndex} in the
     * {@code model}'s bug tracker.
     */
    public static void showBugAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredBugList().size());

        Bug bug = model.getFilteredBugList().get(targetIndex.getZeroBased());
        final String[] splitName = bug.getName().fullName.split("\\s+");
        model.updateFilteredBugList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredBugList().size());
    }

}
