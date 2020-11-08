package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_PROVIDE_COLUMN;
import static seedu.address.commons.core.Messages.MESSAGE_REMOVE_COLUMN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLUMN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATE;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.MoveByStateCommand;
import seedu.address.logic.commands.MoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.bug.State;

/**
 * Parses input arguments and creates a new MoveCommand object
 */
public class MoveCommandParser implements Parser<MoveCommand> {

    private static final Logger LOGGER = LogsCenter.getLogger(SearchCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the MoveCommand
     * and returns an MoveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MoveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        LOGGER.info("---[MOVE COMMAND PARSER][Parse: " + args + "]---");

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STATE, PREFIX_COLUMN);

        String preambleIndex = argMultimap.getPreamble();
        ensureValidIndex(preambleIndex);

        Index index = getIndex(preambleIndex);
        State state = getState(argMultimap, PREFIX_STATE);


        if (argMultimap.getValue(PREFIX_COLUMN).isPresent()) {
            ensureKanbanView();
            State targetState = getState(argMultimap, PREFIX_COLUMN);
            return new MoveByStateCommand(index, state, targetState);
        }

        ensureListView();
        return new MoveCommand(index, state);
    }

    /**
     * Ensures the current window is List view.
     * @throws ParseException if it is the Kanban view.
     */
    private void ensureListView() throws ParseException {
        if (ModelManager.isKanban()) {
            throw new ParseException(MESSAGE_PROVIDE_COLUMN);
        }
    }

    /**
     * Ensures the current window is Kanban view.
     * @throws ParseException if it is the List view.
     */
    private void ensureKanbanView() throws ParseException {
        if (!ModelManager.isKanban()) {
            throw new ParseException(MESSAGE_REMOVE_COLUMN);
        }
    }

    /**
     * Returns the valid state value.
     * @param argMultimap argument multimap is used to get the arguments of the prefix.
     * @param prefix the prefix type.
     * @throws ParseException if the state is invalid.
     */
    private State getState(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        if (!arePrefixesPresent(argMultimap, prefix)) {
            LOGGER.info("---[MOVE COMMAND PARSER][Cannot find sufficient number of prefixes]---");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE));
        }

        return ParserUtil.parseState(argMultimap.getValue(prefix).get());
    }

    /**
     * Returns valid index.
     * @param preambleIndex the preamble parsed for index.
     * @throws ParseException if the {@code preambleIndex} is not a valid index.
     */
    private Index getIndex(String preambleIndex) throws ParseException {
        try {
            return ParserUtil.parseIndex(preambleIndex);
        } catch (ParseException pe) {
            LOGGER.info("---[MOVE COMMAND PARSER][The index is invalid]---");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Ensures the preamble index is a number and not overflowed.
     * @param preambleIndex the preamble parsed for index.
     * @throws ParseException if the {@code preambleIndex} is not a number or overflowed.
     */
    private void ensureValidIndex(String preambleIndex) throws ParseException {
        if (!StringUtil.isNumber(preambleIndex)) {
            LOGGER.info("---[MOVE COMMAND PARSER][The preamble index is not a number]---");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE));
        }

        if (StringUtil.isIndexOverflow(preambleIndex)) {
            LOGGER.info("---[MOVE COMMAND PARSER][The preamble index is overflowed]---");
            throw new ParseException(Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
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

