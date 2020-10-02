package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Bug}.
 */
class JsonAdaptedBug {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Bug's %s field is missing!";

    private final String name;
    private final String email;
    private final String description;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given bug details.
     */
    @JsonCreator
    public JsonAdaptedBug(@JsonProperty("name") String name,
            @JsonProperty("email") String email, @JsonProperty("description") String description,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.email = email;
        this.description = description;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Bug} into this class for Jackson use.
     */
    public JsonAdaptedBug(Bug source) {
        name = source.getName().fullName;
        email = source.getState().value;
        description = source.getDescription().value;
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
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, State.class.getSimpleName()));
        }
        if (!State.isValidEmail(email)) {
            throw new IllegalValueException(State.MESSAGE_CONSTRAINTS);
        }
        final State modelState = new State(email);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Bug(modelName, modelState, modelDescription, modelTags);
    }

}
