package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUERY_STRING_ARGUMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUERY_STRING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.QUERY_STRING_REPEAT;
import static seedu.address.logic.commands.CommandTestUtil.QUERY_STRING_TRAILING;
import static seedu.address.logic.commands.CommandTestUtil.QUERY_STRING_TYPICAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUERY_STRING_ONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.bug.BugContainsQueryStringPredicate;


public class SearchCommandParserTest {
    private SearchCommandParser parser = new SearchCommandParser();
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, INVALID_QUERY_STRING_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPrefix_throwsParseException() {
        assertParseFailure(parser, INVALID_QUERY_STRING_ARGUMENT_DESC, SearchCommand.MESSAGE_EMPTY_QUERY_STRING);
    }

    @Test
    public void parse_valid_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedSearchCommand =
                new SearchCommand(new BugContainsQueryStringPredicate(VALID_QUERY_STRING_ONE));
        assertParseSuccess(parser, QUERY_STRING_TYPICAL, expectedSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, QUERY_STRING_TRAILING, expectedSearchCommand);

        // multiple repetitive prefixes
        assertParseSuccess(parser, QUERY_STRING_REPEAT, expectedSearchCommand);
    }
}
