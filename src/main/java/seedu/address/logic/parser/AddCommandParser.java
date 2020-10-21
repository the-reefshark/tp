package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bug.*;
import seedu.address.model.tag.Tag;

import javax.swing.text.html.Option;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    public static final State DEFAULT_STATE = new State("backlog");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_STATE, PREFIX_DESCRIPTION, PREFIX_NOTE,
                        PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        State state;
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Optional<Note> optionalNote = Optional.empty();
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        if (arePrefixesPresent(argMultimap, PREFIX_STATE)) {
            state = ParserUtil.parseState(argMultimap.getValue(PREFIX_STATE).get());
        } else {
            state = DEFAULT_STATE;
        }

        if (arePrefixesPresent(argMultimap, PREFIX_NOTE)
                && !Note.isValidNote(argMultimap.getValue(PREFIX_NOTE).get())) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }

        if (arePrefixesPresent(argMultimap, PREFIX_NOTE)) {
            optionalNote = Optional.of(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
        }

        Bug bug = new Bug(name, state, description, optionalNote, tagList);
        return new AddCommand(bug);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
