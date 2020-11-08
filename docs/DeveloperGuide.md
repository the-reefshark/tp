---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}
--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<img src="images/ArchitectureDiagram.png" width="450" />

The ***Architecture Diagram*** given above explains the high-level design of the App. Given below is a quick overview of each component.

**`Main`** has two classes called [`Main`](https://github.com/AY2021S1-CS2103T-W17-1/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2021S1-CS2103T-W17-1/tp/blob/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes clean-up methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components,

* defines its *API* in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![Class Diagram of the Logic Component](images/LogicClassDiagram.png)

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

The sections below give more details of each component.

### UI component

![Structure of the UI Component](images/UiClassDiagram.png)

**API** :
[`Ui.java`](https://github.com/AY2021S1-CS2103T-W17-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `BugListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2021S1-CS2103T-W17-1/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2021S1-CS2103T-W17-1/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Listens for changes to `Model` data so that the UI can be updated with the modified data.

### Logic component

![Structure of the Logic Component](images/LogicClassDiagram.png)

**API** :
[`Logic.java`](https://github.com/AY2021S1-CS2103T-W17-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

1. `Logic` uses the `KanBugTrackerParser` class to parse the user command.
1. This results in a `Command` object which is executed by the `LogicManager`.
1. The command execution can affect the `Model` (e.g. adding a bug).
1. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.
1. In addition, the `CommandResult` object can also instruct the `Ui` to perform certain actions, such as displaying help to the user.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: Note: The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

### Model component

![Structure of the Model Component](images/ModelClassDiagram.png)

**API** : [`Model.java`](https://github.com/AY2021S1-CS2103T-W17-1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user’s preferences.
* stores the KanBug Tracker data.
* exposes an unmodifiable `ObservableList<Bug>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.


<div markdown="span" class="alert alert-info">:information_source: Note: An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `KanBugTracker`, which `Bug` references. This allows `KanBugTracker` to only require one `Tag` object per unique `Tag`, instead of each `Bug` needing their own `Tag` object.<br>
![BetterModelClassDiagram](images/BetterModelClassDiagram.png)

</div>


### Storage component

![Structure of the Storage Component](images/StorageClassDiagram.png)

**API** : [`Storage.java`](https://github.com/AY2021S1-CS2103T-W17-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the KanBug Tracker data in json format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### FeatureUI kanban view window

#### Proposed Implementation
The kanban view window would comprise of 4 columns that would divide the list of bug by their states. This would be implemented by putting 4 BugListPane in a horizontal box. The 4 BugListPanes would be constructed using a Observerable list that contains only the bugs that belong to their respective state. This observerable list would be provided by the logic manager. These 4 BugListPanes would be filled when the method fillInnerParts() is called by MainWindow.

<img src="images/Ui.png" width="450" />

Given below is how the KanbanBoard window will create the 4 BugListpanes

Step 1:
The user lanches the app and the system initalises the UI.

Step 2:
MainWindow calls fillInnerParts() on KanbanBoard.

Step 3:
For each of the 4 states, KanbanBoard would call getFilteredBugListByState on logic manager to get the appropriate lists and create the BugListPane.

Given below is sequence diagram for the creation of the BugListPanes:

<img src= "images/KanbanBoardUI.png" width = "400">

With the implementation of kanban view window, command such as delete, move and edit that depend on the index would not work as expected. This is because, the Kanban view seperates the bugs and place then in different columns. As such, it would be essential to allow the users to execute these commands in the kanban view as we implement the new window. This can be done by allowing the user to chose which column would be affected by these commands.

This can be done by adding the following classes:

- `DeleteByStateCommand` which extends `DeleteCommand`
- `MoveBystateCommand` which extends `MoveCommand`
- `EditByStateCommand` which extends `EditCommand`

These command would take in an extra input to specify which column is being targeted. The list of bugs would then be filtered according to the column specified. The respective parsers would also have to be modified such that the new command could be returned if a column is specifed. The following activity diagram summerizes what happens when the user enters a delete command.(edit and move command parser would act in a similar way)

<img src = "images/DeleteCommandParserActivityDiagram.png" width ="400">

#### Design consideration:

- **Alternative 1**: Use a prefix "/c" to specify which column we are refering to.(Current choice)
    - Pros: Easier to implement
    - Cons: Adds an additional prefix which the user has to remember to the applicatiom
- **Alternative 2**: Allow the users to specify an active column and execute the commands with respect to that column
    - Cons: Need to add an additional command to change the active column.

### Edit Tag feature

#### Implementation

The edit tag feature is facilitated by `EditTagCommandParser`, `EditTagCommand` and `EditTagByStateCommand`.  The class structure of the implementation is given below.

![EditTagClassStructure](images/EditTagClassStructure.png)

`EditTagCommand` extends `Command` and uses **editTag** as its `COMMAND_WORD`  and makes use of the **ot/** and **nt/** prefixes.

The operations that each class implements is given below:

`EditTagCommandParser`

- `EditTagCommandParser#parse(String)`— Parses input 

`EditTagCommand`

- `EditTagCommand#execute(Model)`  —  Executes the command.

- `EditTagCommand#updateTagInBug(Bug, Tag, Tag)` — Replaces the old Tag in Bug with the new Tag.



#### Usages

There are two possible usages of this feature, depending on whether the user is in the **Kanban view** or **List view**. 

Given below is an example usage scenario in **Kanban View** and how the edit tag feature behaves at each step.

Step 1. The user launches the application for the first time. The `KanBugTracker` will be initialized with the initial KanBug tracker state.

Step 2. The user executes `add n/Print bug d/prints the wrong message s/todo t/Ui` command to add a new bug to the KanBug tracker. A new bug with the following information is added:

- name: **Print bug**
- description: **prints the wrong message**
- state: **todo**
- tag: **Ui**

This bug is added as the 6th bug in the KanBug tracker.

**Note that the index 6 is for illustration purposes only**

Step 3. The user decides that the tag they added is incorrect and would like to modify it. The user executes `editTag 6 c/todo ot/Ui nt/display`. This will result in the tag of the bug at index **6** of the **todo** column being modified such that the new tag is **display** instead of **Ui**.

The updated bug is as follows:

- name: **Print bug**
- description: **prints the wrong message**
- state: **todo**
- tag: **display**

The following sequence diagram shows how the edit tag operation works in **Kanban view**:



![EditTagByStateKanBan](images/EditTagByStateSequenceDiagram.png)

ℹ️ **Note:** The lifeline for `EditTagCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

The usage scenario in **List view** is similar to that of **Kanban view** except that the user does not supply a **column** as input. An example of such a command would be `editTag 6 ot/Ui nt/display`. Instead of an `EditTagByStateCommand` , an `EditTagCommand` is returned by `EditTagCommandParser`.

The following sequence diagram shows how the edit tag operation works in **List view**:

![EditTagByStateKanBan](images/EditTagSequence.png)

ℹ️ **Note:** The lifeline for `EditTagCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

### Add tag feature

#### Implementation

The add tag feature is facilitated by `AddTagCommandParser`, `AddTagCommand` and `AddTagByStateCommand`.  The class structure of the implementation is given below.

![AddTagClassStructure](images/AddTagClassStructure.png)



`AddTagCommand` extends `Command` and uses **addTag** as its `COMMAND_WORD`  and makes use of the **nt/** prefix.

The operations that each class implements is given below:

`AddTagCommandParser`

- `AddTagCommandParser#parse(String)`— Parses input 

`AddTagCommand`

- `AddTagCommand#execute(Model)`  —  Executes the command.
- `AddTagCommand#updateTagInBug(Bug, Tag)` — Adds the new Tag to the Bug.



#### Usages

There are two possible usages of this feature, depending on whether the user is in the **Kanban view** or **List view**. 

Given below is an example usage scenario in **Kanban View** and how the edit tag feature behaves at each step.

Step 1. The user launches the application for the first time. The `KanBugTracker` will be initialized with the initial KanBug tracker state.

Step 2. The user executes `add n/Print bug d/prints the wrong message s/todo t/Ui` command to add a new bug to the KanBug tracker. A new bug with the following information is added:

- name: **Print bug**
- description: **prints the wrong message**
- state: **todo**
- tag: **Ui**

This bug is added as the 6th bug in the KanBug tracker.

**Note that the index 6 is for illustration purposes only**

Step 3. The user decides that they would like to add an additional tag to the bug. The user executes `addTag 6 c/todo nt/wrongPrinting`. This will result in a new tag **wrongPrinting** being added to the bug at index **6** of the **todo** column.

The updated bug is as follows:

- name: **Print bug**
- description: **prints the wrong message**
- state: **todo**
- tag: **Ui**, **wrongPrinting**

The following sequence diagram shows how the edit tag operation works in **Kanban view**:

![AddTagByStateKanBan](images/AddTagByStateSequenceDiagram.png)

ℹ️ **Note:** The lifeline for `AddTagCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

The usage scenario in **List view** is similar to that of **Kanban view** except that the user does not supply a **column** as input. An example of such a command would be `addTag 6 nt/display`. Instead of an `AddTagByStateCommand` , an `AddTagCommand` is returned by `AddTagCommandParser`.

The following sequence diagram shows how the edit tag operation works in **List view**:

![AddTagKanBan](images/AddTagSequenceDiagram.png)

ℹ️ **Note:** The lifeline for `AddTagCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

### Bug priority

Feature description: Each bug will now have a priority level (low, medium, high) that will be shown on the GUI. Users
can add or edit a bug with the priority using the `pr/` tag. The priority is optional, but each bug must have at most
one priority only.

#### Proposed Implementation

The followings are notable differences between `Priority` and other fields of `Bug`:
- The `VALIDATION_REGEX` of `PRIORITY` will be set such that its constructor can only accept the `String` `"low"`, 
`"medium"`, `"higher"` (either uppercase or lowercase)
- Aside from the 3 possible states of `Priority` (`"low"`, `"medium"` or `"high"`), it will also have the state `""` 
that represent when the bug have no priority indicated. This type of `Priority` will be create using an overload 
version of the constructor that accept no argument: `new Priority("low")` will create a low priority, but 
`new Priority()` will create an "empty" priority. 
- We will check if the `Priority` is "empty" before showing it on the UI.

<div markdown="span" class="alert alert-info">:information_source: Note: This is to simplified the code so that every instance of `Bug` is mandatory and also avoid using `null` (which could cause `NullPointerException` and break the app) at the same time.
</div>

Here is a diagram show how an AddCommandParser work with priority:

![Priority](images/PrioritySequenceDiagram.png)

Given below is an example usage scenario concerning only the bug's priority when the user add a new
bug:

Step 1. The user launches the application for the first time. The `KanBugTracker` will be initialized with the initial Kanbug Tracker state.

Step 2. The user execute the command "add n/Bug name d/Bug description pr/high"

Step 3. Through `LogicManager` and `KanBugTrackerParser`, an `AddCommandParser` will eventually have to parse `"n/Bug name d/Bug description pr/high"`

Step 4. The process will continue as shown below, after which an `AddCommand` object will be returned and executed:

![PriorityExample](images/PriorityExampleSequenceDiagram.png)

#### Design consideration:

Regarding the Priority class:
- **Alternative 1**: Create `Priority` as a separate class [current implementation]
    - Pros: Adhere OOP principles
    - Cons: Need to refactor quite a lot in many different places.
- **Alternative 2**: Create `Priority` as a subclass of `Tag` [rejected]
    - Cons: Break the Liskov Substitution Principle.
    
Regarding the situation when the bug's priority is not indicated:
- **Alternative 1**: Create a special type called "empty" Priority [current implementation]
    - Pros: Implementation will be similar to other existing fields.
    - Cons: Need to make sure that this special type will not be shown to users.
- **Alternative 2**: Use `Optional` [rejected]
    - Pros: Use imperative programming style, code look neater.
    - Cons: Harder to implement correctly.
- **Alternative 3**: Use `null` [rejected]
    - Pros: Easiest to implement.
    - Cons: Risky because of possible `NullPointerException`, also code will look complicated because of the need to check whether the object is `null`.

### Search feature

#### Implementation
The proposed search command is facilitated by `ModelManager`. It generates a filtered list (updated by a specified `Predicate<Bug>` as an argument), stored internally as `FilteredList<Bug>`. It should have its own class named `SearchCommand` and inherits from the abstract class `Command`. The command then returns an instance of `CommandResult` upon success and prints feedback to the users.
Additionally, it implements the following operations:  
* `SearchCommand#execute()` - Executes the search command.  
* `ModelManager#updateFilteredBugList(Predicate<Bug>)` - Filters internal data storage via its argument `Predicate<Bug>`   

The following class diagram shows the structure of the search command implementation:
![SearchCommandClassDiagram](images/SearchCommandClassDiagram.png)

Given below is an example usage scenario and how the search feature behaves at each step.  
Step 1. The user launches the application for the first time. The `KanBugTracker` will be initialized with the initial kanbug tracker state.  

Step 2. The user executes `add n/Ui bug d/Displays wrongly the information s/todo t/Ui.java` command to add a new bug to the kanbug tracker. A new bug with the following information is added:  

- name: **Ui bug**
- description: **Displays wrongly the information**
- state: **todo**
- tag: **Ui.java**

Step 3. When there are a lot of bugs in the tracker, it is difficult for the user to look for the particular bug.
The user wants to see the information of the above bug. Then, the user executes `search q/Ui bug`.
This `search` command checks if the input is valid and then parses before using it to create a Predicate<Bug> instance (**BugContainsQueryStringPredicate** in detail).
The predicate is internally passed and used to filter **FilteredList<Bug>**.
This results in the information of all the bugs of which name or description or tag contains **Ui bug** as a substring displays in the tracker.

The following sequence diagram summarizes what happens when a user executes the search command:
![SearchCommandSequenceDiagram](images/SearchCommandSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: Note: The query-string given by the user cannot be empty and is case-insensitive.
</div>

#### Design consideration:
**Alternative 1 (current choice)**: Use `q/` new prefix as a query-string to search
- Pro: Flexible search
- Con: Might be a long list of relevant bugs

**Alternative 2**: Use current field prefixes to search
- Pro: Might be complicated because the user needs to remember exactly some information of a particular field
- Con: Restricted search



### Note feature

#### Proposed Implementation
The proposed notes feature is facilitated by `Bug`, `AddCommandParser` and `EditCommandParser`. It adds a new `Note` state that can be parsed by the `AddCommandParser` and `EditCommandParser` and stored internally as an `Optional<Note>` object inside `Bug`.

The added operations by `Note` are internal operations that are handled by the `AddCommandParser` and `EditCommandParser` and as a result their functionality is not required elsewhere.

Given below is an example usage scenario and how the `Note` mechanism behaves at each step when used with `AddCommandParser` and `EditCommandParser`.

Step 1: The user launches the application and executes the add command and provides a `Note` input using the `nt/` prefix. The `AddCommandParser` then executes and splits the input String into its respective components asccording to the given prefix.

Step 2: The `AddCommandParser` then wraps the string following the `nt/` prefix in an `Optional<Note>` object which is then stored inside the new `Bug` that has been created.

The following activity diagram summarizes what happens when a user executes the add command:

<img src="images/NoteAddActivityDiagram.png" width="350">

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `nt/` command is not followed by a String, it will result in a message to the user that their input should not be blank.

</div>

Step 3: The user then decides to change the `Note` in the bug that he has just added using the `edit` commmand accompanied with the `nt/` prefix. The `EditCommandParser` then executes and splits the input String into its respective components according to the given prefix.

Step 4: The `EditCommandParser` then copies the unchanged information from the original `Bug` into a new `Bug` while modifying the `Note` section by wrapping the given input String into an `Optional<Note>` object and storing it in the new `Bug` that has been created. This new `Bug` object then replace the original object in the KanBug Tracker.

The following activity diagram summarizes what happens when a user executes the edit command:

<img src="images/NoteEditActivityDiagram.png" width="400">

#### Design consideration:

##### Aspect: How notes are stored and accessed

* **Alternative 1 (current choice):** Saves the Note inside an Optional<Note> object in Bug.
  * Pros: Prevents a null pointer exception and is a safer implementation while allowing the notes field to be optional
  * Cons: Difficult to implement

* **Alternative 2:** Saves the Note directly in Bug
  * Pros: Easy to implement.
  * Cons: Will run into null pointer exceptions that are hard to trace if the user chooses not to add notes


## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Has a need to manage a significant number of bugs
* Prefers desktop apps over other types
* Can type fast
* Prefers typing to mouse interactions
* Is reasonably comfortable using CLI apps

**Value proposition**: Providing a command line interface for software engineering students to manage and track the bug fixing process faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                     | So that I can…​                                                        |
| -------- | ------------------------------------------ | ------------------------------ | ---------------------------------------------------------------------- |
| `* * *`  | new user                                   | see usage instructions         | refer to instructions when I forget how to use the App                 |
| `* * *`  | user                                       | add a new bug              |                                                                        |
| `* * *`  | user                                       | delete a bug                | remove entries that I no longer need                                   |
| `* * *`  | user                                       | add notes to specific bugs   | quickly remeber details about the bug|
| `* * *`  | user                                       | keep track of the number of issues | keep track of the number of issues in my code|
| `* * *`  | user                                       | see all the bugs             | Know what are the bugs in my program|

### Use cases

(For all use cases below, the **System** is the `Kanbug Tracker` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Delete a bug**

**MSS**

1.  User requests to list bugs
2.  Kanbug Tracker shows the list of bug
3.  User requests to delete a specific bug in the list
4.  Kanbug Tracker deletes the bug

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Kanbug Tracker shows an error message.

      Use case resumes at step 2.

**Use case: Add a bug**

**MSS**

1. User requests to add bug

2. KanBug Tracker adds the new bug and displays the updated list of bugs

   to the user

   Use case ends.

**Extensions**

- 1a. The bug format is invalid

  - 1a1. Kanbug Tracker shows an error message.

    Use case resumes at step 1.

**Use case: Edit a bug**

**MSS**

1. User requests to list bugs

2. KanBug Tracker shows the list of bugs

3. User requests to edit a specific bug in the list

4. KanBug Tracker edits the bug

   Use case ends.

**Extensions**

- 2a. The list is empty.

  Use case ends.

- 3a. The given index is invalid.

  - 3a1. KanBug Tracker shows an error message.

    Use case resumes at 2.

- 3b. The user enters an invalid edit format

  - 3b1. KanBug Tracker shows an error message.

    Use case resumes at 2.

**Use case: Move a bug**

**MSS**

1. User requests to list bugs
2. KanBug Tracker shows the list of bugs
3. User requests to move a specific bug to another state
4. KanBug Tracker moves the bug

**Extensions**

- 2a. The list is empty.

  Use case ends.

- 3a. The given index is invalid.

  - 3a1. KanBug Tracker shows an error message.

    Use case resumes at 2.

- 3b. The user enters an invalid edit format.

  - 3b1. KanBug Tracker shows an error message.

    Use case resumes at 2.

### Non-Functional Requirements

1.  KanBug Tracker should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
3.  KanBug Tracker should be able to hold up to 10000 **Bug** items without a remarkable sluggishness in performance for typical usage.
4.  KanBug Tracker should work on both 32-bit and 64-bit environments.
5.  The size of the compiled JAR file (KanBugTracker.jar) should be less than 100Mb.
6.  KanBug Tracker should be intentionally designed for a single-user (i.e. KanBug Tracker should not be a multi-user application).
7.  KanBug Tracker should be developed incrementally over the project duration.
8.  The software’s codebase should adhere to OOP principles.

### Glossary
* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Command Line Interface (CLI)**: Text-based user interface that is used to view and manage device files.
* **Graphical User Interface (GUI)**: A visual way of interacting with a device using a variety of items (e.g. windows, tables, icons, etc.).
* **OOP**: Object-Oriented Programming.
* **Prefix**: A set of characters placed before a specified parameter when typing a command.
* **Query-string**: The user keyword input to find matches when the search command is executed.

We provide more glossaries for non-technical terms of KanBug Tracker in [Glossary of the User Guide](https://ay2021s1-cs2103t-w17-1.github.io/tp/UserGuide.html#2-glossary).


--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: Note: These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.

### Switching views

1. Switching Kanbug Tracker's view with `switch`.

1. Prerequisites: None.

1. Test case:

    1. Open the app<br>
       Expected: the default view should be Kanban View.

    1. Execute `switch`<br> 
       Expected: the view should be switched.

    1. Execute `switch` again<br>
       Expected: the view should be switched back to the original one.
    
### Opening the `help` window

1. Opening the Kanbug Tracker's `help` window in two ways.

1. Prerequisites: Launch Kanbug Tracker successfully

1. Test case 1:

    1. Switch to Kanban View.
    
    1. Execute `help`<br>
       Expected: the `help` window should be opened.
    
    1. Close the `help` window using the mouse<br>
       Expected: only the `help` window will be closed.
    
1. Test case 2:

    1. Switch to List View.
    
    1. Press F1<br>
       Expected: the `help` window should be opened.
    
    1. Focus on the app's main window (either using mouse of Alt + Tab).
    
    1. Press F1 again<br>
       Expected: the `help` window should be focused again.
    
    1. Press Esc<br>
       Expected: only the `help` window will be closed.

### Adding a bug

1. Adding a bug using the `add` command.

1. Prerequisites: Launch Kanbug Tracker successfully and clear all the bugs.

1. Test case 1:

    1. Switch to Kanban View.
    
    1. Execute `add n/My bug d/Text`<br>
       Expected: a new bug with the name "My bug" and description "Text" should be added in the `backlog` column.
    
    1. Execute `add n/Bug d/Des t/tag1 t/tag2 pr/low s/done`<br>
       Expected: a new bug with the correct fields should be added in the `done` column.
    
1. Test case 2:
    
    1. Execute `add n/bug d/des`<br>
       Expected: a new bug with the correct fields should be added.
    
    1. Execute `add n/b`<br>
       Expected: No bug is added. The app should response with "Invalid command format! ..."
       (since the `description` field is missing).
    
    1. Execute `add n/bug`<br>
       Expected: No bug is added. The app should response with "This bug already exists in ..."
       (since a bug with the same name already exists).
    
### Searching bugs

1. Searching bugs using the `search` command.

1. Prerequisite: he app have 3 bugs: a bug name "name1" and 
    description "des1", a bug name "me12" and description "description", a bug name "bug12" 
    and description "description".
    
1. Test case:

    1. Execute `search q/e1`<br>
       Expected: the bug "name1" and the bug "me12" is listed.
    
    1. Execute "search q/w"<br>
       Expected: no bug is listed.
    
    1. Execute "search q/description"<br>
       Expected: the bug "me12" and the bug "bug12" is listed.
    
### Listing all bugs

1. Listing all bugs using the `list` command.

1. Prerequisite: same prerequisite with the testing for Searching bugs.

1. Test case:

    1. Execute `search q/e1`, now 2 bugs should be listed.
    
    1. Execute `list`<br>
       Expected: all 3 bugs are listed.
    
    1. Execute `list` again<br>
       Expected: all 3 bugs are still listed.
    
### Deleting a bug

1. Deleting a bug while all bugs are being shown.

1. Prerequisites: List all bugs using the `list` command. Multiple bugs in the list.

1. Test case 1:

   1. Switch to List view.

   1. Execute `delete 1`<br>
      Expected: First bug is deleted from the list. Details of the deleted bug shown in the status message. 

   1. Execute `delete 0`<br>
      Expected: No bug is deleted. The app should response with "Invalid command format...". 

   1. Execute `delete x` where x is larger than the number of bug in the list<br>
      Expected: No bug is deleted. The app should response with "The bug index provided is invalid". 

   1. Execute `delete 1 c/backlog`<br>
      Expected: No bug is deleted. The app should response with "Please do not provide column..." 

1. Test case 2:
    
    1. Switch to Kanban view.
    
    1. Add some bugs into the todo column.
    
    1. Execute `delete 1 c/todo`<br>
       Expected: First bug is deleted from the `todo` column. Details of the deleted bug shown in the status message.
    
    1. Execute `delete 1`<br>
       Expected: No bug is deleted. The app should response with "Please provide column...".

### Editing bugs

1. Editing a bug using the `edit` command.

1. Prerequisite: There are multiple bugs, at least one is in `todo`.

1. Test case 1:

    1. Switch to List view.
    
    1. Execute `edit 1 t/tag1 pr/low note/some note`<br>
       Expected: The first bug will have a new tag, priority and note according to the command.
       Details of the bug is shown in the status message.
       
    1. Execute `edit 1 pr/ note/ t/`<br>
       Expected: The first bug will have its priority, note and tags removed. Details
       of the bug is shown in the status message.

1. Test case 2:

    1. Switch to Kanban view.
    
    1. Execute `edit 1 d/desc`<br>
       Expected: No bug is edited. The app response with "Please provide column...".
       
    1. Execute `edit 1 c/todo d/desc`<br>
       Expected: The first bug in the `todo` column is edited. Details of the
       bug is shown in the status message.
       
### Appending the tags

1. Adding tags to a bug without removing the original ones.

1. Prerequisite: The first bug in the List view have at least one tag.

1. Test case:

    1. Switch to List view.
    
    1. Execute `addTag 1 nt/tag3 nt/tag4`<br>
       Expected: The first bug has two more tags along with the original ones.
       Details of the bug is shown in the status message.
       
    1. Execute `addTag 1 nt/tag4 nt/tag5`<br>
       Expected: No tags will be added. The app should response with "The new tag already exists".

### Editing a single tag

1. Editing a tag of some bug without removing the other ones.

1. Prerequisite: The first bug in the List view has two tags "tag1" and "tag2".

1. Test case:

    1. Switch to List view.
    
    1. Execute `editTag 1 ot/tag1 nt/tag3`<br>
       Expected: The tag "tag1" of the first bug is rename to "tag3". 
       
    1. Execute `editTag 1 ot/tag1 nt/tag4`<br>
       Expected: No tags will be edited. The app should response with "A valid existing tag must be supplied.".
       
    1. Execute `editTag 1 ot/tag2 nt/tag3`<br>
       Expected: No tags will be edited. The app should response with "The new tag already exists!".
       
### Moving a bug to a new state

1. Moving a bug to a new state using the `move` command.

1. Prerequisite: Successfully opened the app. Clear all bugs.

1. Test case 1:

    1. Switch to List view.
    
    1. Add a bug with state `backlog`
    
    1. Execute `move 1 s/todo`<br>
       Expected: The state of the first bug in the list is changed to `todo`. 
       Details of the bug is shown in the status message.
       
    1. Execute `move 1 s/todo` again<br>
       Expected: No bug has its state edited. The app should response with "Cannot move 
       bug to the same state...".
       
1. Test case 2:

    1. Switch to Kanban view.
    
    1. Add a bug with state `backlog`
    
    1. Execute `move 1 c/backlog s/todo`<br>
       Expected: The state of the first bug in the list is changed to `todo`. 
       Details of the bug is shown in the status message.
       
    1. Execute `move 1 c/backlog s/todo` again<br>
       Expected: No bug has its state edited. The app should response with "The bug
       index provided is invalid".
       
### Clearing all bugs

1. Clearing all current bugs in the list.

1. Prerequisite: The app have some bugs.

1. Test case:

    1. Execute `clear`<br>
       Expected: All bugs is cleared.
       
    1. Add some bugs to the app.
       
    1. Execute `clear all`<br>
       Expected: No bug is deleted. The app response with "Please remove extra 
       irrelevant arguments!".

### Exiting Kanbug Tracker
       
1. Exiting the app and closing the window.

1. Prerequisite: The app is successfully opened.

1. Test case 1:

    1. Execute `exit`<br>
       Expected: The app's window is closed.
       
1. Test case 2:

    1. Press Esc<br>
       Expected: The app's window is closed.
       
1. Test case 3:

    1. Open `help` window.
    
    1. Focus on the main window.
    
    1. Press Esc<br>
       Expected: Both the app's window and the `help` window is closed at once.
       
### Saving data

1. Dealing with missing/corrupted data files.

1. Prerequisite: The app have some bugs.

1. Test case 1:

   1. Close the app.
   
   1. Edit the `kanbugtracker.json` file by editing the description field of some bug into "bug description".
   
   1. Open the app again<br>
      Expected: The change should be reflected in the app.
      
1. Test case 2:

   1. Close the app.
   
   1. Edit the `kanbugtracker.json` file by editing the name field of some bug into "@".
   
   1. Open the app again<br>
      Expected: The app now will have no bugs on it (since "@" is not a valid name for the bugs).
