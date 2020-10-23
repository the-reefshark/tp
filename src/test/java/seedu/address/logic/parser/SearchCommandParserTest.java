package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.bug.BugContainsQueryStringPredicate;


public class SearchCommandParserTest {
    private SearchCommandParser parser = new SearchCommandParser();
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_valid_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedSearchCommand = new SearchCommand(new BugContainsQueryStringPredicate("Ui bug"));
        assertParseSuccess(parser, "search q/Ui bug", expectedSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " search  q/ Ui bug     ", expectedSearchCommand);

        // multiple repetitive prefixes
        assertParseSuccess(parser, " search q/Skip q/Skip q/Ui bug", expectedSearchCommand);
    }
}
