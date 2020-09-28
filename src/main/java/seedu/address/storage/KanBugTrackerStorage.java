package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.KanBugTracker;
import seedu.address.model.ReadOnlyKanBugTracker;

/**
 * Represents a storage for {@link KanBugTracker}.
 */
public interface KanBugTrackerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyKanBugTracker}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyKanBugTracker> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyKanBugTracker> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyKanBugTracker} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyKanBugTracker addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyKanBugTracker)
     */
    void saveAddressBook(ReadOnlyKanBugTracker addressBook, Path filePath) throws IOException;

}
