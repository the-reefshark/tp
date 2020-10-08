package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.KanBugTracker;
import seedu.address.model.Model;

/**
 * Clears the bug tracker.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "KanBug Tracker has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setKanBugTracker(new KanBugTracker());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
