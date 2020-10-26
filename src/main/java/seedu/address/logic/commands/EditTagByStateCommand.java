package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

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
        requireNonNull(targetState);
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
        //short circuit if same object
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditTagByStateCommand)) {
            return false;
        }

        EditTagByStateCommand e = (EditTagByStateCommand) other;
        return index.equals(e.index)
                && oldTag.equals(e.oldTag)
                && newTag.equals(e.newTag)
                && targetState.equals(e.targetState);
    }

}
