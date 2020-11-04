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
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUG;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_BUG;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagByStateCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.model.ModelManager;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;


public class AddTagCommandParserTest {

    //TODO Add in test for multiple tags being added

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE);

    private AddTagCommandParser parser = new AddTagCommandParser();


    @Test
    public void parse_extraPrefixes_failure() {
        Index targetIndex = INDEX_SECOND_BUG;
        String userInputOne = targetIndex.getOneBased() + TAG_DESC_NEW + TAG_DESC_NEW + NAME_DESC_UI;
        String userInputTwo = targetIndex.getOneBased() + TAG_DESC_NEW + NAME_DESC_UI + TAG_DESC_NEW;
        String userInputThree = targetIndex.getOneBased() + NAME_DESC_UI + TAG_DESC_NEW;

        // since n/ is not a valid prefix for this command, it is read together with the earlier input
        assertParseFailure(parser, userInputOne, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, userInputTwo, Tag.MESSAGE_CONSTRAINTS);

        // n/ will be read before tag, together with prefix.
        assertParseFailure(parser, userInputThree, MESSAGE_INVALID_INDEX);
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
        assertParseFailure(parser, "1" + INVALID_TAG_NEW, Tag.MESSAGE_CONSTRAINTS); // invalid tag
        assertParseFailure(parser, "1" + " nt/print array", Tag.MESSAGE_CONSTRAINTS); // invalid tag
    }


    @Test
    public void parse_validValueWithoutColumn_success() {
        ModelManager.setListViewWindow();
        Index targetIndex = INDEX_SECOND_BUG;
        String userInput = targetIndex.getOneBased() + TAG_DESC_NEW;
        Set<Tag> tagsToAddLogic = new HashSet<>();
        tagsToAddLogic.add(new Tag(VALID_TAG_COMPONENT));
        AddTagCommand expectedCommand = new AddTagCommand(targetIndex, tagsToAddLogic);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validValueWithColumn_success() {
        Set<Tag> tagsToAddLogic = new HashSet<>();
        tagsToAddLogic.add(new Tag(VALID_TAG_COMPONENT));
        Index targetIndex = INDEX_SECOND_BUG;
        String userInput = targetIndex.getOneBased() + COLUMN_DESC_TODO + TAG_DESC_NEW;
        AddTagByStateCommand expectedCommand = new AddTagByStateCommand(targetIndex, tagsToAddLogic,
            VALID_STATE_TODO);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validValueWithRepeatedColumn_failure() {
        Index targetIndex = INDEX_SECOND_BUG;
        String userInput = targetIndex.getOneBased() + COLUMN_DESC_TODO + TAG_DESC_NEW + COLUMN_DESC_TODO;
        String expectedString = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagByStateCommand.MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedString);
    }

    @Test
    public void parse_invalidColumnValue_failure() {
        ModelManager.setKanbanWindow();
        Index targetIndex = INDEX_SECOND_BUG;
        String userInput = targetIndex.getOneBased() + INVALID_COLUMN_DESC + TAG_DESC_NEW;
        String expectedString = State.MESSAGE_CONSTRAINTS;

        assertParseFailure(parser, userInput, expectedString);
    }

    @Test
    public void parse_invalidUserInputs_throwParseExeception() {
        Index targetIndex = INDEX_THIRD_BUG;
        String userInputOne = targetIndex.getOneBased() + "";
        String userInputTwo = targetIndex.getOneBased() + TAG_DESC_OLD;

        assertParseFailure(parser, userInputOne, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, userInputTwo, MESSAGE_INVALID_FORMAT);
    }

}
