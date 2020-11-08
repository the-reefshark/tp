package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_EXTRA_ARGUMENTS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUERYSTRING;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bug.BugContainsQueryStringPredicate;

public class SearchCommandParser implements Parser<SearchCommand> {

    private static final Logger LOGGER = LogsCenter.getLogger(SearchCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of SearchCommand
     * and returns a SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        LOGGER.info("---[SEARCH COMMAND PARSER][Parse: " + args + "]---");

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_QUERYSTRING);

        ensurePrefixesPresent(argMultimap);
        ensureNoRedundantPreamble(argMultimap);

        String queryString = getQueryString(argMultimap);
        ensureNoEmptyQueryString(queryString);

        return new SearchCommand(new BugContainsQueryStringPredicate(queryString));
    }

    /**
     * Ensures the {@code argMultimap} is able to parse sufficient number of prefixes.
     * @param argMultimap argument multimap is used to get the arguments of the prefix.
     * @throws ParseException if the prefix is not found in the input command.
     */
    private void ensurePrefixesPresent(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_QUERYSTRING)) {
            LOGGER.info("---[SEARCH COMMAND PARSER][Cannot find the prefix QUERYSTRING]---");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Ensures the {@code argMultimap} is able to parse sufficient number of prefixes.
     * @param argMultimap argument multimap is used to get the arguments of the prefix.
     * @throws ParseException if the preamble is empty in the input command.
     */
    private void ensureNoRedundantPreamble(ArgumentMultimap argMultimap) throws ParseException {
        if (!StringUtil.isEmptyArgument(argMultimap.getPreamble())) {
            LOGGER.info("---[SEARCH COMMAND PARSER][Redundant Preamble]---");
            throw new ParseException(MESSAGE_EXTRA_ARGUMENTS);
        }
    }

    /**
     * Ensures the query-string is not empty.
     * @param queryString the query-string to search.
     * @throws ParseException if the {@code queryString} is not empty.
     */
    private void ensureNoEmptyQueryString(String queryString) throws ParseException {
        if (StringUtil.isEmptyArgument(queryString)) {
            LOGGER.info("---[SEARCH COMMAND PARSER][Empty QUERYSTRING]---");
            throw new ParseException(SearchCommand.MESSAGE_EMPTY_QUERY_STRING);
        }
    }

    /**
     * Returns the valid query-string value.
     * @param argMultimap argument multimap is used to get the arguments of the prefix.
     * @throws ParseException if the query-string is invalid.
     */
    private String getQueryString(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseQueryString(argMultimap.getValue(PREFIX_QUERYSTRING).get());
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
