package seedu.address.model.bug;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

public class BugContainsQueryStringPredicate implements Predicate<Bug> {
    private final String queryString;

    public BugContainsQueryStringPredicate(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public boolean test(Bug bug) {
        return StringUtil.containsQueryStringIgnoreCase(bug.getName().fullName, queryString)
                || StringUtil.containsQueryStringIgnoreCase(bug.getDescription().value, queryString)
                || bug.getTags().stream()
                .anyMatch(tag -> StringUtil.containsQueryStringIgnoreCase(tag.tagName, queryString));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // shortcut circuit if the same object
                || (other instanceof BugContainsQueryStringPredicate) // instanceof handles nulls
                && queryString.equals(((BugContainsQueryStringPredicate) other).queryString); // state check
    }
}
