package seedu.address.model.bug;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Bug's state in the bug tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidState(String)}
 */
public class State {

    public enum Value {
        BACKLOG,
        TODO,
        ONGOING,
        DONE
    }

    public static final String MESSAGE_CONSTRAINTS = "States should be of the four allowed states:\n"
            + "1. backlog\n"
            + "2. todo\n"
            + "3. ongoing\n"
            + "4. done\n";

    private static final String BACKLOG_REGEX = "((?i)\\bbacklog\\b)";
    private static final String TODO_REGEX = "((?i)\\btodo\\b)";
    private static final String ONGOING_REGEX = "((?i)\\bongoing\\b)";
    private static final String DONE_REGEX = "((?i)\\bdone\\b)";
    public static final String VALIDATION_REGEX =
            BACKLOG_REGEX + "|" + TODO_REGEX + "|" + ONGOING_REGEX + "|" + DONE_REGEX;

    public final Value value;

    /**
     * Constructs an {@code State}.
     *
     * @param state A valid state address.
     */
    public State(String state) {
        requireNonNull(state);
        checkArgument(isValidState(state), MESSAGE_CONSTRAINTS);
        value = getValueOfState(state.toLowerCase());
    }

    /**
     * Returns if a given string is a valid state.
     */
    public static boolean isValidState(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public Value getValueOfState(String state) {
        switch (state) {
        case "backlog" :
            return Value.BACKLOG;
        case "todo":
            return Value.TODO;
        case "ongoing":
            return Value.ONGOING;
        case "done":
            return Value.DONE;
        default:
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    public String getStringOfValue() {
        switch (value) {
        case BACKLOG:
            return "backlog";
        case TODO:
            return "todo";
        case ONGOING:
            return "ongoing";
        case DONE:
            return "done";
        default:
            throw new IllegalArgumentException("Illegal state object!");
        }
    }

    @Override
    public String toString() {
        return getStringOfValue();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof State // instanceof handles nulls
                && value.equals(((State) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
