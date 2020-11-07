package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_PROVIDE_COLUMN;
import static seedu.address.commons.core.Messages.MESSAGE_REMOVE_COLUMN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLUMN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddTagByStateCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;

public class AddTagCommandParser implements Parser<AddTagCommand> {

    private static final int NUMBER_OF_PREFIXES_EXPECTED = 2;
    private static final int NUMBER_OF_COLUMN = 1;
    private static final Logger LOGGER = LogsCenter.getLogger(AddTagCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the EditTagCommand
     * and returns an EditTagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        LOGGER.info("----------------[ADD TAG COMMAND PARSER][Parsing: " + args + "]");
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NEWTAG, PREFIX_COLUMN);

        ensureValidPreamble(argMultimap);
        ensureValidNumberOfPrefixes(argMultimap);

        assert argMultimap.getValue(PREFIX_NEWTAG).isPresent();

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        Set<Tag> tagsToAdd = parseTagsForEdit(argMultimap.getAllValues(PREFIX_NEWTAG)).get();

        boolean isColumnSupplied = arePrefixesPresent(argMultimap, PREFIX_COLUMN);

        if (isColumnSupplied) {
            return addTagsByState(argMultimap, index, tagsToAdd);
        } else {
            return addTagsWithoutState(index, tagsToAdd);
        }

    }

    /**
     * Creates an AddTagCommand with the appropriate arguments.
     *
     * @param index of bug to add tags to
     * @param tagsToAdd set of tags to add to bug
     * @return AddTagCommand with appropriate values
     * @throws ParseException if column value is found in argMultimap
     */
    private AddTagCommand addTagsWithoutState(Index index, Set<Tag> tagsToAdd) throws ParseException {
        if (ModelManager.isKanban()) {
            throw new ParseException(MESSAGE_PROVIDE_COLUMN);
        }

        return new AddTagCommand(index, tagsToAdd);
    }

    /**
     * Creates an AddTagByStateCommand with the appropriate arguments.
     *
     * @param argMultimap argument multimap of prefix values
     * @param index index of bug to edit
     * @param tagsToAdd set of tags to add to bug
     * @return AddTagByStateCommand with the appropriate values
     * @throws ParseException if no column value is found in argMultimap
     */
    private AddTagCommand addTagsByState(ArgumentMultimap argMultimap, Index index, Set<Tag> tagsToAdd)
            throws ParseException {
        if (!ModelManager.isKanban()) {
            throw new ParseException(MESSAGE_REMOVE_COLUMN);
        }
        State targetState = ParserUtil.parseState(argMultimap.getValue(PREFIX_COLUMN).get());
        return new AddTagByStateCommand(index, tagsToAdd, targetState);
    }

    /**
     * Ensures the appropriate number of prefixes were given as input.
     *
     * @param argMultimap argument multimap of prefix values
     * @throws ParseException if an invalid number of prefixes were supplied
     */
    private void ensureValidNumberOfPrefixes(ArgumentMultimap argMultimap) throws ParseException {
        int numberOfPrefixesExpected = NUMBER_OF_PREFIXES_EXPECTED;
        if (arePrefixesPresent(argMultimap, PREFIX_COLUMN)) {
            numberOfPrefixesExpected++;
        }
        boolean hasExtraPrefixes = argMultimap.getSize() != numberOfPrefixesExpected;
        boolean hasIncorrectNumberOfColumn = argMultimap.numberOfPrefixElements(PREFIX_COLUMN) > NUMBER_OF_COLUMN;

        if (!arePrefixesPresent(argMultimap, PREFIX_NEWTAG)
                || argMultimap.getPreamble().isEmpty() || hasExtraPrefixes || hasIncorrectNumberOfColumn) {
            LOGGER.info("----------------[ADD TAG COMMAND PARSER][Incorrect number of prefixes]");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Ensures the preamble is valid.
     *
     * @param argMultimap argument multimap of prefix values
     * @throws ParseException if preamble is invalid
     */
    private void ensureValidPreamble(ArgumentMultimap argMultimap) throws ParseException {
        String trimmedIndex = argMultimap.getPreamble().trim();
        if (!StringUtil.isNumber(trimmedIndex)) {
            LOGGER.info("----------------[ADD TAG COMMAND PARSER][Invalid preamble, extra arguments!]");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }
        if (StringUtil.isIndexOverflow(trimmedIndex)) {
            LOGGER.info("----------------[ADD TAG COMMAND PARSER][Invalid preamble, invalid value]");
            throw new ParseException(Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }

        assert (!tags.isEmpty() == true);
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
