package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLUMN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUGS;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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
    protected static final Logger LOGGER = LogsCenter.getLogger(AddTagCommand.class);

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
        LOGGER.info("----------------[ADD TAG COMMAND][Bug at index: " + index.getZeroBased()
                            + ". Attempting to add " + newTags.size() + "tags.");

        List<Bug> lastShownList = model.getFilteredBugList();

        return updateList(lastShownList, model);
    }

    protected CommandResult updateList(List<Bug> lastShownList, Model model) throws CommandException {
        ensureValidIndex(index, lastShownList);

        Bug bugToEdit = lastShownList.get(index.getZeroBased());
        Bug editedBug = addTagsToBug(bugToEdit, newTags);
        model.setBug(bugToEdit, editedBug);
        model.updateFilteredBugList(PREDICATE_SHOW_ALL_BUGS);

        LOGGER.info("----------------[ADD TAG COMMAND][Updated bug at index: " + index.getZeroBased()
                            + ". Successfully added " + newTags.size() + "tags.");

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
    private Bug addTagsToBug(Bug bugToEdit, Set<Tag> newTags) throws CommandException {
        if (bugToEdit == null || newTags == null) {
            throw new CommandException(MESSAGE_NOT_ADDED);
        }

        Set<Tag> existingTagSet = bugToEdit.getTags();
        ensureNoDuplicateTags(newTags, existingTagSet);

        return duplicateBugWithAddedTags(bugToEdit);
    }

    /**
     * Compares both sets and ensures that no duplicate tags exist.
     *
     * @param newTags Set of new tags
     * @param existingTagSet Set of existing tags in bug
     * @throws CommandException if a duplicate tag exists
     */
    private void ensureNoDuplicateTags(Set<Tag> newTags, Set<Tag> existingTagSet) throws CommandException {
        if (existingTagSet.stream().anyMatch(newTags::contains)) {
            throw new CommandException(MESSAGE_INVALID_NEW);
        }
    }

    /**
     * Duplicates bug given as input and adds all tags in the set of new tags to it.
     *
     * @param bugToDuplicate Bug to be duplicated
     * @return Returns a copy of the duplicated bug with all the new tags added to its tag set
     */
    private Bug duplicateBugWithAddedTags(Bug bugToDuplicate) {
        Name bugName = bugToDuplicate.getName();
        State bugState = bugToDuplicate.getState();
        Set<Tag> existingTagSet = bugToDuplicate.getTags();
        Description bugDescription = bugToDuplicate.getDescription();
        Priority bugPriority = bugToDuplicate.getPriority();
        Optional<Note> updatedNote = bugToDuplicate.getOptionalNote();
        Set<Tag> updatedTags = addTagsToSet(existingTagSet, newTags);

        return new Bug(bugName, bugState, bugDescription, updatedNote, updatedTags, bugPriority);
    }

    private Set<Tag> addTagsToSet(Set<Tag> existingTagSet, Set<Tag> newTags) {
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
