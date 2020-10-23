package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBugs.BUGEIGHT;
import static seedu.address.testutil.TypicalBugs.BUGFIVE;
import static seedu.address.testutil.TypicalBugs.BUGFOUR;
import static seedu.address.testutil.TypicalBugs.BUGNINE;
import static seedu.address.testutil.TypicalBugs.BUGONE;
import static seedu.address.testutil.TypicalBugs.BUGSEVEN;
import static seedu.address.testutil.TypicalBugs.BUGSIX;
import static seedu.address.testutil.TypicalBugs.BUGTEN;
import static seedu.address.testutil.TypicalBugs.BUGTHREE;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.State;
import seedu.address.storage.JsonKanBugTrackerStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

public class LogicManagerListTest {
    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonKanBugTrackerStorage kanBugTrackerStorage =
            new JsonKanBugTrackerStorage(temporaryFolder.resolve("kanbugtracker.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(kanBugTrackerStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @BeforeEach
    public void addBugs() {
        model.addBug(BUGONE);
        model.addBug(BUGTHREE);
        model.addBug(BUGFOUR);
        model.addBug(BUGFIVE);
        model.addBug(BUGSIX);
        model.addBug(BUGSEVEN);
        model.addBug(BUGEIGHT);
        model.addBug(BUGNINE);
        model.addBug(BUGTEN);
    }

    @Test
    public void filteredBugListTestSameState() {
        ObservableList<Bug> todoList = logic.getFilteredBugListByState("todo");
        ObservableList<Bug> backlogList = logic.getFilteredBugListByState("backlog");
        ObservableList<Bug> ongoingList = logic.getFilteredBugListByState("ongoing");
        ObservableList<Bug> doneList = logic.getFilteredBugListByState("done");
        assertStateEquals(todoList, new State("todo"));
        assertStateEquals(backlogList, new State("backlog"));
        assertStateEquals(doneList, new State("done"));
        assertStateEquals(ongoingList, new State("ongoing"));
    }

    @Test
    public void filterBugListTestIncorrectState() {
        ObservableList<Bug> allStateList = logic.getFilteredBugList();
        ObservableList<Bug> todoList = logic.getFilteredBugListByState("todo");
        ObservableList<Bug> ongoingList = logic.getFilteredBugListByState("ongoing");
        assetStateNotEquals(allStateList, new State("todo"));
        assetStateNotEquals(allStateList, new State("backlog"));
        assetStateNotEquals(allStateList, new State("ongoing"));
        assetStateNotEquals(allStateList, new State("done"));
        assetStateNotEquals(todoList, new State("ongoing"));
        assetStateNotEquals(todoList, new State("backlog"));
        assetStateNotEquals(todoList, new State("done"));
        assetStateNotEquals(ongoingList, new State("backlog"));
        assetStateNotEquals(ongoingList, new State("todo"));
        assetStateNotEquals(ongoingList, new State("backlog"));
    }

    private void assertStateEquals(ObservableList<Bug> list, State state) {
        for (Bug bug: list) {
            assertEquals(bug.getState(), state);
        }
    }

    private void assetStateNotEquals(ObservableList<Bug> list, State state) {
        boolean hasOtherState = false;
        for (Bug bug: list) {
            if (!bug.getState().equals(state)) {
                hasOtherState = true;
            }
        }
        assertTrue(hasOtherState);
    }
}
