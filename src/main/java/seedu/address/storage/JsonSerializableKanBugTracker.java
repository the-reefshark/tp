package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.KanBugTracker;
import seedu.address.model.ReadOnlyKanBugTracker;
import seedu.address.model.bug.Bug;

/**
 * An Immutable KanBugTracker that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableKanBugTracker {

    public static final String MESSAGE_DUPLICATE_BUG = "Bugs list contains duplicate bug(s).";

    private final List<JsonAdaptedBug> bugs = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableKanBugTracker} with the given bugs.
     */
    @JsonCreator
    public JsonSerializableKanBugTracker(@JsonProperty("bugs") List<JsonAdaptedBug> bugs) {
        this.bugs.addAll(bugs);
    }

    /**
     * Converts a given {@code ReadOnlyKanBugTracker} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableKanBugTracker}.
     */
    public JsonSerializableKanBugTracker(ReadOnlyKanBugTracker source) {
        bugs.addAll(source.getBugList().stream().map(JsonAdaptedBug::new).collect(Collectors.toList()));
    }

    /**
     * Converts this KanBug tracker into the model's {@code KanBugTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public KanBugTracker toModelType() throws IllegalValueException {
        KanBugTracker kanBugTracker = new KanBugTracker();
        for (JsonAdaptedBug jsonAdaptedBug : bugs) {
            Bug bug = jsonAdaptedBug.toModelType();
            if (kanBugTracker.hasBug(bug)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BUG);
            }
            kanBugTracker.addBug(bug);
        }
        return kanBugTracker;
    }

}
