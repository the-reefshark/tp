package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyKanBugTracker;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends KanBugTrackerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getKanBugTrackerFilePath();

    @Override
    Optional<ReadOnlyKanBugTracker> readKanBugTracker() throws DataConversionException, IOException;

    @Override
    void saveKanBugTracker(ReadOnlyKanBugTracker kanBugTracker) throws IOException;

}
