package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.State;

public class EditByStateCommand extends EditCommand {

    private State targetState;

    /**
     * @param index of the bug in the filtered bug list to edit
     * @param editBugDescriptor details to edit the bug with
     */
    public EditByStateCommand(Index index, EditBugDescriptor editBugDescriptor, State targetState) {
        super(index, editBugDescriptor);
        requireNonNull(index);
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
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditByStateCommand)) {
            return false;
        }

        // state check
        EditByStateCommand e = (EditByStateCommand) other;
        return index.equals(e.index)
                   && editBugDescriptor.equals(e.editBugDescriptor)
                   && targetState.equals(e.targetState);
    }

}
