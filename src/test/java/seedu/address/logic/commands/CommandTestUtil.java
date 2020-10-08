package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
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
import seedu.address.testutil.EditBugDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_IDA = "Ida Mueller";
    public static final String VALID_STATE_AMY = "todo";
    public static final String VALID_STATE_BOB = "done";
    public static final String VALID_DESCRIPTION_AMY = "Block 312, Amy Street 1";
    public static final String VALID_DESCRIPTION_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_DESCRIPTION_IDA = "chicago ave";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_IDA = " " + PREFIX_NAME + VALID_NAME_IDA;
    public static final String STATE_DESC_AMY = " " + PREFIX_STATE + VALID_STATE_AMY;
    public static final String STATE_DESC_BOB = " " + PREFIX_STATE + VALID_STATE_BOB;
    public static final String DESCRIPTION_DESC_AMY = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_AMY;
    public static final String DESCRIPTION_DESC_BOB = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BOB;
    public static final String DESCRIPTION_DESC_IDA = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_IDA;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_STATE_DESC = " " + PREFIX_STATE + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION; // descriptions cannot be empty
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditBugDescriptor DESC_AMY;
    public static final EditCommand.EditBugDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditBugDescriptorBuilder().withName(VALID_NAME_AMY)
                .withState(VALID_STATE_AMY).withDescription(VALID_DESCRIPTION_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditBugDescriptorBuilder().withName(VALID_NAME_BOB)
                .withState(VALID_STATE_BOB).withDescription(VALID_DESCRIPTION_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
     * - the bug tracker, filtered bug list and selected bug in {@code actualModel} remain unchanged
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
