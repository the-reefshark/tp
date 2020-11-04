package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.*;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_TODO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUG;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteByStateCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.ModelManager;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommandListView() {
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_BUG));
    }

    @Test
    public void parse_invalidArgs_throwsParseExceptionListView() {
        ModelManager.setListViewWindow();
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 c/todo",MESSAGE_REMOVE_COLUMN);
    }

    @Test
    public void parse_validArgs_returnsDeleteCommandKanbanView() {
        ModelManager.setKanbanWindow();
        assertParseSuccess(parser, "1 c/todo", new DeleteByStateCommand(INDEX_FIRST_BUG, VALID_STATE_TODO));
    }

    @Test
    public void parse_invalidArgs_throwsParseExceptionKanbanView() {
        ModelManager.setKanbanWindow();
        assertParseFailure(parser, "b c/todo", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1", MESSAGE_PROVIDE_COLUMN);
    }
}
