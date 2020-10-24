package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUGS;

import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;

public class AddTagByStateCommand extends AddTagCommand {
    private final State targetState;
    /**
     * Creates a new instance of AddTagCommand
     *
     * @param index  of bug to add the tag to
     * @param newTag to add to the bug
     * @param  targetState which indicates which state to modify
     */
    public AddTagByStateCommand(Index index, Tag newTag, State targetState) {
        super(index, newTag);
        this.targetState = targetState;
    }

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Bug> lastShownList = model.getFilteredBugListByState(targetState);

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
        }

        Bug bugToEdit = lastShownList.get(index.getZeroBased());
        Bug editedBug = addTagToBug(bugToEdit, newTag);

        model.setBug(bugToEdit, editedBug);
        model.updateFilteredBugList(PREDICATE_SHOW_ALL_BUGS);
        return new CommandResult(String.format(MESSAGE_ADD_BUG_SUCCESS, editedBug));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagByStateCommand)) {
            return false;
        }

        // state check
        AddTagByStateCommand e = (AddTagByStateCommand) other;
        return index.equals(e.index)
                   && newTag.equals(e.newTag)
                   && targetState.equals(e.targetState);
    }
}
