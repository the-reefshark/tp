package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.KanBugTracker;
import seedu.address.testutil.TypicalBugs;

public class JsonSerializableKanBugTrackerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableKanBugTracker dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableKanBugTracker.class).get();
        KanBugTracker kanBugTrackerFromFile = dataFromFile.toModelType();
        KanBugTracker typicalPersonsKanBugTracker = TypicalBugs.getTypicalAddressBook();
        assertEquals(kanBugTrackerFromFile, typicalPersonsKanBugTracker);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableKanBugTracker dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableKanBugTracker.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableKanBugTracker dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableKanBugTracker.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableKanBugTracker.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
