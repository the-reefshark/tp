package seedu.address.model.bug;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Bug in the bug tracker.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Bug {

    // Identity fields
    private final Name name;
    private final State state;

    // Data fields
    private final Description description;
    private final Optional<Note> optionalNote;
    private final Set<Tag> tags = new HashSet<>();
    private final Priority priority;
    /**
     * Every field must be present and not null.
     **/
    public Bug(Name name, State state, Description description, Optional<Note> optionalNote, Set<Tag> tags,
               Priority priority) {
        requireAllNonNull(name, state, description, tags);
        this.name = name;
        this.state = state;
        this.description = description;
        this.optionalNote = optionalNote;
        this.tags.addAll(tags);
        this.priority = priority;
    }

    public Name getName() {
        return name;
    }

    public State getState() {
        return state;
    }

    public Description getDescription() {
        return description;
    }

    public Optional<Note> getOptionalNote() {
        return optionalNote;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Priority getPriority() {
        return priority;
    }

    /**
     * Returns true if both bugs have the same name.
     * This defines a weaker notion of equality between two bugs.
     */
    public boolean isSameBug(Bug otherBug) {
        if (otherBug == this) {
            return true;
        }

        return otherBug != null
                && otherBug.getName().equals(getName());
    }

    /**
     * Returns true if both bugs have the same identity and data fields.
     * This defines a stronger notion of equality between two bugs.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Bug)) {
            return false;
        }

        Bug otherBug = (Bug) other;
        return otherBug.getName().equals(getName())
                && otherBug.getState().equals(getState())
                && otherBug.getDescription().equals(getDescription())
                && otherBug.getPriority().equals(getPriority())
                && otherBug.getOptionalNote().equals(getOptionalNote())
                && otherBug.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, state, description, optionalNote, tags, priority);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" State: ")
                .append(getState())
                .append(" Description: ")
                .append(getDescription());

        if (getOptionalNote() != null && getOptionalNote().isPresent()) {
            builder.append(" Note: ")
                   .append(getOptionalNote().get().toString());
        }
        if (!getPriority().isNull()) {
            builder.append(" Priority: ")
                   .append(getPriority());
        }
        if (!tags.isEmpty()) {
            builder.append(" Tags: ");
            getTags().forEach(builder::append);
        }
        return builder.toString();
    }

    public boolean compareState(State state) {
        return this.state.equals(state);
    }
}
