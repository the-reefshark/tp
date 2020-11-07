package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLUMN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLDTAG;
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

public class EditTagCommand extends Command {

    public static final String COMMAND_WORD = "editTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the tags of "
            + "the bug identified by the index number used in the displayed bug list."
            + "The existing tag supplied by the user will be replaced with the new tag given"
            + "as input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_COLUMN + "]"
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
        Bug editedBug = new EditBugTagsDescriptor(bugToEdit, oldTag, newTag).updateTagInBug();
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

    public static class EditBugTagsDescriptor {
        private Bug bugToEdit;
        private Tag oldTag;
        private Tag newTag;

        /**
         * Creates an EditBugTagsDescriptor object with the given input.
         *
         * @param bugToEdit the bug that is to be edited
         * @param oldTag in the bug to be edited
         * @param newTag to replace the old tag
         */
        public EditBugTagsDescriptor(Bug bugToEdit, Tag oldTag, Tag newTag) {
            requireAllNonNull(bugToEdit, oldTag, newTag);
            this.bugToEdit = bugToEdit;
            this.oldTag = oldTag;
            this.newTag = newTag;
        }

        /**
         * Updates the old tag in the specified bug and replaces it with the new tag.
         *
         * @return updated bug
         * @throws CommandException if {@code oldTag} does not exist, the {@code newTag} already exists or the inputs
         * are null
         */
        public Bug updateTagInBug() throws CommandException {

            Set<Tag> existingTagsOfBug = bugToEdit.getTags();
            ensureEditIsValid(existingTagsOfBug, oldTag, newTag);
            Set<Tag> updatedTagsOfBug = updateTagSet(existingTagsOfBug);

            return duplicateBugWithNewTagSet(updatedTagsOfBug);
        }

        private Bug duplicateBugWithNewTagSet(Set<Tag> newTagSet) {
            Name bugName = bugToEdit.getName();
            State bugState = bugToEdit.getState();
            Description bugDescription = bugToEdit.getDescription();
            Priority bugPriority = bugToEdit.getPriority();
            Optional<Note> updatedNote = bugToEdit.getOptionalNote();

            return new Bug(bugName, bugState, bugDescription, updatedNote, newTagSet, bugPriority);
        }

        private Set<Tag> updateTagSet(Set<Tag> existingTagsOfBug) {
            assert existingTagsOfBug.contains(oldTag);
            assert !existingTagsOfBug.contains(newTag);

            Set<Tag> setCopy = new HashSet<>(existingTagsOfBug);
            setCopy.remove(oldTag);
            setCopy.add(newTag);
            return setCopy;
        }

        private void ensureEditIsValid(Set<Tag> existingTagsOfBug, Tag oldTag, Tag newTag) throws CommandException {

            //Ensure that the bug contains the tag to be updated
            if (!existingTagsOfBug.contains(oldTag)) {
                throw new CommandException((MESSAGE_INVALID_OLD));
            }

            //Ensure that the update will not result in a duplicate tag
            if (existingTagsOfBug.contains(newTag)) {
                throw new CommandException((MESSAGE_INVALID_NEW));
            }
        }
    }
}
