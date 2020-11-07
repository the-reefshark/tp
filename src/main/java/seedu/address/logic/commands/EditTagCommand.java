package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLUMN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLDTAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUGS;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.bug.Bug;
import seedu.address.model.tag.Tag;

public class EditTagCommand extends Command {

    public static final String COMMAND_WORD = "editTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the tags of "
            + "the bug identified by the index number used in the displayed bug list. "
            + "The existing tag supplied by the user will be replaced with the new tag given "
            + "as input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_COLUMN + "] "
            + PREFIX_OLDTAG + "OLD_TAG "
            + PREFIX_NEWTAG + "NEW_TAG\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_OLDTAG + "display " + PREFIX_NEWTAG + "Ui";

    public static final String MESSAGE_EDIT_BUG_SUCCESS = "Edited Tag: %1$s";
    public static final String MESSAGE_INVALID_OLD = "A valid existing tag must be supplied.";
    public static final String MESSAGE_INVALID_NEW = "The new tag already exists!";
    protected static final Logger LOGGER = LogsCenter.getLogger(EditTagCommand.class);

    protected Index index;
    protected Tag oldTag;
    protected Tag newTag;

    /**
     * Creates a new instance of an EditTagCommand with the appropriate {@code index},
     * {@code oldTag} and {@code newTag}.
     *
     * @param index of the bug in the filtered bug list to edit
     * @param oldTag to be modified
     * @param newTag to replace old tag
     */
    public EditTagCommand(Index index, Tag oldTag, Tag newTag) {
        requireNonNull(index);
        requireNonNull(oldTag);
        requireNonNull(newTag);
        this.index = index;
        this.oldTag = oldTag;
        this.newTag = newTag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        LOGGER.info("----------------[EDIT TAG COMMAND][Bug at index: " + index.getZeroBased()
                            + ". Attempting to change tag from " + oldTag + " to " + newTag);

        List<Bug> lastShownList = model.getFilteredBugList();

        return updateList(lastShownList, model);
    }

    /**
     * Updates list of bugs to reflect updated tags of bug.
     *
     * @param lastShownList current list of bugs
     * @param model {@code Model} which the command should operate on
     * @return feedback message of the operation result for display
     * @throws CommandException if update is invalid
     */
    protected CommandResult updateList(List<Bug> lastShownList, Model model) throws CommandException {
        ensureValidIndex(index, lastShownList);

        Bug bugToEdit = lastShownList.get(index.getZeroBased());
        Bug editedBug = new ModifyTagUtility(bugToEdit).updateTagInBug(oldTag, newTag);
        model.setBug(bugToEdit, editedBug);
        model.updateFilteredBugList(PREDICATE_SHOW_ALL_BUGS);

        LOGGER.info("----------------[EDIT TAG COMMAND][Updated bug at index: " + index.getZeroBased()
                            + ". Successfully updated tag from " + oldTag + " to " + newTag);

        return new CommandResult(String.format(MESSAGE_EDIT_BUG_SUCCESS, editedBug));
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
