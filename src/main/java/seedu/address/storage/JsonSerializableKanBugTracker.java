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
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableKanBugTracker {

    public static final String MESSAGE_DUPLICATE_BUG = "Bugs list contains duplicate bug(s).";

    private final List<JsonAdaptedBug> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given bugs.
     */
    @JsonCreator
    public JsonSerializableKanBugTracker(@JsonProperty("persons") List<JsonAdaptedBug> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableKanBugTracker(ReadOnlyKanBugTracker source) {
        persons.addAll(source.getBugList().stream().map(JsonAdaptedBug::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public KanBugTracker toModelType() throws IllegalValueException {
        KanBugTracker kanBugTracker = new KanBugTracker();
        for (JsonAdaptedBug jsonAdaptedBug : persons) {
            Bug bug = jsonAdaptedBug.toModelType();
            if (kanBugTracker.hasBug(bug)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BUG);
            }
            kanBugTracker.addBug(bug);
        }
        return kanBugTracker;
    }

}
