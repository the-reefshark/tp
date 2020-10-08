package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.bug.Bug;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final KanBugTracker kanBugTracker;
    private final UserPrefs userPrefs;
    private final FilteredList<Bug> filteredBugs;

    /**
     * Initializes a ModelManager with the given kanBugTracker and userPrefs.
     */
    public ModelManager(ReadOnlyKanBugTracker kanBugTracker, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(kanBugTracker, userPrefs);

        logger.fine("Initializing with address book: " + kanBugTracker + " and user prefs " + userPrefs);

        this.kanBugTracker = new KanBugTracker(kanBugTracker);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredBugs = new FilteredList<>(this.kanBugTracker.getBugList());
    }

    public ModelManager() {
        this(new KanBugTracker(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getKanBugTrackerFilePath() {
        return userPrefs.getKanBugTrackerFilePath();
    }

    @Override
    public void setKanBugTrackerFilePath(Path kanBugTrackerFilePath) {
        requireNonNull(kanBugTrackerFilePath);
        userPrefs.setKanBugTrackerFilePath(kanBugTrackerFilePath);
    }

    //=========== KanBugTracker ================================================================================

    @Override
    public void setKanBugTracker(ReadOnlyKanBugTracker kanBugTracker) {
        this.kanBugTracker.resetData(kanBugTracker);
    }

    @Override
    public ReadOnlyKanBugTracker getKanBugTracker() {
        return kanBugTracker;
    }

    @Override
    public boolean hasBug(Bug bug) {
        requireNonNull(bug);
        return kanBugTracker.hasBug(bug);
    }

    @Override
    public void deleteBug(Bug target) {
        kanBugTracker.removeBug(target);
    }

    @Override
    public void addBug(Bug bug) {
        kanBugTracker.addBug(bug);
        updateFilteredBugList(PREDICATE_SHOW_ALL_BUGS);
    }

    @Override
    public void setBug(Bug target, Bug editedBug) {
        requireAllNonNull(target, editedBug);

        kanBugTracker.setBug(target, editedBug);
    }

    //=========== Filtered Bug List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Bug} backed by the internal list of
     * {@code versionedKanBugTracker}
     */
    @Override
    public ObservableList<Bug> getFilteredBugList() {
        return filteredBugs;
    }

    @Override
    public void updateFilteredBugList(Predicate<Bug> predicate) {
        requireNonNull(predicate);
        filteredBugs.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return kanBugTracker.equals(other.kanBugTracker)
                && userPrefs.equals(other.userPrefs)
                && filteredBugs.equals(other.filteredBugs);
    }

}
