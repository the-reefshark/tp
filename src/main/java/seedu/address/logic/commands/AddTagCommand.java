package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLUMN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUGS;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.bug.Bug;
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
                            + ". Attempting to add " + newTags.size() + " tags.]");

        List<Bug> lastShownList = model.getFilteredBugList();

        return updateList(lastShownList, model);
    }

    protected CommandResult updateList(List<Bug> lastShownList, Model model) throws CommandException {
        ensureValidIndex(index, lastShownList);

        Bug bugToEdit = lastShownList.get(index.getZeroBased());
        Bug editedBug = new ModifyTagUtility(bugToEdit).addTagsToBug(newTags);
        model.setBug(bugToEdit, editedBug);
        model.updateFilteredBugList(PREDICATE_SHOW_ALL_BUGS);

        LOGGER.info("----------------[ADD TAG COMMAND][Updated bug at index: " + index.getZeroBased()
                            + ". Successfully added " + newTags.size() + " tags.]");

        return new CommandResult(String.format(MESSAGE_ADD_BUG_SUCCESS, editedBug));
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
