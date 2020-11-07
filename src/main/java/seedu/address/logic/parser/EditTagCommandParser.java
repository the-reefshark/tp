package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_PROVIDE_COLUMN;
import static seedu.address.commons.core.Messages.MESSAGE_REMOVE_COLUMN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLUMN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLDTAG;

import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTagByStateCommand;
import seedu.address.logic.commands.EditTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;

public class EditTagCommandParser implements Parser<EditTagCommand> {

    private static final int TOTAL_NUMBER_OF_PREFIXES_EXPECTED = 3;
    private static final int NUMBER_OF_OLDTAG_PREFIXES_EXPECTED = 1;
    private static final int NUMBER_OF_NEWTAG_PREFIXES_EXPECTED = 1;
    private static final int NUMBER_OF_COLUMN_PREFIXES_EXPECTED = 1;

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
                ArgumentTokenizer.tokenize(args, PREFIX_OLDTAG, PREFIX_NEWTAG, PREFIX_COLUMN);

        ensureValidPreamble(argMultimap);
        ensureValidNumberOfPrefixes(argMultimap);

        assert argMultimap.getValue(PREFIX_OLDTAG).isPresent();
        assert argMultimap.getValue(PREFIX_NEWTAG).isPresent();

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        Tag oldTag = getTagFromMultiMap(argMultimap, PREFIX_OLDTAG);
        Tag newTag = getTagFromMultiMap(argMultimap, PREFIX_NEWTAG);

        boolean isColumnSupplied = arePrefixesPresent(argMultimap, PREFIX_COLUMN);

        if (isColumnSupplied) {
            return editTagsByState(argMultimap, index, oldTag, newTag);
        } else {
            return editTagsWithoutState(index, oldTag, newTag);
        }
    }

    private void ensureValidNumberOfPrefixes(ArgumentMultimap argMultimap) throws ParseException {
        int numberOfPrefixExpected = TOTAL_NUMBER_OF_PREFIXES_EXPECTED;

        if (arePrefixesPresent(argMultimap, PREFIX_COLUMN)) {
            numberOfPrefixExpected++;
        }

        boolean hasExtraPrefixes = argMultimap.getSize() != numberOfPrefixExpected;
        boolean hasIncorrectNumberOfNewTag = argMultimap.numberOfPrefixElements(PREFIX_NEWTAG)
                                                     != NUMBER_OF_NEWTAG_PREFIXES_EXPECTED;
        boolean hasIncorrectNumberOfOldTag = argMultimap.numberOfPrefixElements(PREFIX_OLDTAG)
                                                     != NUMBER_OF_OLDTAG_PREFIXES_EXPECTED;
        boolean hasIncorrectNumberOfColumn = argMultimap.numberOfPrefixElements(PREFIX_COLUMN)
                                                     > NUMBER_OF_COLUMN_PREFIXES_EXPECTED;
        boolean hasIncorrectNumberOfPrefixValues =
                hasIncorrectNumberOfNewTag || hasIncorrectNumberOfOldTag || hasIncorrectNumberOfColumn;

        if (!arePrefixesPresent(argMultimap, PREFIX_OLDTAG, PREFIX_NEWTAG, PREFIX_COLUMN)
                    || argMultimap.getPreamble().isEmpty() || hasExtraPrefixes
                    || hasIncorrectNumberOfPrefixValues) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagCommand.MESSAGE_USAGE));
        }
    }

    private void ensureValidPreamble(ArgumentMultimap argMultimap) throws ParseException {
        String trimmedIndex = argMultimap.getPreamble().trim();
        if (trimmedIndex.contains(" ")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagCommand.MESSAGE_USAGE));
        }
        if (trimmedIndex.length() >= Integer.toString(Integer.MAX_VALUE - 1).length()) {
            throw new ParseException(Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
        }
    }

    private Tag getTagFromMultiMap(ArgumentMultimap argMultimap, Prefix tagPrefix) throws ParseException {
        try {
            return new Tag(argMultimap.getValue(tagPrefix).get());
        } catch (IllegalArgumentException e) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
    }

    private EditTagByStateCommand editTagsByState(ArgumentMultimap argMultimap, Index index, Tag oldTag, Tag newTag)
            throws ParseException {
        if (!ModelManager.isKanban()) {
            throw new ParseException(MESSAGE_REMOVE_COLUMN);
        }
        State targetState = ParserUtil.parseState(argMultimap.getValue(PREFIX_COLUMN).get());
        return new EditTagByStateCommand(index, oldTag, newTag, targetState);
    }

    private EditTagCommand editTagsWithoutState(Index index, Tag oldTag,
                                                       Tag newTag)
            throws ParseException {
        if (ModelManager.isKanban()) {
            throw new ParseException(MESSAGE_PROVIDE_COLUMN);
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
