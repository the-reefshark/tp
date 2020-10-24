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

public class EditTagByStateCommand extends EditTagCommand {

    private final State targetState;
    /**
     * @param index  of the bug in the filtered bug list to edit
     * @param oldTag to be modified
     * @param newTag to replace old tag
     * @param targetState the state that we are targeting
     */
    public EditTagByStateCommand(Index index, Tag oldTag, Tag newTag, State targetState) {
        super(index, oldTag, newTag);
        this.targetState = targetState;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Bug> lastShownList = model.getFilteredBugListByState(targetState);

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
        }

        Bug bugToEdit = lastShownList.get(index.getZeroBased());
        Bug editedBug = updateTagInBug(bugToEdit, oldTag, newTag);

        model.setBug(bugToEdit, editedBug);
        model.updateFilteredBugList(PREDICATE_SHOW_ALL_BUGS);
        return new CommandResult(String.format(MESSAGE_EDIT_BUG_SUCCESS, editedBug));
    }
}
