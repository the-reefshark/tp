package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_BLANK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPONENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.KanBugTracker;
import seedu.address.model.bug.Bug;

/**
 * A utility class containing a list of {@code Bug} objects to be used in tests.
 */
public class TypicalBugs {

    public static final Bug BUGONE = new BugBuilder()
            .withName("Add command bug")
            .withDescription("Cannot add a bug with multiple tags")
            .withNote("this is a note")
            .withState("todo").withTags("AddCommand")
            .withPriority("low")
            .build();

    public static final Bug BUGTWO = new BugBuilder()
            .withName("Edit command parsing")
            .withDescription("The prefixes of edit command are wrongly parsed")
            .withNote("this is the second note")
            .withState("todo")
            .withPriority()
            .withTags("EditCommand", "backend")
            .build();

    public static final Bug BUGTHREE = new BugBuilder()
            .withName("Main window overflows")
            .withState("done").withDescription("If there is a huge number of bugs, the main window cannot display")
            .withNote("this is the third note")
            .withPriority("low")
            .withTags("Ui")
            .build();

    public static final Bug BUGFOUR = new BugBuilder()
            .withName("Search command test")
            .withState("ongoing")
            .withDescription("Need to add more search test to cover all equivalence partition")
            .withNote("this is the fourth note")
            .withTags("SearchCommand", "backend")
            .withPriority()
            .build();

    public static final Bug BUGFIVE = new BugBuilder()
            .withName("Exit command hangs")
            .withState("done")
            .withDescription("Exit command hangs more than 10 seconds")
            .withNote("this is the fifth note")
            .withPriority("medium")
            .build();

    public static final Bug BUGSIX = new BugBuilder()
            .withName("Note rendering")
            .withState("ongoing")
            .withDescription("Note field does not show up in the main window")
            .withNote("this is the sixth note")
            .withPriority("low")
            .withTags("JavaFx", "Ui")
            .build();
    public static final Bug BUGSEVEN = new BugBuilder()
            .withName("Jar file does not work")
            .withState("backlog")
            .withDescription("Current jar file cannot work on Mac OS system")
            .withNote("this is the seventh note")
            .withPriority("high")
            .build();

    // Manually added - default state backlog
    public static final Bug BUGEIGHT = new BugBuilder()
            .withName("Nested if statements")
            .withState("backlog")
            .withDescription("There are a lot of deep nested if blocks in Command package")
            .build();

    public static final Bug BUGNINE = new BugBuilder()
            .withName("ArrayOutOfBounds Error")
            .withState("backlog")
            .withDescription("No input validation for invalid index")
            .withNote("").build();

    // Manually added - Bug's details found in {@code CommandTestUtil}
    public static final Bug BUGTEN = new BugBuilder()
            .withName(VALID_NAME_PARSER)
            .withState(VALID_STATE_PARSER)
            .withDescription(VALID_DESCRIPTION_PARSER)
            .withNote(VALID_NOTE_PARSER)
            .withTags(VALID_TAG_FRIEND)
            .withPriority(VALID_PRIORITY_PARSER).build();

    public static final Bug BUGELEVEN = new BugBuilder()
            .withName(VALID_NAME_HOMEPAGE)
            .withState(VALID_STATE_HOMEPAGE)
            .withDescription(VALID_DESCRIPTION_HOMEPAGE)
            .withNote(VALID_NOTE_HOMEPAGE)
            .withPriority(VALID_PRIORITY_HOMEPAGE)
            .withTags(VALID_TAG_COMPONENT, VALID_TAG_FRIEND)
            .build();

    public static final Bug BUGTWELVE = new BugBuilder()
            .withName(VALID_NAME_HOMEPAGE)
            .withState(VALID_STATE_HOMEPAGE)
            .withDescription(VALID_DESCRIPTION_HOMEPAGE)
            .withPriority(VALID_PRIORITY_HOMEPAGE)
            .withNote(VALID_NOTE_BLANK)
            .withTags(VALID_TAG_COMPONENT, VALID_TAG_FRIEND)
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
