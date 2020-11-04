package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.*;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditBugDescriptor;
import seedu.address.model.ModelManager;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.Priority;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditBugDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;
    private static final String PRIORITY_EMPTY = " " + PREFIX_PRIORITY;
    private static final String NOTE_EMPTY = " " + PREFIX_NOTE;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_PARSER, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
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
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_STATE_DESC, State.MESSAGE_CONSTRAINTS); // invalid state
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag
        assertParseFailure(parser, "1" + INVALID_PRIORITY_DESC, Priority.MESSAGE_CONSTRAINTS); // invalid priority

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Bug} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRONTEND + TAG_DESC_BACKEND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRONTEND + TAG_EMPTY + TAG_DESC_BACKEND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRONTEND + TAG_DESC_BACKEND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_STATE_DESC + VALID_DESCRIPTION_PARSER,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_BUG;
        String userInput = targetIndex.getOneBased() + TAG_DESC_BACKEND + PRIORITY_DESC_PARSER
                + STATE_DESC_PARSER + DESCRIPTION_DESC_PARSER + NOTE_DESC_PARSER + NAME_DESC_PARSER + TAG_DESC_FRONTEND;

        EditBugDescriptor descriptor = new EditBugDescriptorBuilder().withName(VALID_NAME_PARSER)
                .withState(VALID_STATE_PARSER).withDescription(VALID_DESCRIPTION_PARSER)
                .withNote(VALID_NOTE_PARSER).withTags(VALID_TAG_COMPONENT, VALID_TAG_LOGIC)
                .withPriority(VALID_PRIORITY_PARSER).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_BUG;
        String userInput = targetIndex.getOneBased() + STATE_DESC_PARSER + PRIORITY_DESC_PARSER;

        EditCommand.EditBugDescriptor descriptor = new EditBugDescriptorBuilder()
                .withState(VALID_STATE_PARSER).withPriority(VALID_PRIORITY_PARSER).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_BUG;
        String userInput = targetIndex.getOneBased() + NAME_DESC_PARSER;
        EditCommand.EditBugDescriptor descriptor = new EditBugDescriptorBuilder().withName(VALID_NAME_PARSER).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);


        // state
        userInput = targetIndex.getOneBased() + STATE_DESC_PARSER;
        descriptor = new EditBugDescriptorBuilder().withState(VALID_STATE_PARSER).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_PARSER;
        descriptor = new EditBugDescriptorBuilder().withDescription(VALID_DESCRIPTION_PARSER).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRONTEND;
        descriptor = new EditBugDescriptorBuilder().withTags(VALID_TAG_LOGIC).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // priority
        userInput = targetIndex.getOneBased() + PRIORITY_DESC_PARSER;
        descriptor = new EditBugDescriptorBuilder().withPriority(VALID_PRIORITY_PARSER).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // note
        userInput = targetIndex.getOneBased() + NOTE_DESC_PARSER;
        descriptor = new EditBugDescriptorBuilder().withNote(VALID_NOTE_PARSER).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_BUG;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_PARSER + PRIORITY_DESC_PARSER
                + NOTE_DESC_PARSER + STATE_DESC_PARSER + TAG_DESC_FRONTEND + DESCRIPTION_DESC_PARSER
                + STATE_DESC_PARSER + TAG_DESC_FRONTEND + DESCRIPTION_DESC_HOMEPAGE + STATE_DESC_HOMEPAGE
                + PRIORITY_DESC_HOMEPAGE + TAG_DESC_BACKEND + NOTE_DESC_HOMEPAGE;

        EditBugDescriptor descriptor = new EditBugDescriptorBuilder()
                .withState(VALID_STATE_HOMEPAGE).withDescription(VALID_DESCRIPTION_HOMEPAGE)
                .withNote(VALID_NOTE_HOMEPAGE).withTags(VALID_TAG_LOGIC, VALID_TAG_COMPONENT)
                .withPriority(VALID_PRIORITY_HOMEPAGE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validValue_success() {
        // other valid values specified
        Index targetIndex = INDEX_FIRST_BUG;
        String userInput = targetIndex.getOneBased() + STATE_DESC_HOMEPAGE + DESCRIPTION_DESC_HOMEPAGE;
        EditBugDescriptor descriptor = new EditBugDescriptorBuilder().withState(VALID_STATE_HOMEPAGE)
                .withDescription(VALID_DESCRIPTION_HOMEPAGE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        ModelManager.setListViewWindow();
        Index targetIndex = INDEX_THIRD_BUG;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditBugDescriptor descriptor = new EditBugDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test void parse_resetPriority_success() {
        Index targetIndex = INDEX_THIRD_BUG;
        String userInput = targetIndex.getOneBased() + PRIORITY_EMPTY;

        EditBugDescriptor descriptor = new EditBugDescriptorBuilder().withPriority().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test void parse_resetNote_success() {
        Index targetIndex = INDEX_THIRD_BUG;
        String userInput = targetIndex.getOneBased() + NOTE_EMPTY;

        EditBugDescriptor descriptor = new EditBugDescriptorBuilder().withNote("").build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedPriorityIncludingEmpty_acceptsLast() {
        // empty priority is the last priority
        Index targetIndex = INDEX_FIRST_BUG;
        String userInput = targetIndex.getOneBased() + PRIORITY_DESC_HOMEPAGE + PRIORITY_EMPTY;

        EditBugDescriptor descriptor = new EditBugDescriptorBuilder().withPriority().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // empty priority is not the last priority
        userInput = targetIndex.getOneBased() + PRIORITY_EMPTY + PRIORITY_DESC_HOMEPAGE;
        descriptor = new EditBugDescriptorBuilder().withPriority(VALID_PRIORITY_HOMEPAGE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
