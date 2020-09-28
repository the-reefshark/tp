package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
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
            .withDescription("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withTags("friends").build();
    public static final Bug BENSON = new BugBuilder().withName("Benson Meier")
            .withDescription("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withTags("owesMoney", "friends").build();
    public static final Bug CARL = new BugBuilder().withName("Carl Kurz")
            .withEmail("heinz@example.com").withDescription("wall street").build();
    public static final Bug DANIEL = new BugBuilder().withName("Daniel Meier")
            .withEmail("cornelia@example.com").withDescription("10th street").withTags("friends").build();
    public static final Bug ELLE = new BugBuilder().withName("Elle Meyer")
            .withEmail("werner@example.com").withDescription("michegan ave").build();
    public static final Bug FIONA = new BugBuilder().withName("Fiona Kunz")
            .withEmail("lydia@example.com").withDescription("little tokyo").build();
    public static final Bug GEORGE = new BugBuilder().withName("George Best")
            .withEmail("anna@example.com").withDescription("4th street").build();

    // Manually added
    public static final Bug HOON = new BugBuilder().withName("Hoon Meier")
            .withEmail("stefan@example.com").withDescription("little india").build();
    public static final Bug IDA = new BugBuilder().withName("Ida Mueller")
            .withEmail("hans@example.com").withDescription("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Bug AMY = new BugBuilder().withName(VALID_NAME_AMY)
            .withEmail(VALID_EMAIL_AMY).withDescription(VALID_DESCRIPTION_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Bug BOB = new BugBuilder().withName(VALID_NAME_BOB)
            .withEmail(VALID_EMAIL_BOB).withDescription(VALID_DESCRIPTION_BOB)
                                          .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalBugs() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static KanBugTracker getTypicalAddressBook() {
        KanBugTracker ab = new KanBugTracker();
        for (Bug bug : getTypicalPersons()) {
            ab.addBug(bug);
        }
        return ab;
    }

    public static List<Bug> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
