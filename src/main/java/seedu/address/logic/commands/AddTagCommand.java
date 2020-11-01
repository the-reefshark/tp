package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLUMN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUGS;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.Note;
import seedu.address.model.bug.Priority;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;

public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "addTag";

    //TODO Update the DG such that only valid tags are used

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to the"
            + " bug identified by the index number used in the displayed bug list."
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_COLUMN + "COLUMN] "
            + PREFIX_NEWTAG + "NEW_TAG\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NEWTAG + "Ui";

    public static final String MESSAGE_ADD_BUG_SUCCESS = "Added Tag: %1$s";
    public static final String MESSAGE_NOT_ADDED = "Input values cannot be null.";
    public static final String MESSAGE_INVALID_NEW = "The new tag already exists!";

    protected Index index;
    protected Set<Tag> newTags;

    /**
     * Creates a new instance of AddTagCommand.
     *
     * @param index of bug to add the tag to
     * @param newTags to add to the bug
     */
    public AddTagCommand(Index index, Set<Tag> newTags) {
        requireNonNull(index);
        requireNonNull(newTags);
        this.index = index;
        this.newTags = newTags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Bug> lastShownList = model.getFilteredBugList();

        return updateList(lastShownList, model);
    }

    protected CommandResult updateList(List<Bug> lastShownList, Model model) throws CommandException {
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
        }

        Bug bugToEdit = lastShownList.get(index.getZeroBased());
        Bug editedBug = addTagsToBug(bugToEdit, newTags);

        model.setBug(bugToEdit, editedBug);
        model.updateFilteredBugList(PREDICATE_SHOW_ALL_BUGS);
        return new CommandResult(String.format(MESSAGE_ADD_BUG_SUCCESS, editedBug));
    }

    /**
     * Adds a new tag to the specified bug.
     *
     * @param bugToEdit bug to add the new tag
     * @param newTags to add
     * @return updated bug
     * @throws CommandException if the {@code newTag} already exists or the inputs are null
     */
    public static Bug addTagsToBug(Bug bugToEdit, Set<Tag> newTags) throws CommandException {
        if (bugToEdit == null || newTags == null) {
            throw new CommandException(MESSAGE_NOT_ADDED);
        }

        Set<Tag> existingTagSet = bugToEdit.getTags();

        if (existingTagSet.stream().anyMatch(newTags::contains)) {
            throw new CommandException(MESSAGE_INVALID_NEW);
        }

        Name bugName = bugToEdit.getName();
        State bugState = bugToEdit.getState();
        Description bugDescription = bugToEdit.getDescription();
        Set<Tag> updatedTags = addTagsToSet(existingTagSet, newTags);
        Priority bugPriority = bugToEdit.getPriority();
        Optional<Note> updatedNote = bugToEdit.getOptionalNote();

        return new Bug(bugName, bugState, bugDescription, updatedNote, updatedTags, bugPriority);
    }

    private static Set<Tag> addTagsToSet(Set<Tag> existingTagSet, Set<Tag> newTags) {
        Set<Tag> setCopy = new HashSet<>(existingTagSet);
        setCopy.addAll(newTags);
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
                       && newTags.equals(e.newTags);
    }
}
