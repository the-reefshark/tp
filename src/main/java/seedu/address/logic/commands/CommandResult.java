package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean isHelp;

    /** The application should exit. */
    private final boolean isExit;

    /** The applicaton should switch view. */
    private final boolean isSwitch;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean isHelp, boolean isExit, boolean isSwitch) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.isHelp = isHelp;
        this.isExit = isExit;
        this.isSwitch = isSwitch;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isHelp() {
        return isHelp;
    }

    public boolean isExit() {
        return isExit;
    }

    public boolean isSwitch() {
        return isSwitch;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && isHelp == otherCommandResult.isHelp
                && isExit == otherCommandResult.isExit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, isHelp, isExit);
    }

}
