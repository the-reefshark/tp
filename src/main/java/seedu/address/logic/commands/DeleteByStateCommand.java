package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.State;

public class DeleteByStateCommand extends DeleteCommand {
    private State targetState;

    /**
     * @param targetIndex index of the bug in the filtered bug list to delete
     * @param targetState current state of the bug to be deleted.
     */
    public DeleteByStateCommand(Index targetIndex, State targetState) {
        super(targetIndex);
        requireNonNull(targetState);
        requireNonNull(targetIndex);
        this.targetState = targetState;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Bug> lastShownList = model.getFilteredBugListByState(targetState);

        return updateList(lastShownList, model);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        } else if (!(other instanceof DeleteByStateCommand)) {
            return false; // different object
        }
        DeleteByStateCommand d = (DeleteByStateCommand) other;

        return targetState.equals(d.targetState)
            && targetIndex.equals(d.targetIndex);
    }
}
