package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLDTAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUGS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;

public class EditTagCommand extends Command {

    public static final String COMMAND_WORD = "editTag";

    //TODO Update the DG such that only valid tags are used


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the tags of "
            + "the bug identified by the index number used in the displayed bug list."
            + "The existing tag supplied by the user will be replaced with the new tag given"
            + "as input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_OLDTAG + "OLD_TAG "
            + PREFIX_NEWTAG + "NEW_TAG\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_OLDTAG + "display " + PREFIX_NEWTAG + "Ui";

    public static final String MESSAGE_EDIT_BUG_SUCCESS = "Edited Tag: %1$s";
    public static final String MESSAGE_NOT_EDITED = "A valid existing tag must be supplied.";

    private Index index;
    private Tag oldTag;
    private Tag newTag;

    /**
     * @param index of the bug in the filtered bug list to edit
     * @param oldTag to be modified
     * @param newTag to replace old tag
     */
    public EditTagCommand(Index index, Tag oldTag, Tag newTag) {
        this.index = index;
        this.oldTag = oldTag;
        this.newTag = newTag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Bug> lastShownList = model.getFilteredBugList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
        }

        Bug bugToEdit = lastShownList.get(index.getZeroBased());
        Bug editedBug = updateTagInBug(bugToEdit, oldTag, newTag);

        assert (!bugToEdit.isSameBug(editedBug) && !model.hasBug(editedBug));

        model.setBug(bugToEdit, editedBug);
        model.updateFilteredBugList(PREDICATE_SHOW_ALL_BUGS);
        return new CommandResult(String.format(MESSAGE_EDIT_BUG_SUCCESS, editedBug));
    }

    private static Bug updateTagInBug(Bug bugToEdit, Tag oldTag, Tag newTag) throws CommandException {
        assert bugToEdit != null;

        Set<Tag> existingTagSet = bugToEdit.getTags();

        if (!existingTagSet.contains(oldTag)) {
            throw new CommandException((MESSAGE_NOT_EDITED));
        }

        Name bugName = bugToEdit.getName();
        State bugState = bugToEdit.getState();
        Description bugDescription = bugToEdit.getDescription();
        Set<Tag> updatedTags = updateTagSet(existingTagSet, oldTag, newTag);

        return new Bug(bugName, bugState, bugDescription, updatedTags);
    }

    private static Set<Tag> updateTagSet(Set<Tag> existingTagSet, Tag oldTag, Tag newTag) {
        assert existingTagSet.contains(oldTag);
        Set<Tag> setCopy = new HashSet<>(existingTagSet);
        setCopy.remove(oldTag);
        setCopy.add(newTag);
        return setCopy;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTagCommand)) {
            return false;
        }

        // state check
        EditTagCommand e = (EditTagCommand) other;
        return index.equals(e.index)
                       && oldTag.equals(e.oldTag)
                       && newTag.equals(e.newTag);
    }


}
