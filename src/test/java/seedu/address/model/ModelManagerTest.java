package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUGS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBugs.BUGONE;
import static seedu.address.testutil.TypicalBugs.BUGTWO;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.bug.NameContainsKeywordsPredicate;
import seedu.address.testutil.KanBugTrackerBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new KanBugTracker(), new KanBugTracker(modelManager.getKanBugTracker()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setKanBugTrackerFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setKanBugTrackerFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setKanBugTrackerFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setKanBugTrackerFilePath(null));
    }

    @Test
    public void setKanBugTrackerFilePath_validPath_setsKanBugTrackerFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setKanBugTrackerFilePath(path);
        assertEquals(path, modelManager.getKanBugTrackerFilePath());
    }

    @Test
    public void hasBug_nullBug_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasBug(null));
    }

    @Test
    public void hasBug_bugNotInKanBugTracker_returnsFalse() {
        assertFalse(modelManager.hasBug(BUGONE));
    }

    @Test
    public void hasBug_bugInKanBugTracker_returnsTrue() {
        modelManager.addBug(BUGONE);
        assertTrue(modelManager.hasBug(BUGONE));
    }

    @Test
    public void getFilteredBugList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredBugList().remove(0));
    }

    @Test
    public void equals() {
        KanBugTracker kanBugTracker = new KanBugTrackerBuilder().withBug(BUGONE).withBug(BUGTWO).build();
        KanBugTracker differentKanBugTracker = new KanBugTracker();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(kanBugTracker, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(kanBugTracker, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different kanBugTracker -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentKanBugTracker, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = BUGONE.getName().fullName.split("\\s+");
        modelManager.updateFilteredBugList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(kanBugTracker, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredBugList(PREDICATE_SHOW_ALL_BUGS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setKanBugTrackerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(kanBugTracker, differentUserPrefs)));
    }

}
