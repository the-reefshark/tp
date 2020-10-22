package seedu.address.storage;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.Priority;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;

import javax.swing.text.html.Option;

/**
 * Jackson-friendly version of {@link Bug}.
 */
class JsonAdaptedBug {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Bug's %s field is missing!";

    private final String name;
    private final String state;
    private final String description;
    private final String note;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String priority;

    /**
     * Constructs a {@code JsonAdaptedBug} with the given bug details.
     */
    @JsonCreator
    public JsonAdaptedBug(@JsonProperty("name") String name,
                          @JsonProperty("state") String state,
                          @JsonProperty("description") String description,
                          @JsonProperty("note") String note,
                          @JsonProperty("priority") String priority,
                          @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.state = state;
        this.description = description;
        this.note = note;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.priority = priority;
    }

    /**
     * Converts a given {@code Bug} into this class for Jackson use.
     */
    public JsonAdaptedBug(Bug source) {
        name = source.getName().fullName;
        state = source.getState().toString();
        description = source.getDescription().value;

        Optional<Note> note = source.getOptionalNote();
        this.note = note.isPresent() ? note.get().value : "";

        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        priority = source.getPriority().getValue();
    }

    /**
     * Converts this Jackson-friendly adapted bug object into the model's {@code Bug} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted bug.
     */
    public Bug toModelType() throws IllegalValueException {
        final List<Tag> bugTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            bugTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (state == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, State.class.getSimpleName()));
        }
        if (!State.isValidState(state)) {
            throw new IllegalValueException(State.MESSAGE_CONSTRAINTS);
        }
        final State modelState = new State(state);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriority(priority) && !priority.equals(Priority.EMPTY_PRIORITY)) {
            throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
        }
        final Priority modelPriority = priority.equals(Priority.EMPTY_PRIORITY)
                ? new Priority() : new Priority(priority);

        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Note.class.getSimpleName()));
        }

        final Optional<Note> modelOptionalNote = Optional.of(new Note(note));

        final Set<Tag> modelTags = new HashSet<>(bugTags);
        return new Bug(modelName, modelState, modelDescription, modelOptionalNote, modelTags, modelPriority);
    }

}
