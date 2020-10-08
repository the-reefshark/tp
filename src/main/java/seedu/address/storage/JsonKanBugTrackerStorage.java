package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyKanBugTracker;

/**
 * A class to access KanBugTracker data stored as a json file on the hard disk.
 */
public class JsonKanBugTrackerStorage implements KanBugTrackerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonKanBugTrackerStorage.class);

    private Path filePath;

    public JsonKanBugTrackerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getKanBugTrackerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyKanBugTracker> readKanBugTracker() throws DataConversionException {
        return readKanBugTracker(filePath);
    }

    /**
     * Similar to {@link #readKanBugTracker()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyKanBugTracker> readKanBugTracker(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableKanBugTracker> jsonKanBugTracker = JsonUtil.readJsonFile(
                filePath, JsonSerializableKanBugTracker.class);
        if (!jsonKanBugTracker.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonKanBugTracker.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveKanBugTracker(ReadOnlyKanBugTracker kanBugTracker) throws IOException {
        saveKanBugTracker(kanBugTracker, filePath);
    }

    /**
     * Similar to {@link #saveKanBugTracker(ReadOnlyKanBugTracker)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveKanBugTracker(ReadOnlyKanBugTracker kanBugTracker, Path filePath) throws IOException {
        requireNonNull(kanBugTracker);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableKanBugTracker(kanBugTracker), filePath);
    }

}
