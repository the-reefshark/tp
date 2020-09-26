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
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.bug.Bug;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Bug validBug = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validBug).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validBug), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBug), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Bug validBug = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validBug);
        ModelStub modelStub = new ModelStubWithPerson(validBug);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Bug alice = new PersonBuilder().withName("Alice").build();
        Bug bob = new PersonBuilder().withName("Bob").build();
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

        // different bug -> returns false
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Bug bug) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Bug bug) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Bug target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Bug target, Bug editedBug) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Bug> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Bug> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single bug.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Bug bug;

        ModelStubWithPerson(Bug bug) {
            requireNonNull(bug);
            this.bug = bug;
        }

        @Override
        public boolean hasPerson(Bug bug) {
            requireNonNull(bug);
            return this.bug.isSamePerson(bug);
        }
    }

    /**
     * A Model stub that always accept the bug being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Bug> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Bug bug) {
            requireNonNull(bug);
            return personsAdded.stream().anyMatch(bug::isSamePerson);
        }

        @Override
        public void addPerson(Bug bug) {
            requireNonNull(bug);
            personsAdded.add(bug);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
