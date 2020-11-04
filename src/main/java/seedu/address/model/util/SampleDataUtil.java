package seedu.address.model.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.KanBugTracker;
import seedu.address.model.ReadOnlyKanBugTracker;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.Description;
import seedu.address.model.bug.Name;
import seedu.address.model.bug.Note;
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
                Optional.of(new Note("Tried to resize in JavaFX did not work. First spotted when switching modes and"
                        + "making screen larger")), getTagSet("UI"), new Priority("low")),
            new Bug(new Name("AddCommandParser"), new State("todo"),
                new Description("Parser to add command insert incorrect input"),
                Optional.empty(), getTagSet("Parser", "Add"), new Priority("medium")),
            new Bug(new Name("Delete command"), new State("done"),
                new Description("Delete command removes incorrect index"),
                Optional.of(new Note("Command accidentally removes the first bug every time command is executed "
                        + "instead of the bug provided")), getTagSet("Delete"), new Priority("high")),
            new Bug(new Name("Exit command"), new State("ongoing"),
                new Description("Data is not being saved"),
                Optional.empty(), getTagSet("Storage", "Exit"), new Priority("high")),
            new Bug(new Name("Help Command"), new State("done"),
                new Description("Help command does not appear when executed"),
                Optional.empty(), getTagSet("help"), new Priority("low")),
            new Bug(new Name("List command"), new State("backlog"),
                new Description("List command does not show the full list"),
                Optional.empty(), getTagSet("List"), new Priority("medium")),
            new Bug(new Name("Load file error"), new State("backlog"),
                new Description("Unable to load files from previous run"),
                Optional.empty(), getTagSet("Load", "Storage"), new Priority("high"))
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
