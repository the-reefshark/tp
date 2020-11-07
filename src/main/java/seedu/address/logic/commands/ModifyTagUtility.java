package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.EditTagCommand.MESSAGE_INVALID_NEW;
import static seedu.address.logic.commands.EditTagCommand.MESSAGE_INVALID_OLD;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.Note;
import seedu.address.model.bug.Priority;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;

public class ModifyTagUtility {
    private Bug bugToEdit;

    /**
     * Creates an EditBugTagsDescriptor object with the given input.
     *
     * @param bugToEdit the bug that is to be edited
     */
    public ModifyTagUtility(Bug bugToEdit) {
        requireAllNonNull(bugToEdit);
        this.bugToEdit = bugToEdit;

    }

    /**
     * Updates the old tag in the specified bug and replaces it with the new tag.
     *
     * @return updated bug
     * @throws CommandException if {@code oldTag} does not exist, the {@code newTag} already exists or the inputs
     * are null
     */
    public Bug updateTagInBug(Tag oldTag, Tag newTag) throws CommandException {

        Set<Tag> existingTagsOfBug = bugToEdit.getTags();
        ensureEditIsValid(existingTagsOfBug, oldTag, newTag);
        Set<Tag> updatedTagsOfBug = updateTagSet(existingTagsOfBug, oldTag, newTag);

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

    private Set<Tag> updateTagSet(Set<Tag> existingTagsOfBug, Tag oldTag, Tag newTag) {
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

    /**
     * Adds a new tag to the specified bug.
     *
     * @return updated bug
     * @throws CommandException if the {@code newTag} already exists or the inputs are null
     */
    public Bug addTagsToBug(Set<Tag> newTagsToAdd) throws CommandException {

        Set<Tag> existingTagSet = bugToEdit.getTags();
        ensureNoDuplicateTags(newTagsToAdd, existingTagSet);

        return duplicateBugWithAddedTags(bugToEdit, newTagsToAdd);
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
    private Bug duplicateBugWithAddedTags(Bug bugToDuplicate, Set<Tag> newTagsToAdd) {
        Name bugName = bugToDuplicate.getName();
        State bugState = bugToDuplicate.getState();
        Set<Tag> existingTagSet = bugToDuplicate.getTags();
        Description bugDescription = bugToDuplicate.getDescription();
        Priority bugPriority = bugToDuplicate.getPriority();
        Optional<Note> updatedNote = bugToDuplicate.getOptionalNote();
        Set<Tag> updatedTags = addTagsToSet(existingTagSet, newTagsToAdd);

        return new Bug(bugName, bugState, bugDescription, updatedNote, updatedTags, bugPriority);
    }

    private Set<Tag> addTagsToSet(Set<Tag> existingTagSet, Set<Tag> newTags) {
        Set<Tag> setCopy = new HashSet<>(existingTagSet);
        setCopy.addAll(newTags);
        return setCopy;
    }
}
