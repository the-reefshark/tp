package seedu.address.testutil;

import seedu.address.model.KanBugTracker;
import seedu.address.model.bug.Bug;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new KanBugTrackerBuilder().withBug("John", "Doe").build();}
 */
public class KanBugTrackerBuilder {

    private KanBugTracker kanBugTracker;

    public KanBugTrackerBuilder() {
        kanBugTracker = new KanBugTracker();
    }

    public KanBugTrackerBuilder(KanBugTracker kanBugTracker) {
        this.kanBugTracker = kanBugTracker;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public KanBugTrackerBuilder withBug(Bug bug) {
        kanBugTracker.addBug(bug);
        return this;
    }

    public KanBugTracker build() {
        return kanBugTracker;
    }
}
