package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.KanBugTracker;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyKanBugTracker;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.bug.Bug;
import seedu.address.testutil.BugBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBugAdded modelStub = new ModelStubAcceptingBugAdded();
        Bug validBug = new BugBuilder().build();

        CommandResult commandResult = new AddCommand(validBug).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validBug), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBug), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Bug validBug = new BugBuilder().build();
        AddCommand addCommand = new AddCommand(validBug);
        ModelStub modelStub = new ModelStubWithBug(validBug);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Bug alice = new BugBuilder().withName("Alice").build();
        Bug bob = new BugBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getKanBugTrackerFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setKanBugTrackerFilePath(Path kanBugTrackerFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBug(Bug bug) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setKanBugTracker(ReadOnlyKanBugTracker newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyKanBugTracker getKanBugTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBug(Bug bug) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBug(Bug target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBug(Bug target, Bug editedBug) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Bug> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBugList(Predicate<Bug> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithBug extends ModelStub {
        private final Bug bug;

        ModelStubWithBug(Bug bug) {
            requireNonNull(bug);
            this.bug = bug;
        }

        @Override
        public boolean hasBug(Bug bug) {
            requireNonNull(bug);
            return this.bug.isSamePerson(bug);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingBugAdded extends ModelStub {
        final ArrayList<Bug> personsAdded = new ArrayList<>();

        @Override
        public boolean hasBug(Bug bug) {
            requireNonNull(bug);
            return personsAdded.stream().anyMatch(bug::isSamePerson);
        }

        @Override
        public void addBug(Bug bug) {
            requireNonNull(bug);
            personsAdded.add(bug);
        }

        @Override
        public ReadOnlyKanBugTracker getKanBugTracker() {
            return new KanBugTracker();
        }
    }

}
