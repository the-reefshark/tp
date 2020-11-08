package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_BLANK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LOW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_MEDIUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_VALUE_BACKLOG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_VALUE_DONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_VALUE_ONGOING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_VALUE_TODO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPONENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOGIC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.KanBugTracker;
import seedu.address.model.bug.Bug;

/**
 * A utility class containing a list of {@code Bug} objects to be used in tests.
 */
public class TypicalBugs {

    // Bug 1
    public static final String BUG_ONE_NAME = "Add command bug";
    public static final String BUG_ONE_DESCRIPTION = "Cannot add a bug with multiple tags";
    public static final String BUG_ONE_NOTE = "this is a note";
    public static final String[] BUG_ONE_TAG = {"AddCommand"};

    // Bug 2
    public static final String BUG_TWO_NAME = "Edit command parsing";
    public static final String BUG_TWO_DESCRIPTION = "The prefixes of edit command are wrongly parsed";
    public static final String BUG_TWO_NOTE = "this is the second note";
    public static final String[] BUG_TWO_TAG = {"EditCommand", "backend"};

    // Bug 3
    public static final String BUG_THREE_NAME = "Main window overflows";
    public static final String BUG_THREE_DESCRIPTION =
            "If there is a huge number of bugs, the main window cannot display";
    public static final String BUG_THREE_NOTE = "this is the third note";
    public static final String[] BUG_THREE_TAG = {"Ui"};

    // Bug 4
    public static final String BUG_FOUR_NAME = "Search command test";
    public static final String BUG_FOUR_DESCRIPTION = "Need to add more search test to cover all equivalence partition";
    public static final String BUG_FOUR_NOTE = "this is the fourth note";
    public static final String[] BUG_FOUR_TAG = {"SearchCommand", "backend"};


    // Bug 5
    public static final String BUG_FIVE_NAME = "Exit command hangs";
    public static final String BUG_FIVE_DESCRIPTION = "Exit command hangs more than 10 seconds";
    public static final String BUG_FIVE_NOTE = "this is the fifth note";

    // Bug 6
    public static final String BUG_SIX_NAME = "Note rendering";
    public static final String BUG_SIX_DESCRIPTION = "Note field does not show up in the main window";
    public static final String BUG_SIX_NOTE = "this is the sixth note";
    public static final String[] BUG_SIX_TAG = {"JavaFx", "Ui"};

    // Bug 7
    public static final String BUG_SEVEN_NAME = "Jar file does not work";
    public static final String BUG_SEVEN_DESCRIPTION = "Current jar file cannot work on Mac OS system";
    public static final String BUG_SEVEN_NOTE = "this is the seventh note";

    // Bug 8
    public static final String BUG_EIGHT_NAME = "Nested if statements";
    public static final String BUG_EIGHT_DESCRIPTION = "There are a lot of deep nested if blocks in Command package";

    // Bug 9
    public static final String BUG_NINE_NAME = "ArrayOutOfBounds Error";
    public static final String BUG_NINE_DESCRIPTION = "No input validation for invalid index";
    public static final String BUG_NINE_NOTE = "";

    public static final Bug BUGONE = new BugBuilder()
            .withName(BUG_ONE_NAME)
            .withDescription(BUG_ONE_DESCRIPTION)
            .withNote(BUG_ONE_NOTE)
            .withState(VALID_STATE_VALUE_TODO).withTags(BUG_ONE_TAG)
            .withPriority(VALID_PRIORITY_LOW)
            .build();

    public static final Bug BUGTWO = new BugBuilder()
            .withName(BUG_TWO_NAME)
            .withDescription(BUG_TWO_DESCRIPTION)
            .withNote(BUG_TWO_NOTE)
            .withState(VALID_STATE_VALUE_TODO)
            .withPriority()
            .withTags(BUG_TWO_TAG)
            .build();

    public static final Bug BUGTHREE = new BugBuilder()
            .withName(BUG_THREE_NAME)
            .withState(VALID_STATE_VALUE_DONE)
            .withDescription(BUG_THREE_DESCRIPTION)
            .withNote(BUG_THREE_NOTE)
            .withPriority(VALID_PRIORITY_LOW)
            .withTags(BUG_THREE_TAG)
            .build();

