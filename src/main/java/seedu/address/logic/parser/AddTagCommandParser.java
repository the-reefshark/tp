package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLUMN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagByStateCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;

public class AddTagCommandParser implements Parser<AddTagCommand> {

    private static final int NUMBER_OF_PREFIXES_EXPECTED = 2;
    private static final int NUMBER_OF_NEWTAG = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the EditTagCommand
     * and returns an EditTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NEWTAG, PREFIX_COLUMN);

        Index index;
        Tag newTag;
        int numberOfPrefixesExpected = NUMBER_OF_PREFIXES_EXPECTED;
        if (arePrefixesPresent(argMultimap, PREFIX_COLUMN)) {
            numberOfPrefixesExpected++;
        }
        boolean hasExtraPrefixes = argMultimap.getSize() != numberOfPrefixesExpected;
        boolean hasIncorrectNumberOfPrefixValues =
                argMultimap.numberOfPrefixElements(PREFIX_NEWTAG) != NUMBER_OF_NEWTAG;

        if (!arePrefixesPresent(argMultimap, PREFIX_NEWTAG)
                || argMultimap.getPreamble().isEmpty() || hasExtraPrefixes || hasIncorrectNumberOfPrefixValues) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }
        assert argMultimap.getValue(PREFIX_NEWTAG).isPresent();

        index = ParserUtil.parseIndex(argMultimap.getPreamble());

        try {
            newTag = new Tag(argMultimap.getValue(PREFIX_NEWTAG).get());
        } catch (IllegalArgumentException e) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }

        if (arePrefixesPresent(argMultimap, PREFIX_COLUMN)) {
            State targetState = ParserUtil.parseState(argMultimap.getValue(PREFIX_COLUMN).get());
            return new AddTagByStateCommand(index, newTag, targetState);
        }

        return new AddTagCommand(index, newTag);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
