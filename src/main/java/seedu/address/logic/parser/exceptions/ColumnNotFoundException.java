package seedu.address.logic.parser.exceptions;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;

public class ColumnNotFoundException extends IllegalValueException {
    public ColumnNotFoundException() {
        super(Messages.MESSAGE_PROVIDE_COLUMN);
    }
}
