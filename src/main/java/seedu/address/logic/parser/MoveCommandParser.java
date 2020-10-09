package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bug.State;

/**
 * Parses input arguments and creates a new MoveCommand object
 */
public class MoveCommandParser implements Parser<MoveCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MoveCommand
     * and returns an MoveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MoveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_STATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE));
        }

        State state = ParserUtil.parseState(argMultimap.getValue(PREFIX_STATE).get());

        return new MoveCommand(index, state);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

