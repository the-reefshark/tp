package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.KanBugTracker;
import seedu.address.model.ReadOnlyKanBugTracker;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.Priority;
import seedu.address.model.bug.State;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code KanBugTracker} with sample data.
 */
public class SampleDataUtil {
    public static Bug[] getSampleBugs() {
        return new Bug[]{
            new Bug(new Name("UI Homepage bug"), new State("backlog"),
                new Description("Homepage UI does not scale correctly"),
                getTagSet("UI"), new Priority()),
            new Bug(new Name("AddCommandParser"), new State("todo"),
                new Description("Parser to add command insert incorrect input"),
                getTagSet("Parser", "Add"), new Priority()),
            new Bug(new Name("Delete command"), new State("done"),
                new Description("Delete command removes incorrect index"),
                getTagSet("Delete"), new Priority()),
            new Bug(new Name("Exit command"), new State("ongoing"),
                new Description("Data is not being saved"),
                getTagSet("Storage", "Exit"), new Priority()),
            new Bug(new Name("Help Command"), new State("done"),
                new Description("Help command does not appear when executed"),
                getTagSet("help"), new Priority()),
            new Bug(new Name("List command"), new State("backlog"),
                new Description("List command does not show the full list"),
                getTagSet("List"), new Priority())
        };
    }

    public static ReadOnlyKanBugTracker getSampleKanBugTracker() {
        KanBugTracker sampleAb = new KanBugTracker();
        for (Bug sampleBug : getSampleBugs()) {
            sampleAb.addBug(sampleBug);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                       .map(Tag::new)
                       .collect(Collectors.toSet());
    }
}
