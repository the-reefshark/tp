package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;
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

public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "addTag";

    //TODO Update the DG such that only valid tags are used

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to the"
            + "bug identified by the index number used in the displayed bug list."
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NEWTAG + "NEW_TAG\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NEWTAG + "Ui";

    public static final String MESSAGE_ADD_BUG_SUCCESS = "Added Tag: %1$s";
    public static final String MESSAGE_NOT_ADDED = "A valid tag must be supplied.";

    private Index index;
    private Tag newTag;

    /**
     * Creates a new instance of AddTagCommand
     * @param index of bug to add the tag to
     * @param newTag to add to the bug
     */
    public AddTagCommand(Index index, Tag newTag) {
        this.index = index;
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
        Bug editedBug = addTagToBug(bugToEdit, newTag);

        model.setBug(bugToEdit, editedBug);
        model.updateFilteredBugList(PREDICATE_SHOW_ALL_BUGS);
        return new CommandResult(String.format(MESSAGE_ADD_BUG_SUCCESS, editedBug));
    }

    private static Bug addTagToBug(Bug bugToEdit, Tag newTag) throws CommandException {
        assert bugToEdit != null;

        Set<Tag> existingTagSet = bugToEdit.getTags();

        Name bugName = bugToEdit.getName();
        State bugState = bugToEdit.getState();
        Description bugDescription = bugToEdit.getDescription();
        Set<Tag> updatedTags = addTagSet(existingTagSet, newTag);

        return new Bug(bugName, bugState, bugDescription, updatedTags);
    }

    private static Set<Tag> addTagSet(Set<Tag> existingTagSet, Tag newTag) {
        Set<Tag> setCopy = new HashSet<>(existingTagSet);
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
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        // state check
        AddTagCommand e = (AddTagCommand) other;
        return index.equals(e.index)
                       && newTag.equals(e.newTag);
    }
}
