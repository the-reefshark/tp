package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.stream.Stream;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTagByStateCommand;
import seedu.address.logic.commands.EditTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;

public class EditTagCommandParser implements Parser<EditTagCommand> {

    private static final int NUMBER_OF_PREFIXES_EXPECTED = 3;
    private static final int NUMBER_OF_OLDTAG = 1;
    private static final int NUMBER_OF_NEWTAG = 1;

    //TODO clean up all documentation details for EditTagCommand, EditTagCommandParser,
    // AddTagCommand, AddTagCommandParser
    /**
     * Parses the given {@code String} of arguments in the context of the EditTagCommand
     * and returns an EditTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OLDTAG, PREFIX_NEWTAG, PREFIX_COLUMN);
        int number_of_prefix_expected = NUMBER_OF_PREFIXES_EXPECTED;
        Index index;
        Tag oldTag;
        Tag newTag;
        if (arePrefixesPresent(argMultimap, PREFIX_COLUMN)) {
            number_of_prefix_expected++;
        }

        boolean hasExtraPrefixes = argMultimap.getSize() != number_of_prefix_expected;
        boolean hasIncorrectNumberOfPrefixValues =
                argMultimap.numberOfPrefixElements(PREFIX_NEWTAG) != NUMBER_OF_NEWTAG
                || argMultimap.numberOfPrefixElements(PREFIX_OLDTAG) != NUMBER_OF_OLDTAG;

        if (!arePrefixesPresent(argMultimap, PREFIX_OLDTAG, PREFIX_NEWTAG, PREFIX_COLUMN)
                    || argMultimap.getPreamble().isEmpty() || hasExtraPrefixes
                    || hasIncorrectNumberOfPrefixValues) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagCommand.MESSAGE_USAGE));
        }
        assert argMultimap.getValue(PREFIX_OLDTAG).isPresent();
        assert argMultimap.getValue(PREFIX_NEWTAG).isPresent();

        index = ParserUtil.parseIndex(argMultimap.getPreamble());

        try {
            oldTag = new Tag(argMultimap.getValue(PREFIX_OLDTAG).get());
            newTag = new Tag(argMultimap.getValue(PREFIX_NEWTAG).get());
        } catch (IllegalArgumentException e) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }

        if (arePrefixesPresent(argMultimap, PREFIX_COLUMN)) {
            State targetState = ParserUtil.parseState(argMultimap.getValue(PREFIX_COLUMN).get());
            return new EditTagByStateCommand(index, oldTag, newTag, targetState);
        }


        return new EditTagCommand(index, oldTag, newTag);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
