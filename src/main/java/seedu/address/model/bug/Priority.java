package seedu.address.model.bug;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Priority {
    public static final String MESSAGE_CONSTRAINTS = "Priority level should only be either low, medium or high.";

    private static final String LOW_REGEX = "((?i)\\blow\\b)";
    private static final String MEDIUM_REGEX = "((?i)\\bmedium\\b)";
    private static final String HIGH_REGEX = "((?i)\\bhigh\\b)";
    public static final String VALIDATION_REGEX =
            LOW_REGEX + "|" + MEDIUM_REGEX + "|" + HIGH_REGEX;

    private String priorityLevel;

    public Priority(String priorityLevel) {
        requireNonNull(priorityLevel);
        checkArgument(isValidPriority(priorityLevel), MESSAGE_CONSTRAINTS);
        this.priorityLevel = priorityLevel.toLowerCase();
    }

    public static boolean isValidPriority(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return priorityLevel;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Priority)
                && this.priorityLevel.equals(((Priority) other).priorityLevel);
    }

    @Override
    public int hashCode() { return priorityLevel.hashCode(); }
}
