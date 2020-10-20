package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUGS;

import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;
import seedu.address.model.Model;
import seedu.address.model.bug.NameContainsKeywordsPredicate;

public class SearchCommand extends Command {
    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_SUCCESS = "Searched all bugs containing keyword as a substring";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Search all bugs whose names/descriptions/tags contain "
            + "the specified keyword (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + PREFIX_KEYWORD + "KEYWORD\n"
            + "Example: " + COMMAND_WORD + " Incorrect display";

    private final NameContainsKeywordsPredicate predicate;

    public SearchCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBugList(PREDICATE_SHOW_ALL_BUGS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && predicate.equals(((SearchCommand) other).predicate)); // state check
    }
}
