package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.model.bug.State;

public class EditByStateCommand extends EditCommand {

    private State targetState;

    /**
     * @param index of the bug in the filtered bug list to edit
     * @param editBugDescriptor details to edit the bug with
     */
    public EditByStateCommand(Index index, EditBugDescriptor editBugDescriptor, State targetState) {
        super(index, editBugDescriptor);
        this.targetState = targetState;
    }

    
}
