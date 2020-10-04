package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyKanBugTracker;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private KanBugTrackerStorage kanBugTrackerStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(KanBugTrackerStorage kanBugTrackerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.kanBugTrackerStorage = kanBugTrackerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return kanBugTrackerStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyKanBugTracker> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(kanBugTrackerStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyKanBugTracker> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return kanBugTrackerStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyKanBugTracker addressBook) throws IOException {
        saveAddressBook(addressBook, kanBugTrackerStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyKanBugTracker addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        kanBugTrackerStorage.saveAddressBook(addressBook, filePath);
    }

}
