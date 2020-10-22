package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditBugDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bug.Priority;
import seedu.address.model.bug.Note;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
      
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_STATE, PREFIX_DESCRIPTION,
                        PREFIX_NOTE, PREFIX_TAG, PREFIX_PRIORITY);
      
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditBugDescriptor editBugDescriptor = new EditBugDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            assert argMultimap.getValue(PREFIX_NAME).get() != null;
            editBugDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_STATE).isPresent()) {
            editBugDescriptor.setState(ParserUtil.parseState(argMultimap.getValue(PREFIX_STATE).get()));
        }
        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            editBugDescriptor.setPriority(parsePriorityForEdit(argMultimap.getValue(PREFIX_PRIORITY).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editBugDescriptor.setDescription(
                    ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            String editedNoteContent = argMultimap.getValue(PREFIX_NOTE).get();
            if (editedNoteContent.isBlank()) {
                editBugDescriptor.setOptionalNote(Optional.empty());
            }
            editBugDescriptor.setOptionalNote(Optional.of(ParserUtil.parseNote(editedNoteContent)));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editBugDescriptor::setTags);

        if (!editBugDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editBugDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }

        assert (!tags.isEmpty() == true);
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Parses {@code String priority} into a {@code Priority} if {@conde priority} is a non-empty string.
     * If {@code priority} is an empty string, it will be parsed into a 'null' priority.
     */
    private Priority parsePriorityForEdit(String priority) throws ParseException {
        assert priority != null;
        if (priority.equals("")) {
            return new Priority();
        }
        return ParserUtil.parsePriority(priority);
    }

}
