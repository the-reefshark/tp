package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_BLANK_INPUT;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_FILE_CONSTRAINTS;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    @Override
    public ImportCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        String strippedInput = userInput.strip();
        if (strippedInput.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_BLANK_INPUT));
        }
        if (!strippedInput.endsWith(".json")) {
            throw new ParseException(MESSAGE_FILE_CONSTRAINTS);
        }
        Path filePath = Paths.get("data" , strippedInput);
        return new ImportCommand(filePath);
    }
}
