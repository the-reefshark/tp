package seedu.address.storage;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.bug.*;
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
    private final Optional<String> optionalNote;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBug} with the given bug details.
     */
    @JsonCreator
    public JsonAdaptedBug(@JsonProperty("name") String name,
                          @JsonProperty("email") String state,
                          @JsonProperty("description") String description,
                          @JsonProperty("note") String note,
                          @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.state = state;
        this.description = description;
        this.optionalNote = Optional.ofNullable(note);
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Bug} into this class for Jackson use.
     */
    public JsonAdaptedBug(Bug source) {
        name = source.getName().fullName;
        state = source.getState().toString();
        description = source.getDescription().value;

        Optional<Note> note = source.getOptionalNote();
        optionalNote = note.isPresent() ? Optional.of(note.get().value) : Optional.empty();

        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
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

        if (optionalNote == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Note.class.getSimpleName()));
        }
        if (optionalNote.isPresent() && !Note.isValidNote(optionalNote.get())) {
            throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
        }

        final Optional<Note> modelOptionalNote = optionalNote.map(Note::new);

        final Set<Tag> modelTags = new HashSet<>(bugTags);
        return new Bug(modelName, modelState, modelDescription, modelOptionalNote, modelTags);
    }

}
