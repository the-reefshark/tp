package seedu.address.model.bug;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Priority {
    public static final String MESSAGE_CONSTRAINTS = "Priority level should only be either low, medium or high.";
    public static final String EMPTY_PRIORITY = "No priority indicated";

    private static final String LOW_REGEX = "((?i)\\blow\\b)";
    private static final String MEDIUM_REGEX = "((?i)\\bmedium\\b)";
    private static final String HIGH_REGEX = "((?i)\\bhigh\\b)";
    public static final String VALIDATION_REGEX =
            LOW_REGEX + "|" + MEDIUM_REGEX + "|" + HIGH_REGEX;

    public final String priority;

    public Priority(String priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_CONSTRAINTS);
        this.priority = priority.toLowerCase();
    }

    // represent a null Priority object
    public Priority() {
        priority = Priority.EMPTY_PRIORITY;
    }

    public boolean isNull() {
        return priority.equals(Priority.EMPTY_PRIORITY);
    }

    public static boolean isValidPriority(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return priority;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Priority)
                && this.priority.equals(((Priority) other).priority);
    }

    @Override
    public int hashCode() { return priority.hashCode(); }
}
