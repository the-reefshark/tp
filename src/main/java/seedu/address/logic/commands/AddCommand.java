package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.bug.Bug;

/**
 * Adds a bug to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a bug to the KanBug Tracker. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_STATE + "STATE] "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Array size error "
            + PREFIX_STATE + "backlog "
            + PREFIX_DESCRIPTION + "ArrayStoreException due to array being too small "
            + PREFIX_TAG + "Array "
            + PREFIX_TAG + "Size ";

    public static final String MESSAGE_SUCCESS = "New bug added: %1$s";
    public static final String MESSAGE_DUPLICATE_BUG = "This bug already exists in the KanBug Tracker";

    private final Bug toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Bug}
     */
    public AddCommand(Bug bug) {
        requireNonNull(bug);
        toAdd = bug;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasBug(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BUG);
        }

        model.addBug(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
