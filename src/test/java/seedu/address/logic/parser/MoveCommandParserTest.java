package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.STATE_DESC_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_PARSER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUG;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MoveCommand;
import seedu.address.model.bug.State;

class MoveCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE);

    private MoveCommandParser parser = new MoveCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, "s/backlog", MESSAGE_INVALID_FORMAT);

        // no state specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no state specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_PARSER, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_PARSER, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidState_failure() {
        assertParseFailure(parser, "1" + INVALID_STATE_DESC, State.MESSAGE_CONSTRAINTS); // invalid state
    }

    @Test
    public void parse_correctIndexAndState_success() {
        Index targetIndex = INDEX_SECOND_BUG;
        String userInput = targetIndex.getOneBased() + STATE_DESC_PARSER;

        MoveCommand expectedCommand = new MoveCommand(targetIndex, new State(VALID_STATE_PARSER));

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
