package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUERYSTRING;

import java.util.stream.Stream;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bug.BugContainsQueryStringPredicate;

public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of SearchCommand
     * and returns a SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_QUERYSTRING);

        if (!arePrefixesPresent(argMultimap, PREFIX_QUERYSTRING)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        String queryString = ParserUtil.parseQueryString(argMultimap.getValue(PREFIX_QUERYSTRING).get());
        if (queryString.trim().isEmpty()) {
            throw new ParseException(String.format(SearchCommand.MESSAGE_EMPTY_QUERY_STRING,
                    SearchCommand.MESSAGE_USAGE));
        }
        return new SearchCommand(new BugContainsQueryStringPredicate(queryString));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
