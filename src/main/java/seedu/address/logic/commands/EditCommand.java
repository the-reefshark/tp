package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
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
import seedu.address.model.bug.Email;
import seedu.address.model.bug.Name;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing bug in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the bug identified "
            + "by the index number used in the displayed bug list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Bug: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This bug already exists in the address book.";

    private final Index index;
    private final EditBugDescriptor editBugDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editBugDescriptor details to edit the person with
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
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Bug bugToEdit = lastShownList.get(index.getZeroBased());
        Bug editedBug = createEditedPerson(bugToEdit, editBugDescriptor);

        if (!bugToEdit.isSamePerson(editedBug) && model.hasBug(editedBug)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setBug(bugToEdit, editedBug);
        model.updateFilteredBugList(PREDICATE_SHOW_ALL_BUGS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedBug));
    }

    /**
     * Creates and returns a {@code Bug} with the details of {@code bugToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Bug createEditedPerson(Bug bugToEdit, EditBugDescriptor editBugDescriptor) {
        assert bugToEdit != null;

        Name updatedName = editBugDescriptor.getName().orElse(bugToEdit.getName());
        Email updatedEmail = editBugDescriptor.getEmail().orElse(bugToEdit.getEmail());
        Description updatedDescription = editBugDescriptor.getDescription().orElse(bugToEdit.getDescription());
        Set<Tag> updatedTags = editBugDescriptor.getTags().orElse(bugToEdit.getTags());

        return new Bug(updatedName, updatedEmail, updatedDescription, updatedTags);
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
        private Email email;
        private Description description;
        private Set<Tag> tags;

        public EditBugDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditBugDescriptor(EditBugDescriptor toCopy) {
            setName(toCopy.name);
            setEmail(toCopy.email);
            setDescription(toCopy.description);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, email, description, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
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
                    && getEmail().equals(e.getEmail())
                    && getDescription().equals(e.getDescription())
                    && getTags().equals(e.getTags());
        }
    }
}
