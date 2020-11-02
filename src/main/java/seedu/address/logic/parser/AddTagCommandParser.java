package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLUMN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagByStateCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;

public class AddTagCommandParser implements Parser<AddTagCommand> {

    private static final int NUMBER_OF_PREFIXES_EXPECTED = 2;
    private static final int NUMBER_OF_COLUMN = 1;

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
        int numberOfPrefixesExpected = NUMBER_OF_PREFIXES_EXPECTED;
        if (arePrefixesPresent(argMultimap, PREFIX_COLUMN)) {
            numberOfPrefixesExpected++;
        }
        boolean hasExtraPrefixes = argMultimap.getSize() != numberOfPrefixesExpected;
        boolean hasIncorrectNumberOfColumn = argMultimap.numberOfPrefixElements(PREFIX_COLUMN) > NUMBER_OF_COLUMN;

        if (!arePrefixesPresent(argMultimap, PREFIX_NEWTAG)
                || argMultimap.getPreamble().isEmpty() || hasExtraPrefixes || hasIncorrectNumberOfColumn) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }
        assert argMultimap.getValue(PREFIX_NEWTAG).isPresent();

        index = ParserUtil.parseIndex(argMultimap.getPreamble());

        Set<Tag> tagsToAdd = parseTagsForEdit(argMultimap.getAllValues(PREFIX_NEWTAG)).get();

        if (arePrefixesPresent(argMultimap, PREFIX_COLUMN)) {
            State targetState = ParserUtil.parseState(argMultimap.getValue(PREFIX_COLUMN).get());
            return new AddTagByStateCommand(index, tagsToAdd, targetState);
        }

        return new AddTagCommand(index, tagsToAdd);
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
