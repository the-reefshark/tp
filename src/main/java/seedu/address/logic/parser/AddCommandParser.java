package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.Priority;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;

import javax.swing.text.html.Option;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    public static final State DEFAULT_STATE = new State("backlog");
    public static final Priority DEFAULT_PRIORITY = new Priority();

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_STATE, PREFIX_DESCRIPTION, PREFIX_NOTE,
                        PREFIX_TAG, PREFIX_PRIORITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
      
        assert argMultimap.getValue(PREFIX_NAME).isPresent();
        assert argMultimap.getValue(PREFIX_DESCRIPTION).isPresent();
      
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        State state;
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Optional<Note> optionalNote = Optional.empty();
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Priority priority;

        if (arePrefixesPresent(argMultimap, PREFIX_STATE)) {
            assert argMultimap.getValue(PREFIX_STATE).isPresent();
            state = ParserUtil.parseState(argMultimap.getValue(PREFIX_STATE).get());
        } else {
            assert argMultimap.getValue(PREFIX_STATE).isEmpty();
            state = DEFAULT_STATE;
        }

        if (arePrefixesPresent(argMultimap, PREFIX_PRIORITY)) {
            priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
        } else {
            priority = DEFAULT_PRIORITY;
        }

        if (arePrefixesPresent(argMultimap, PREFIX_NOTE)
                && !Note.isValidNote(argMultimap.getValue(PREFIX_NOTE).get())) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }

        if (arePrefixesPresent(argMultimap, PREFIX_NOTE)) {
            optionalNote = Optional.of(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
        }

        Bug bug = new Bug(name, state, description, optionalNote, tagList, priority);
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
