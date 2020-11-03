package seedu.address.logic.parser.exceptions;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;

public class ExtraColumnException extends IllegalValueException {
    public ExtraColumnException() {
        super(Messages.MESSAGE_REMOVE_COLUMN);
    }
}
