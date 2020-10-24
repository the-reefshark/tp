package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLDTAG;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

public class EditTagCommandParser implements Parser<EditTagCommand> {

    private static final int NUMBER_OF_PREFIXES_EXPECTED = 3;
    private static final int NUMBER_OF_OLDTAG = 1;
    private static final int NUMBER_OF_NEWTAG = 1;

    //TODO clean up all documentation details for EditTagCommandParser,
    // AddTagCommand, AddTagCommandParser
    /**
     * Parses the given {@code String} of arguments in the context of the EditTagCommand
     * and returns an EditTagCommand object for execution.
     *
     * @param args string to be parsed
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OLDTAG, PREFIX_NEWTAG);

        Index index;
        Tag oldTag;
        Tag newTag;

        boolean hasExtraPrefixes = argMultimap.getSize() != NUMBER_OF_PREFIXES_EXPECTED;
        boolean hasIncorrectNumberOfPrefixValues =
                argMultimap.numberOfPrefixElements(PREFIX_NEWTAG) != NUMBER_OF_NEWTAG
                || argMultimap.numberOfPrefixElements(PREFIX_OLDTAG) != NUMBER_OF_OLDTAG;

        if (!arePrefixesPresent(argMultimap, PREFIX_OLDTAG, PREFIX_NEWTAG)
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


        return new EditTagCommand(index, oldTag, newTag);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
