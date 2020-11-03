package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_PROVIDE_COLUMN;
import static seedu.address.commons.core.Messages.MESSAGE_REMOVE_COLUMN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLUMN;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteByStateCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ColumnNotFoundException;
import seedu.address.logic.parser.exceptions.ExtraColumnException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.bug.State;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COLUMN);
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            if (arePrefixesPresent(argMultimap, PREFIX_COLUMN)) {
                if (!ModelManager.isKanban()) {
                    throw new ExtraColumnException();
                }
                State targetState = ParserUtil.parseState(argMultimap.getValue(PREFIX_COLUMN).get());
                return new DeleteByStateCommand(index, targetState);
            }
            if (ModelManager.isKanban()) {
                throw new ColumnNotFoundException();
            }
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        } catch (ExtraColumnException ece) {
            throw new ParseException(MESSAGE_REMOVE_COLUMN, ece);
        } catch (ColumnNotFoundException ce) {
            throw new ParseException(MESSAGE_PROVIDE_COLUMN, ce);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
