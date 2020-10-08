package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBugs.ALICE;
import static seedu.address.testutil.TypicalBugs.HOON;
import static seedu.address.testutil.TypicalBugs.IDA;
import static seedu.address.testutil.TypicalBugs.getTypicalKanBugTracker;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.KanBugTracker;
import seedu.address.model.ReadOnlyKanBugTracker;

public class JsonKanBugTrackerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonKanBugTrackerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readKanBugTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readKanBugTracker(null));
    }

    private java.util.Optional<ReadOnlyKanBugTracker> readKanBugTracker(String filePath) throws Exception {
        return new JsonKanBugTrackerStorage(Paths.get(filePath))
                .readKanBugTracker(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readKanBugTracker("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readKanBugTracker("notJsonFormatKanBugTracker.json"));
    }

    @Test
    public void readKanBugTracker_invalidBugKanBugTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readKanBugTracker("invalidBugKanBugTracker.json"));
    }

    @Test
    public void readKanBugTracker_invalidAndValidBugKanBugTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readKanBugTracker("invalidAndValidBugKanBugTracker.json"));
    }

    @Test
    public void readAndSaveKanBugTracker_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempKanBugTracker.json");
        KanBugTracker original = getTypicalKanBugTracker();
        JsonKanBugTrackerStorage jsonKanBugTrackerStorage = new JsonKanBugTrackerStorage(filePath);

        // Save in new file and read back
        jsonKanBugTrackerStorage.saveKanBugTracker(original, filePath);
        ReadOnlyKanBugTracker readBack = jsonKanBugTrackerStorage.readKanBugTracker(filePath).get();
        assertEquals(original, new KanBugTracker(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addBug(HOON);
        original.removeBug(ALICE);
        jsonKanBugTrackerStorage.saveKanBugTracker(original, filePath);
        readBack = jsonKanBugTrackerStorage.readKanBugTracker(filePath).get();
        assertEquals(original, new KanBugTracker(readBack));

        // Save and read without specifying file path
        original.addBug(IDA);
        jsonKanBugTrackerStorage.saveKanBugTracker(original); // file path not specified
        readBack = jsonKanBugTrackerStorage.readKanBugTracker().get(); // file path not specified
        assertEquals(original, new KanBugTracker(readBack));

    }

    @Test
    public void saveKanBugTracker_nullKanBugTracker_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveKanBugTracker(null, "SomeFile.json"));
    }

    /**
     * Saves {@code kanBugTracker} at the specified {@code filePath}.
     */
    private void saveKanBugTracker(ReadOnlyKanBugTracker kanBugTracker, String filePath) {
        try {
            new JsonKanBugTrackerStorage(Paths.get(filePath))
                    .saveKanBugTracker(kanBugTracker, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveKanBugTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveKanBugTracker(new KanBugTracker(), null));
    }
}
