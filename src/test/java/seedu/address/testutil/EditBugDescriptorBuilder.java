package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Email;
import seedu.address.model.bug.Name;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditBugDescriptorBuilder {

    private EditCommand.EditBugDescriptor descriptor;

    public EditBugDescriptorBuilder() {
        descriptor = new EditCommand.EditBugDescriptor();
    }

    public EditBugDescriptorBuilder(EditCommand.EditBugDescriptor descriptor) {
        this.descriptor = new EditCommand.EditBugDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code bug}'s details
     */
    public EditBugDescriptorBuilder(Bug bug) {
        descriptor = new EditCommand.EditBugDescriptor();
        descriptor.setName(bug.getName());
        descriptor.setEmail(bug.getEmail());
        descriptor.setDescription(bug.getDescription());
        descriptor.setTags(bug.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBugDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBugDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBugDescriptorBuilder withDescription(String address) {
        descriptor.setDescription(new Description(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditBugDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditBugDescriptor build() {
        return descriptor;
    }
}
