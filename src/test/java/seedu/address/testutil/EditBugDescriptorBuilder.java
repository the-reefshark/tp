package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.model.bug.Address;
import seedu.address.model.bug.Email;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.Phone;
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
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditBugDescriptorBuilder(Bug bug) {
        descriptor = new EditCommand.EditBugDescriptor();
        descriptor.setName(bug.getName());
        descriptor.setPhone(bug.getPhone());
        descriptor.setEmail(bug.getEmail());
        descriptor.setAddress(bug.getAddress());
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
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBugDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
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
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBugDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
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