    public static final Bug BUGFOUR = new BugBuilder()
            .withName(BUG_FOUR_NAME)
            .withState(VALID_STATE_VALUE_ONGOING)
            .withDescription(BUG_FOUR_DESCRIPTION)
            .withNote(BUG_FOUR_NOTE)
            .withTags(BUG_FOUR_TAG)
            .withPriority()
            .build();

    public static final Bug BUGFIVE = new BugBuilder()
            .withName(BUG_FIVE_NAME)
            .withState(VALID_STATE_VALUE_DONE)
            .withDescription(BUG_FIVE_DESCRIPTION)
            .withNote(BUG_FIVE_NOTE)
            .withPriority(VALID_PRIORITY_MEDIUM)
            .build();

    public static final Bug BUGSIX = new BugBuilder()
            .withName(BUG_SIX_NAME)
            .withState(VALID_STATE_VALUE_ONGOING)
            .withDescription(BUG_SIX_DESCRIPTION)
            .withNote(BUG_SIX_NOTE)
            .withPriority(VALID_PRIORITY_LOW)
            .withTags(BUG_SIX_TAG)
            .build();

    public static final Bug BUGSEVEN = new BugBuilder()
            .withName(BUG_SEVEN_NAME)
            .withState(VALID_STATE_VALUE_BACKLOG)
            .withDescription(BUG_SEVEN_DESCRIPTION)
            .withNote(BUG_SEVEN_NOTE)
            .withPriority(VALID_PRIORITY_HIGH)
            .build();

    // Manually added - default state backlog
    public static final Bug BUGEIGHT = new BugBuilder()
            .withName(BUG_EIGHT_NAME)
            .withState(VALID_STATE_VALUE_BACKLOG)
            .withDescription(BUG_EIGHT_DESCRIPTION)
            .build();

    public static final Bug BUGNINE = new BugBuilder()
            .withName(BUG_NINE_NAME)
            .withState(VALID_STATE_VALUE_BACKLOG)
            .withDescription(BUG_NINE_DESCRIPTION)
            .withNote(BUG_NINE_NOTE)
            .build();

    // Manually added - Bug's details found in {@code CommandTestUtil}
    public static final Bug BUGTEN = new BugBuilder()
            .withName(VALID_NAME_PARSER)
            .withState(VALID_STATE_PARSER)
            .withDescription(VALID_DESCRIPTION_PARSER)
            .withNote(VALID_NOTE_PARSER)
            .withTags(VALID_TAG_LOGIC)
            .withPriority(VALID_PRIORITY_PARSER).build();

    public static final Bug BUGELEVEN = new BugBuilder()
            .withName(VALID_NAME_HOMEPAGE)
            .withState(VALID_STATE_HOMEPAGE)
            .withDescription(VALID_DESCRIPTION_HOMEPAGE)
            .withNote(VALID_NOTE_HOMEPAGE)
            .withPriority(VALID_PRIORITY_HOMEPAGE)
            .withTags(VALID_TAG_COMPONENT, VALID_TAG_LOGIC)
            .build();

    public static final Bug BUGTWELVE = new BugBuilder()
            .withName(VALID_NAME_HOMEPAGE)
            .withState(VALID_STATE_HOMEPAGE)
            .withDescription(VALID_DESCRIPTION_HOMEPAGE)
            .withPriority(VALID_PRIORITY_HOMEPAGE)
            .withNote(VALID_NOTE_BLANK)
            .withTags(VALID_TAG_COMPONENT, VALID_TAG_LOGIC)
            .build();

    private TypicalBugs() {} // prevents instantiation

    /**
     * Returns an {@code KanBugTracker} with all the typical bugs.
     */
    public static KanBugTracker getTypicalKanBugTracker() {
        KanBugTracker ab = new KanBugTracker();
        for (Bug bug : getTypicalBugs()) {
            ab.addBug(bug);
        }
        return ab;
    }

    public static List<Bug> getTypicalBugs() {
        return new ArrayList<>(Arrays.asList(BUGONE, BUGTWO, BUGTHREE, BUGFOUR, BUGFIVE, BUGSIX, BUGSEVEN));
    }
}
