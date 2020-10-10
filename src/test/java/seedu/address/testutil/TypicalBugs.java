package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_HOMEPAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATE_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.KanBugTracker;
import seedu.address.model.bug.Bug;

/**
 * A utility class containing a list of {@code Bug} objects to be used in tests.
 */
public class TypicalBugs {

    public static final Bug ALICE = new BugBuilder().withName("Alice Pauline")
            .withDescription("123, Jurong West Ave 6, #08-111").withState("todo")
            .withTags("friends").build();
    public static final Bug BENSON = new BugBuilder().withName("Benson Meier")
            .withDescription("311, Clementi Ave 2, #02-25")
            .withState("todo")
            .withTags("owesMoney", "friends").build();
    public static final Bug CARL = new BugBuilder().withName("Carl Kurz")
            .withState("done").withDescription("wall street").build();
    public static final Bug DANIEL = new BugBuilder().withName("Daniel Meier")
            .withState("ongoing").withDescription("10th street").withTags("friends").build();
    public static final Bug ELLE = new BugBuilder().withName("Elle Meyer")
            .withState("done").withDescription("michegan ave").build();
    public static final Bug FIONA = new BugBuilder().withName("Fiona Kunz")
            .withState("ongoing").withDescription("little tokyo").build();
    public static final Bug GEORGE = new BugBuilder().withName("George Best")
            .withState("backlog").withDescription("4th street").build();

    // Manually added
    public static final Bug HOON = new BugBuilder().withName("Hoon Meier")
            .withState("backlog").withDescription("little india").build();
    public static final Bug IDA = new BugBuilder().withName("Ida Mueller")
            .withState("backlog").withDescription("chicago ave").build();

    // Manually added - Bug's details found in {@code CommandTestUtil}
    public static final Bug AMY = new BugBuilder().withName(VALID_NAME_PARSER)
            .withState(VALID_STATE_PARSER).withDescription(VALID_DESCRIPTION_PARSER).withTags(VALID_TAG_FRIEND).build();
    public static final Bug BOB = new BugBuilder().withName(VALID_NAME_HOMEPAGE)
            .withState(VALID_STATE_HOMEPAGE).withDescription(VALID_DESCRIPTION_HOMEPAGE)
                                          .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
