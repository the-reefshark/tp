package seedu.address.model.bug.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateBugException extends RuntimeException {
    public DuplicateBugException() {
        super("Operation would result in duplicate persons");
    }
}
