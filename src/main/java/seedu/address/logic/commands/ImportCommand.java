package seedu.address.logic.commands;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyKanBugTracker;
import seedu.address.storage.JsonKanBugTrackerStorage;

/**
 * Allows the user to import KanBugTracker data from a different file
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Imports the file path identified into the"
            + "KanBug Tracker";
    public static final String MESSAGE_IMPORT_SUCCESS = "File import successful";
    public static final String MESSAGE_IMPORT_FAILURE = "The selected file is invalid";
    public static final String MESSAGE_BLANK_INPUT = "Please input a valid file name";
    public static final String MESSAGE_FILE_CONSTRAINTS = "Please select a valid .json file";

    private Path filePath;

    public ImportCommand(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            JsonKanBugTrackerStorage importedKanBugTracker = new JsonKanBugTrackerStorage(filePath);
            Optional<ReadOnlyKanBugTracker> readOnlyOptionalKanBugTracker = importedKanBugTracker.readKanBugTracker();
            if (readOnlyOptionalKanBugTracker.isEmpty()) {
                throw new CommandException(MESSAGE_IMPORT_FAILURE);
            }
            model.setKanBugTrackerFilePath(filePath);
            model.setKanBugTracker(readOnlyOptionalKanBugTracker.get());
            return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS, filePath.getFileName()));
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_IMPORT_FAILURE);
        }
    }
}
