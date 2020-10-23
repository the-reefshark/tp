package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUGS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.Priority;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;


/**
 * Edits the details of an existing bug in the bug tracker.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the bug identified "
            + "by the index number used in the displayed bug list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_STATE + "STATE] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_STATE + "todo";

    public static final String MESSAGE_EDIT_BUG_SUCCESS = "Edited Bug: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_BUG = "This bug already exists in the KanBug Tracker.";

    protected final Index index;
    protected final EditBugDescriptor editBugDescriptor;

    /**
     * @param index of the bug in the filtered bug list to edit
     * @param editBugDescriptor details to edit the bug with
     */
    public EditCommand(Index index, EditBugDescriptor editBugDescriptor) {
        requireNonNull(index);
        requireNonNull(editBugDescriptor);

        this.index = index;
        this.editBugDescriptor = new EditBugDescriptor(editBugDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Bug> lastShownList = model.getFilteredBugList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
        }

        Bug bugToEdit = lastShownList.get(index.getZeroBased());
        Bug editedBug = createEditedBug(bugToEdit, editBugDescriptor);

        if (!bugToEdit.isSameBug(editedBug) && model.hasBug(editedBug)) {
            throw new CommandException(MESSAGE_DUPLICATE_BUG);
        }

        model.setBug(bugToEdit, editedBug);
        model.updateFilteredBugList(PREDICATE_SHOW_ALL_BUGS);
        return new CommandResult(String.format(MESSAGE_EDIT_BUG_SUCCESS, editedBug));
    }

    /**
     * Creates and returns a {@code Bug} with the details of {@code bugToEdit}
     * edited with {@code editBugDescriptor}.
     */
    private static Bug createEditedBug(Bug bugToEdit, EditBugDescriptor editBugDescriptor) {
        assert bugToEdit != null;

        Name updatedName = editBugDescriptor.getName().orElse(bugToEdit.getName());
        State updatedState = editBugDescriptor.getState().orElse(bugToEdit.getState());
        Description updatedDescription = editBugDescriptor.getDescription().orElse(bugToEdit.getDescription());
        Set<Tag> updatedTags = editBugDescriptor.getTags().orElse(bugToEdit.getTags());
        Priority updatedPriority = editBugDescriptor.getPriority().orElse(bugToEdit.getPriority());

        return new Bug(updatedName, updatedState, updatedDescription, updatedTags, updatedPriority);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editBugDescriptor.equals(e.editBugDescriptor);
    }

    /**
     * Stores the details to edit the bug with. Each non-empty field value will replace the
     * corresponding field value of the bug.
     */
    public static class EditBugDescriptor {
        private Name name;
        private State state;
        private Description description;
        private Set<Tag> tags;
        private Priority priority;

        public EditBugDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditBugDescriptor(EditBugDescriptor toCopy) {
            setName(toCopy.name);
            setState(toCopy.state);
            setDescription(toCopy.description);
            setTags(toCopy.tags);
            setPriority(toCopy.priority);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, state, description, tags, priority);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setState(State state) {
            this.state = state;
        }

        public Optional<State> getState() {
            return Optional.ofNullable(state);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditBugDescriptor)) {
                return false;
            }

            // state check
            EditBugDescriptor e = (EditBugDescriptor) other;

            return getName().equals(e.getName())
                    && getState().equals(e.getState())
                    && getDescription().equals(e.getDescription())
                    && getPriority().equals(e.getPriority())
                    && getTags().equals(e.getTags());
        }
    }
}
