package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_UI;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOTE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_UI;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.STATE_DESC_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.STATE_DESC_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BACKEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRONTEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPONENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOGIC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalBugs.BUGELEVEN;
import static seedu.address.testutil.TypicalBugs.BUGNINE;
import static seedu.address.testutil.TypicalBugs.BUGTEN;
import static seedu.address.testutil.TypicalBugs.BUGTWELVE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.Note;
import seedu.address.model.bug.Priority;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BugBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Bug expectedBug = new BugBuilder(BUGELEVEN).withTags(VALID_TAG_LOGIC).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_HOMEPAGE + STATE_DESC_HOMEPAGE
                + DESCRIPTION_DESC_HOMEPAGE + NOTE_DESC_HOMEPAGE + PRIORITY_DESC_HOMEPAGE + TAG_DESC_FRONTEND,
                new AddCommand(expectedBug));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_PARSER + NAME_DESC_HOMEPAGE + STATE_DESC_HOMEPAGE
                + DESCRIPTION_DESC_HOMEPAGE + NOTE_DESC_HOMEPAGE + PRIORITY_DESC_HOMEPAGE + TAG_DESC_FRONTEND,
                new AddCommand(expectedBug));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_HOMEPAGE + STATE_DESC_HOMEPAGE + PRIORITY_DESC_PARSER
                + DESCRIPTION_DESC_HOMEPAGE + NOTE_DESC_HOMEPAGE + PRIORITY_DESC_HOMEPAGE + TAG_DESC_FRONTEND,
                new AddCommand(expectedBug));

        // multiple state - last state accepted
        assertParseSuccess(parser, NAME_DESC_HOMEPAGE + STATE_DESC_PARSER + STATE_DESC_HOMEPAGE
                + DESCRIPTION_DESC_HOMEPAGE + NOTE_DESC_HOMEPAGE + PRIORITY_DESC_HOMEPAGE + TAG_DESC_FRONTEND,
                new AddCommand(expectedBug));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, NAME_DESC_HOMEPAGE + STATE_DESC_HOMEPAGE + INVALID_DESCRIPTION_DESC
                + DESCRIPTION_DESC_HOMEPAGE + NOTE_DESC_HOMEPAGE + PRIORITY_DESC_HOMEPAGE + TAG_DESC_FRONTEND,
                new AddCommand(expectedBug));

        // multiple tags - all accepted
        Bug expectedBugMultipleTags = new BugBuilder(BUGELEVEN).withTags(VALID_TAG_LOGIC, VALID_TAG_COMPONENT)
                .build();
        assertParseSuccess(parser, NAME_DESC_HOMEPAGE + STATE_DESC_HOMEPAGE + PRIORITY_DESC_HOMEPAGE
                + DESCRIPTION_DESC_HOMEPAGE + NOTE_DESC_HOMEPAGE + TAG_DESC_BACKEND + TAG_DESC_FRONTEND,
                new AddCommand(expectedBugMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Bug expectedBug = new BugBuilder(BUGTEN).withTags().build();
        assertParseSuccess(parser, NAME_DESC_PARSER + STATE_DESC_PARSER
                        + DESCRIPTION_DESC_PARSER + NOTE_DESC_PARSER + PRIORITY_DESC_PARSER,
                new AddCommand(expectedBug));


        expectedBug = new BugBuilder(BUGNINE).withTags().build();
        assertParseSuccess(parser, NAME_DESC_UI + DESCRIPTION_DESC_UI,
                new AddCommand(expectedBug));

        // no priority
        expectedBug = new BugBuilder(BUGELEVEN).withPriority().build();
        assertParseSuccess(parser, NAME_DESC_HOMEPAGE + STATE_DESC_HOMEPAGE + DESCRIPTION_DESC_HOMEPAGE
                + NOTE_DESC_HOMEPAGE + TAG_DESC_BACKEND + TAG_DESC_FRONTEND, new AddCommand(expectedBug));

        // no note
        expectedBug = new BugBuilder(BUGTWELVE).build();
        assertParseSuccess(parser, NAME_DESC_HOMEPAGE + STATE_DESC_HOMEPAGE + DESCRIPTION_DESC_HOMEPAGE
                + PRIORITY_DESC_HOMEPAGE + TAG_DESC_BACKEND + TAG_DESC_FRONTEND, new AddCommand(expectedBug));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_HOMEPAGE + STATE_DESC_HOMEPAGE + DESCRIPTION_DESC_HOMEPAGE
                        + NOTE_DESC_HOMEPAGE + PRIORITY_DESC_HOMEPAGE, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, NAME_DESC_HOMEPAGE + STATE_DESC_HOMEPAGE + VALID_DESCRIPTION_HOMEPAGE
                        + NOTE_DESC_HOMEPAGE + PRIORITY_DESC_HOMEPAGE, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_HOMEPAGE + VALID_STATE_HOMEPAGE + VALID_DESCRIPTION_HOMEPAGE
                        + NOTE_DESC_HOMEPAGE + VALID_PRIORITY_HOMEPAGE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + STATE_DESC_HOMEPAGE + DESCRIPTION_DESC_HOMEPAGE
                + NOTE_DESC_HOMEPAGE + PRIORITY_DESC_HOMEPAGE + TAG_DESC_BACKEND + TAG_DESC_FRONTEND,
                Name.MESSAGE_CONSTRAINTS);

        // invalid state
        assertParseFailure(parser, NAME_DESC_HOMEPAGE + INVALID_STATE_DESC + DESCRIPTION_DESC_HOMEPAGE
                + NOTE_DESC_HOMEPAGE + PRIORITY_DESC_HOMEPAGE + TAG_DESC_BACKEND + TAG_DESC_FRONTEND,
                State.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, NAME_DESC_HOMEPAGE + STATE_DESC_HOMEPAGE + INVALID_DESCRIPTION_DESC
                + NOTE_DESC_HOMEPAGE + PRIORITY_DESC_HOMEPAGE + TAG_DESC_BACKEND + TAG_DESC_FRONTEND,
                Description.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_HOMEPAGE + STATE_DESC_HOMEPAGE + DESCRIPTION_DESC_HOMEPAGE
                + NOTE_DESC_HOMEPAGE + PRIORITY_DESC_HOMEPAGE + INVALID_TAG_DESC + VALID_TAG_LOGIC,
                Tag.MESSAGE_CONSTRAINTS);

        // invalid priority
        assertParseFailure(parser, NAME_DESC_HOMEPAGE + STATE_DESC_HOMEPAGE + DESCRIPTION_DESC_HOMEPAGE
                + NOTE_DESC_HOMEPAGE + INVALID_PRIORITY_DESC + TAG_DESC_BACKEND + TAG_DESC_FRONTEND,
                Priority.MESSAGE_CONSTRAINTS);

        // invalid note
        assertParseFailure(parser, NAME_DESC_HOMEPAGE + STATE_DESC_HOMEPAGE + DESCRIPTION_DESC_HOMEPAGE
                + INVALID_NOTE_DESC + PRIORITY_DESC_HOMEPAGE + TAG_DESC_BACKEND + TAG_DESC_FRONTEND,
                Note.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + STATE_DESC_HOMEPAGE + INVALID_DESCRIPTION_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_HOMEPAGE + STATE_DESC_HOMEPAGE
                + DESCRIPTION_DESC_HOMEPAGE + NOTE_DESC_HOMEPAGE + TAG_DESC_BACKEND + TAG_DESC_FRONTEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
