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

    public static final Bug BUGONE = new BugBuilder().withName("Alice Pauline")
            .withDescription("123, Jurong West Ave 6, #08-111").withNote("this is a note")
            .withState("todo").withTags("friends").withPriority("low").build();
    public static final Bug BUGTWO = new BugBuilder().withName("Benson Meier")
            .withDescription("311, Clementi Ave 2, #02-25").withNote("this is the second note")
            .withState("todo").withPriority().withTags("owesMoney", "friends").build();
    public static final Bug BUGTHREE = new BugBuilder().withName("Carl Kurz")
            .withState("done").withDescription("wall street").withNote("this is the third note")
            .withPriority("low").build();
    public static final Bug BUGFOUR = new BugBuilder().withName("Daniel Meier")
            .withState("ongoing").withDescription("10th street").withNote("this is the fourth note")
            .withTags("friends").withPriority().build();
    public static final Bug BUGFIVE = new BugBuilder().withName("Elle Meyer")
            .withState("done").withDescription("michegan ave").withNote("this is the fifth note")
            .withPriority("medium").build();
    public static final Bug BUGSIX = new BugBuilder().withName("Fiona Kunz")
            .withState("ongoing").withDescription("little tokyo").withNote("this is the sixth note")
            .withPriority("low").build();
    public static final Bug BUGSEVEN = new BugBuilder().withName("George Best")
            .withState("backlog").withDescription("4th street").withNote("this is the seventh note")
            .withPriority("high").build();

    // Manually added - default state backlog
    public static final Bug BUGEIGHT = new BugBuilder().withName("Hoon Meier")
            .withState("backlog").withDescription("little india").build();
    public static final Bug BUGNINE = new BugBuilder().withName("ArrayOutOfBounds Error").withState("backlog")
            .withDescription("No input validation for invalid index").withNote("").build();

    // Manually added - Bug's details found in {@code CommandTestUtil}
    public static final Bug BUGTEN = new BugBuilder().withName(VALID_NAME_PARSER)
            .withState(VALID_STATE_PARSER).withDescription(VALID_DESCRIPTION_PARSER).withNote(VALID_NOTE_PARSER)
            .withTags(VALID_TAG_FRIEND).withPriority(VALID_PRIORITY_PARSER).build();
    public static final Bug BUGELEVEN = new BugBuilder().withName(VALID_NAME_HOMEPAGE)
            .withState(VALID_STATE_HOMEPAGE).withDescription(VALID_DESCRIPTION_HOMEPAGE).withNote(VALID_NOTE_HOMEPAGE)
            .withPriority(VALID_PRIORITY_HOMEPAGE).withTags(VALID_TAG_COMPONENT, VALID_TAG_FRIEND).build();
    public static final Bug BUGTWELVE = new BugBuilder().withName(VALID_NAME_HOMEPAGE)
            .withState(VALID_STATE_HOMEPAGE).withDescription(VALID_DESCRIPTION_HOMEPAGE)
            .withPriority(VALID_PRIORITY_HOMEPAGE).withNote(VALID_NOTE_BLANK)
            .withTags(VALID_TAG_COMPONENT, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

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
