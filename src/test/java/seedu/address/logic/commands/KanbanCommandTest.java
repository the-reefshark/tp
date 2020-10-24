package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class KanbanCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_kanban_success() {
        CommandResult expectedCommandResult = new CommandResult("", false, false, true);
        assertCommandSuccess(new KanbanCommand(), model, expectedCommandResult, expectedModel);
    }
}
