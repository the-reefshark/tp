package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COLUMN_DESC_TODO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COLUMN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_NEW;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_OLD;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_UI;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NEW;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_OLD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_TODO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPONENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOGIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUG;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUG;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_BUG;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTagByStateCommand;
import seedu.address.logic.commands.EditTagCommand;
import seedu.address.model.ModelManager;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;

public class EditTagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagCommand.MESSAGE_USAGE);

    private EditTagCommandParser parser = new EditTagCommandParser();


    @Test
    public void parse_extraPrefixes_failure() {
        Index targetIndex = INDEX_SECOND_BUG;
        String userInputOne = targetIndex.getOneBased() + TAG_DESC_OLD + TAG_DESC_NEW + NAME_DESC_UI;
        String userInputTwo = targetIndex.getOneBased() + TAG_DESC_OLD + NAME_DESC_UI + TAG_DESC_NEW;

        // since n/ is not a valid prefix for this command, it is read together with the earlier input
        assertParseFailure(parser, userInputOne, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, userInputTwo, Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_PARSER, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // missing new tag
        assertParseFailure(parser, "1" + INVALID_TAG_OLD, MESSAGE_INVALID_FORMAT);

        // missing old tag
        assertParseFailure(parser, "1" + INVALID_TAG_NEW, MESSAGE_INVALID_FORMAT); // invalid format
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
    public void parse_invalidValueCorrectFormat_failure() {
        assertParseFailure(parser, "1" + INVALID_TAG_OLD + TAG_DESC_NEW, Tag.MESSAGE_CONSTRAINTS); //invalid tag
        assertParseFailure(parser, "1" + TAG_DESC_OLD + INVALID_TAG_NEW, Tag.MESSAGE_CONSTRAINTS); // invalid tag
        assertParseFailure(parser, "1" + TAG_DESC_OLD + " nt/print array", Tag.MESSAGE_CONSTRAINTS); // invalid tag

    }


    @Test
    public void parse_validValueWithoutColumn_success() {
        Index targetIndex = INDEX_SECOND_BUG;
        String userInput = targetIndex.getOneBased() + TAG_DESC_OLD + TAG_DESC_NEW;

        EditTagCommand expectedCommand = new EditTagCommand(targetIndex, new Tag(VALID_TAG_LOGIC),
                new Tag(VALID_TAG_COMPONENT));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validValueWithColumn_success() {
        Index targetIndex = INDEX_SECOND_BUG;
        String userInput = targetIndex.getOneBased() + COLUMN_DESC_TODO + TAG_DESC_OLD + TAG_DESC_NEW;

        EditTagByStateCommand expectedCommand = new EditTagByStateCommand(targetIndex, new Tag(VALID_TAG_LOGIC),
                new Tag(VALID_TAG_COMPONENT), VALID_STATE_TODO);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_multipleRepeatedFieldsWithoutColumn_success() {
        ModelManager.setListViewWindow();
        Index targetIndex = INDEX_FIRST_BUG;
        String userInput = targetIndex.getOneBased() + TAG_DESC_OLD + " " + PREFIX_NEWTAG + VALID_TAG_LOGIC;

        EditTagCommand expectedCommand = new EditTagCommand(targetIndex, new Tag(VALID_TAG_LOGIC),
                new Tag(VALID_TAG_LOGIC));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validValueWithRepeatedColumn_failure() {
        Index targetIndex = INDEX_SECOND_BUG;
        String userInput =
                targetIndex.getOneBased() + COLUMN_DESC_TODO + TAG_DESC_OLD + TAG_DESC_NEW + COLUMN_DESC_TODO;
        String expectedString = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagByStateCommand.MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedString);
    }

    @Test
    public void parse_invalidColumnValue_failure() {
        ModelManager.setKanbanWindow();
        Index targetIndex = INDEX_SECOND_BUG;
        String userInput = targetIndex.getOneBased() + INVALID_COLUMN_DESC + TAG_DESC_OLD + TAG_DESC_NEW;
        String expectedString = State.MESSAGE_CONSTRAINTS;

        assertParseFailure(parser, userInput, expectedString);
    }

    @Test
    public void parse_invalidUserInputs_throwParseExeception() {
        Index targetIndex = INDEX_THIRD_BUG;
        String userInputOne = targetIndex.getOneBased() + "";
        String userInputTwo = targetIndex.getOneBased() + TAG_DESC_OLD;
        String userInputThree = targetIndex.getOneBased() + TAG_DESC_NEW;

        assertParseFailure(parser, userInputOne, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, userInputTwo, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, userInputThree, MESSAGE_INVALID_FORMAT);
    }

}
