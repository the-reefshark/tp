package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_PROVIDE_COLUMN;
import static seedu.address.commons.core.Messages.MESSAGE_REMOVE_COLUMN;
import static seedu.address.logic.commands.CommandTestUtil.COLUMN_DESC_BACKLOG;
import static seedu.address.logic.commands.CommandTestUtil.COLUMN_DESC_TODO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COLUMN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_NEW;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_OLD;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_UI;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NEW;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_OLD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_TODO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPONENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOGIC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUG;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTagByStateCommand;
import seedu.address.logic.commands.EditTagCommand;
import seedu.address.model.ModelManager;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;

public class EditTagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagCommand.MESSAGE_USAGE);
    private static boolean isKanbanView = ModelManager.isKanban();
    private EditTagCommandParser parser = new EditTagCommandParser();


    @AfterAll
    public static void resetModelManager() {
        if (isKanbanView) {
            ModelManager.setKanbanWindow();
        } else {
            ModelManager.setListViewWindow();
        }
    }


    @Test
    public void parse_validInputs_success() {
        Index targetIndex = INDEX_SECOND_BUG;
        Tag oldTag = new Tag(VALID_TAG_LOGIC);
        Tag newTag = new Tag(VALID_TAG_COMPONENT);

        // Testing valid input in list view
        ModelManager.setListViewWindow();
        String userInputWithoutColumn = targetIndex.getOneBased() + TAG_DESC_OLD + TAG_DESC_NEW;
        EditTagCommand editTagCommand = new EditTagCommand(targetIndex, oldTag, newTag);

        assertParseSuccess(parser, userInputWithoutColumn, editTagCommand);

        // Testing valid input in kanban view
        ModelManager.setKanbanWindow();
        String userInputWithColumn = targetIndex.getOneBased() + COLUMN_DESC_TODO + TAG_DESC_OLD + TAG_DESC_NEW;
        EditTagByStateCommand editTagByStateCommand = new EditTagByStateCommand(targetIndex, oldTag, newTag,
                VALID_STATE_TODO);

        assertParseSuccess(parser, userInputWithColumn, editTagByStateCommand);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        ModelManager.setListViewWindow();
        // negative index
        assertParseFailure(parser, "-5" + TAG_DESC_OLD + TAG_DESC_NEW, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + TAG_DESC_OLD + TAG_DESC_NEW, MESSAGE_INVALID_INDEX);

        // beyond max int
        assertParseFailure(parser, (Integer.MAX_VALUE + 1) + TAG_DESC_OLD + TAG_DESC_NEW,
                Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string" + TAG_DESC_OLD + TAG_DESC_NEW, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string" + TAG_DESC_OLD + TAG_DESC_NEW, MESSAGE_INVALID_FORMAT);

        // blank preamble
        assertParseFailure(parser, "" + TAG_DESC_OLD + TAG_DESC_NEW, MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_extraPrefixes_failure() {
        ModelManager.setListViewWindow();
        Index targetIndex = INDEX_SECOND_BUG;
        String userInputOne = targetIndex.getOneBased() + TAG_DESC_OLD + TAG_DESC_NEW + NAME_DESC_UI;
        String userInputTwo = targetIndex.getOneBased() + TAG_DESC_OLD + NAME_DESC_UI + TAG_DESC_NEW;

        // since n/ is not a valid prefix for this command, it is read together with the earlier input
        assertParseFailure(parser, userInputOne, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, userInputTwo, Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingParts_failure() {
        ModelManager.setListViewWindow();
        // no index specified
        assertParseFailure(parser, TAG_DESC_OLD + TAG_DESC_NEW, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // missing new tag
        assertParseFailure(parser, "1" + TAG_DESC_OLD, MESSAGE_INVALID_FORMAT);

        // missing old tag
        assertParseFailure(parser, "1" + TAG_DESC_NEW, MESSAGE_INVALID_FORMAT); // invalid format
    }


    @Test
    public void parse_invalidTagValueCorrectFormat_failure() {
        ModelManager.setListViewWindow();
        // Invalid old tag
        assertParseFailure(parser, "1" + INVALID_TAG_OLD + TAG_DESC_NEW, Tag.MESSAGE_CONSTRAINTS);

        // invalid new tag
        assertParseFailure(parser, "1" + TAG_DESC_OLD + INVALID_TAG_NEW, Tag.MESSAGE_CONSTRAINTS);

        ModelManager.setKanbanWindow();
        // invalid new old in kanban view
        assertParseFailure(parser, "1" + COLUMN_DESC_TODO + INVALID_TAG_OLD + TAG_DESC_NEW, Tag.MESSAGE_CONSTRAINTS);

        // invalid new tag in kanban view
        assertParseFailure(parser, "1" + COLUMN_DESC_TODO + TAG_DESC_OLD + INVALID_TAG_NEW, Tag.MESSAGE_CONSTRAINTS);

    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        ModelManager.setListViewWindow();
        // multiple old tag inputs
        assertParseFailure(parser, "1" + TAG_DESC_OLD + TAG_DESC_OLD + TAG_DESC_NEW , MESSAGE_INVALID_FORMAT);

        // multiple new tag inputs
        assertParseFailure(parser, "1" + TAG_DESC_OLD + TAG_DESC_NEW + TAG_DESC_NEW, MESSAGE_INVALID_FORMAT);

        // multiple old and new tag inputs
        assertParseFailure(parser, "1" + TAG_DESC_OLD + TAG_DESC_OLD + TAG_DESC_NEW + TAG_DESC_NEW,
                MESSAGE_INVALID_FORMAT);

        ModelManager.setKanbanWindow();
        // multiple old tag inputs
        assertParseFailure(parser, "1" + COLUMN_DESC_TODO + TAG_DESC_OLD + TAG_DESC_OLD + TAG_DESC_NEW,
                MESSAGE_INVALID_FORMAT);

        // multiple new tag inputs
        assertParseFailure(parser, "1" + COLUMN_DESC_TODO + TAG_DESC_OLD + TAG_DESC_NEW + TAG_DESC_NEW,
                MESSAGE_INVALID_FORMAT);

        // multiple old and new tag inputs
        assertParseFailure(parser, "1" + COLUMN_DESC_TODO + TAG_DESC_OLD + TAG_DESC_OLD + TAG_DESC_NEW + TAG_DESC_NEW,
                MESSAGE_INVALID_FORMAT);

        // multiple column inputs
        assertParseFailure(parser, "1" + COLUMN_DESC_TODO + COLUMN_DESC_BACKLOG + TAG_DESC_OLD + TAG_DESC_NEW,
                MESSAGE_INVALID_FORMAT);
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
    public void parse_incorrectlySuppliedColumn_failure() {
        Index targetIndex = INDEX_SECOND_BUG;

        ModelManager.setListViewWindow();
        // Column supplied when not needed
        assertParseFailure(parser, targetIndex.getOneBased() + COLUMN_DESC_TODO + TAG_DESC_OLD + TAG_DESC_NEW,
                MESSAGE_REMOVE_COLUMN);

        ModelManager.setKanbanWindow();
        // Column not supplied when needed
        assertParseFailure(parser, targetIndex.getOneBased() + TAG_DESC_OLD + TAG_DESC_NEW, MESSAGE_PROVIDE_COLUMN);
    }


}
