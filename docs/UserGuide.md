# User Guide
Team Name: AY2021S1-CS2103T-W17-1

KanBug Tracker is a **desktop app for managing the tracking of bugs you encounter, optimized for use via a Command Line Interface (CLI)** while still having the benefits of a Graphical User Interface (GUI). If you can type fast, KanBug Tracker can get your bug management tasks done faster than traditional GUI apps.

[![CI Status](https://github.com/se-edu/addressbook-level3/workflows/Java%20CI/badge.svg)](https://github.com/AY2021S1-CS2103T-W17-1/tp/actions)

[comment]: <> (![Ui](docs/images/Ui.png)

* [Features](#features)
  * [Viewing help : **`help`**](#viewing-help--help)
  * [Listing all bugs : **`list`**](#listing-all-bugs--list)
  * [Adding a bug : **`add`**](#adding-a-bug--add)
  * [Deleting a bug : **`delete`**](#deleting-a-bug--delete)
  * [Editing a bug : **`edit`**](#editing-a-bug--edit)
  * [Moving a bug : **`move`**](#moving-a-bug--move)
  * [Exiting the program :  **`exit`**](#exiting-the-program--exit)
  * [Saving the data](#saving-the-data)
* [Command Summary](#command-summary)

  ---

## Features

- Words in `UPPER_CASE` are parameters to be sipplied by the user
- Items in `[...]` are optional

### Viewing help : `help`
[comment]: <> (To be completed by Phong)

### Listing all bugs : `list`
[comment]: <> (To be completed by Duy)

### Adding a bug : `add`

Adds a bug to the list

Format: `add n/NAME d/DESCRIPTION s/STATE`
* Add a bug with the specified name, description and state to the bottom of the list.
* **All** of the fields are needed

Examples:
* `add n/Print bug d/prints the wrong message s/todo`, Adds a bug with name “Print Bug”, Description of “prints the wrong message” and state of “To do”.
* `add n/move bug d/moves bug to wrong column s/backlog`, Adds a bug with name “move bug”, Description of “moves bug to wrong column” and state of “Backlog”.

### Deleting a bug : `delete`
Deletes a bug from the list

Format: `delete INDEX`
  * Deletes the bug at the specified index
Example:
  * `delete 1`, Deletes the bug at index one of the bug list.


### Editing a bug : `edit`
[comment]: <> (To be completed by Kishen)

### Moving a bug : `move`

Moves an existing bug in the tracker from one state to another

Format: `move INDEX s/STATE`

* Moves the bug at the specified `INDEX`. The index refers to the index number shown in the displayed list of bugs. The Index **must be a positive integer** 1,2,3…
* The state field is **mandatory** and must be provided.
* State can either be `**backlog, todo, ongoing**` or `**done**`.
* Existing state will be updated to the new state.

Examples:

* `move 1 s/todo`, moves the 1st bug from its initial state to the “To Do” state.
* `move 3 s/completed`, moves the 3rd bug from its initial state to the “Completed” state.

### Exiting the program :  `exit`
[comment]: <> (To be completed by Phong)

### Saving the data
[comment]: <> (To be completed by Phong)

---

## Command Summary

|  Action  |  Format  |
|:--------:|:--------:|
|  **help**  |  `help`  |
|  **list**  |  `list [s/STATE]`  |
|  **add**  |  `add n/NAME d/DESCRIPTION s/STATE`  |
|  **delete**  |  `delete INDEX`  |
|  **edit**  |  `edit INDEX [n/NEW_NAME] [d/NEW_DESCRIPTION]`  |
|  **move**  |  `move INDEX s/STATE`  |
|  **exit**  |  `exit`  |

Team Name: AY2021S1-CS2103T-W17-1
