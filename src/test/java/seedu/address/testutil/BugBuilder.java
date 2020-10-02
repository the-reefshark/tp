package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.bug.Bug;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Bug objects.
 */
public class BugBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_DESCRIPTION = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private State state;
    private Description description;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public BugBuilder() {
        name = new Name(DEFAULT_NAME);
        state = new State(DEFAULT_EMAIL);
        description = new Description(DEFAULT_DESCRIPTION);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code bugToCopy}.
     */
    public BugBuilder(Bug bugToCopy) {
        name = bugToCopy.getName();
        state = bugToCopy.getState();
        description = bugToCopy.getDescription();
        tags = new HashSet<>(bugToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Bug} that we are building.
     */
    public BugBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Bug} that we are building.
     */
    public BugBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Bug} that we are building.
     */
    public BugBuilder withDescription(String address) {
        this.description = new Description(address);
        return this;
    }

    /**
     * Sets the {@code State} of the {@code Bug} that we are building.
     */
    public BugBuilder withEmail(String email) {
        this.state = new State(email);
        return this;
    }

    public Bug build() {
        return new Bug(name, state, description, tags);
    }

}
