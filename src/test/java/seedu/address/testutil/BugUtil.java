package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditBugDescriptor;
import seedu.address.model.bug.Bug;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Bug.
 */
public class BugUtil {

    /**
     * Returns an add command string for adding the {@code bug}.
     */
    public static String getAddCommand(Bug bug) {
        return AddCommand.COMMAND_WORD + " " + getBugDetails(bug);
    }

    /**
     * Returns the part of command string for the given {@code bug}'s details.
     */
    public static String getBugDetails(Bug bug) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + bug.getName().fullName + " ");
        sb.append(PREFIX_STATE + bug.getState().toString() + " ");
        sb.append(PREFIX_DESCRIPTION + bug.getDescription().value + " ");
        bug.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditBugDescriptor}'s details.
     */
    public static String getEditBugDescriptorDetails(EditBugDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getState().ifPresent(state -> sb.append(PREFIX_STATE).append(state.value).append(" "));
        descriptor.getDescription().ifPresent(address -> sb.append(PREFIX_DESCRIPTION)
                                                                 .append(address.value).append(" "));
        descriptor.getPriority().ifPresent(priority -> sb.append(PREFIX_PRIORITY)
                .append(priority.isNull() ? "" : priority.priority).append(" "));

        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
