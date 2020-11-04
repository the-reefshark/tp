package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLUMN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUGS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.State;

public class MoveCommand extends Command {

    public static final String COMMAND_WORD = "move";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Move the bug to new state identified "
            + "by the index number used in the displayed bug list. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_STATE + "STATE "
            + "[" + PREFIX_COLUMN + "STATE]"
            + "Example: " + COMMAND_WORD
            + PREFIX_STATE + "done";

    public static final String MESSAGE_MOVE_BUG_SUCCESS = "Moved Bug: %1$s";
    public static final String MESSAGE_DUPLICATE_STATE =
            "Cannot move bug to the same state. Please try another state instead!";

    protected final Index index;
    protected final State state;

    /**
     * @param index of the bug in the filtered bug list to edit
     * @param state details to edit the bug with
     */
    public MoveCommand(Index index, State state) {
        requireNonNull(index);
        requireNonNull(state);
        this.index = index;
        this.state = state;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Bug> lastShownList = model.getFilteredBugList();

        return updateList(lastShownList, model);
    }

    protected static Bug createMovedBug(Bug bugToMove, State destination) {
        assert bugToMove != null;
        return new Bug(bugToMove.getName(), destination, bugToMove.getDescription(), bugToMove.getOptionalNote(),
                bugToMove.getTags(), bugToMove.getPriority());
    }

    protected CommandResult updateList(List<Bug> lastShownList, Model model) throws CommandException {
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
        }

        Bug bugToMove = lastShownList.get(index.getZeroBased());
        Bug movedBug = createMovedBug(bugToMove, state);

        if (model.hasBug(movedBug)) {
            throw new CommandException(MESSAGE_DUPLICATE_STATE);
        }

        model.setBug(bugToMove, movedBug);
        model.updateFilteredBugList(PREDICATE_SHOW_ALL_BUGS);

        return new CommandResult(String.format(MESSAGE_MOVE_BUG_SUCCESS, movedBug));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MoveCommand)) {
            return false;
        }

        // state check
        MoveCommand e = (MoveCommand) other;
        return index.equals(e.index)
                && state.equals(e.state);
    }
}
