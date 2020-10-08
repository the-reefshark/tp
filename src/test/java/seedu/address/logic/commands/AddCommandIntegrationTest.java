package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBugs.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.bug.Bug;
import seedu.address.testutil.BugBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newBug_success() {
        Bug validBug = new BugBuilder().build();

        Model expectedModel = new ModelManager(model.getKanBugTracker(), new UserPrefs());
        expectedModel.addBug(validBug);

        assertCommandSuccess(new AddCommand(validBug), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validBug), expectedModel);
    }

    @Test
    public void execute_duplicateBug_throwsCommandException() {
        Bug bugInList = model.getKanBugTracker().getBugList().get(0);
        assertCommandFailure(new AddCommand(bugInList), model, AddCommand.MESSAGE_DUPLICATE_BUG);
    }

}
