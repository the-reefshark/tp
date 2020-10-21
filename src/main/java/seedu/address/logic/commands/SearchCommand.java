package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUERYSTRING;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.bug.BugContainsQueryStringPredicate;

public class SearchCommand extends Command {
    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Search all bugs whose names/descriptions/tags contain "
            + "the specified query-string (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + PREFIX_QUERYSTRING + "QUERYSTRING\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_QUERYSTRING + " Ui bug";

    public static final String MESSAGE_EMPTY_QUERY_STRING = "The query-string cannot be empty!";
    private final BugContainsQueryStringPredicate predicate;

    public SearchCommand(BugContainsQueryStringPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBugList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_BUGS_LISTED_OVERVIEW, model.getFilteredBugList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && predicate.equals(((SearchCommand) other).predicate)); // state check
    }
}
