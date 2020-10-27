---
layout: page
title: User Guide

---
[![CI Status](https://github.com/AY2021S1-CS2103T-W17-1/tP/workflows/Java%20CI/badge.svg)](https://github.com/AY2021S1-CS2103T-W17-1/tp/actions)

## What is KanBug Tracker?
KanBug Tracker is a **desktop application for managing the tracking of bugs you encounter, optimized for use via a Command Line Interface (CLI)** while still having the benefits of a Graphical User Interface (GUI). If you can type fast, KanBug Tracker can get your bug management tasks done faster than traditional GUI apps.

## Who is this application for?
KanBug Tracker was made to provide a lightweight, offline application for CS2103/T students to manage the bug details of their projects. Users interact with the application entirely through a CLI which makes the application perfect for fast typists. If you are looking for an offline, lightweight and easy-to-use application for your bug tracking needs, KanBug Tracker is the application for you!


## What can users expect?
KanBug Tracker provides two different views that the user can switch between. The first is the traditional Kanban board style view which aims to allow users to get a high-level overview of the state of bugs in their project. The second view is the To-Do list view which allows users to focus only on the bugs within a particular section of the KanBug Tracker.

#### Kanban View:

![Ui](images/Ui3.png)

1. Command Line Interface for users to enter their commands
2. Display that the application uses to give feedback on commands to the user
3. Four of pre-declared states that KanBug Tracker comes with. Each bug will be assigned to one of the four states to aid users in tracking the life cycle of each bug.
4. Each individual bug will be displayed with the following data listed in order of display from top to bottom:
    - Name of bug
    - Short description of bug
    - Relevant tags 
    - Priority of bug

#### List View:

![Ui](images/Ui4.png)

1. Command Line Interface for users to enter their commands
2. Display that the application uses to give feedback on commands to the user
3. Scroll bar to navigate up and down the bug list
4. Each individual bug will be displayed with the following data listed in order of display from top to bottom:
    - Name of bug
    - Short description of bug
    - Note containing extra information about the bug
    - Relevant tags
    - Priority of bug

## Table Of Contents

The first step in every journey is the preparation, after that when we have you all set up we will take you through some key points to aid you on your journey through our user guide. After which we will show you everything that you can do with this powerful tracker before we end off with some commands that will help you keep your bugs in order. Thank you for choosing KanBug Tracker!

1. [Getting Started](#1-getting-started)

2. [Glossary](#2-glossary)

3. [Features](#3-features)

   3.1 [Viewing help : **`help`**](#31-viewing-help--help)

   3.2 [Listing all bugs : **`list`**](#32-listing-all-bugs--list)

   3.3 [Searching for bugs: **`search`**](#33-searching-for-bugs--search)

   3.4 [Adding a bug : **`add`**](#34-adding-a-bug--add)

   3.5 [Deleting a bug : **`delete`**](#35-deleting-a-bug--delete)

   3.6 [Editing a bug : **`edit`**](#36-editing-a-bug--edit)

   3.7 [Editing a tag of a bug: **`editTag`**](#37-editing-a-tag-of-a-bug--edittag)

   3.8 [Adding a tag to a bug : **`addTag`**](#38-adding-a-tag-to-a-bug--addtag)

   3.9 [Moving a bug : **`move`**](#39-moving-a-bug--move)

   3.10 [Clearing all bugs: **`clear`**](#310-clearing-all-bugs--clear)

   3.11 [Exiting the program :  **`exit`**](#311-exiting-the-program--exit)

   3.12 [Saving the data](#312-saving-the-data--automatically)

4. [Command Summary](#4-command-summary)#command-summary)

------

## 1. Getting Started
1. Ensure that you have `Java 11` or above installed on your computer. If you do not have a suitable version of `Java` installed on your computer, you may head [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) to download the installer for your operating system.
2. Download the latest `KanBugTracker.jar` from our [GitHub](https://github.com/AY2021S1-CS2103T-W17-1/tp/releases)
    ![Download](images/KanBugDownload.png)

  

3. Copy the `KanBugTracker.jar`file to a folder you want to use as your _home folder_.
    ![HomeFolder](images/HomeFolder.png)
    **Add Note here to describe what is going on**

4. Double-click on the `KanBugTracker.jar`file to launch the application. 

    <Insert an image here to show what it will look like upon starting up>

The next section will cover the various terms that you need to be familiar with to get started!

## 2. Glossary

We're sure that you're excited to start using KanBug Tracker! Before that however, there are some terms that you need to be familiar with to make the best out of your KanBug Tracker experience.

What are some things I think we need to explain?

- Columns
- What is a bug
  - Bug name
  - Bug Description
  - Tags
  - Priority
  - Notes
- Commands


## 3. Features

- Words in `UPPER_CASE` are parameters to be supplied by the user
- Items in `[...]` are optional
- `INDEX ` **must be a positive integer** 1,2,3...

### 3.1 Viewing help : `help`

Not sure what to do next? Don't worry, just ask for help.

Format: `help`

- Gets all commands’ syntax and usage and link to this User Guide.

### 3.2 Listing all bugs : `list`

Lists all bugs in the tracker for times when you want to quickly look through all the bugs in the tracker.

Format: `list`

- Shows a list of all bugs in the tracker system

### 3.3 Searching for bugs : `search`

<Input the description for search here>

### 3.4 Adding a bug : `add`

When a bug is encountered, the add command is here to help keep track of the bug for furture reference.

Format: `add n/NAME d/DESCRIPTION [s/STATE] [note/NOTE] [t/TAG] [pr/PRIORITY]`

- Add a bug with the specified name, description and state to the bottom of the list.
- The state, note and tag fields are optional, all other fields are needed.
- If state is not specified, a default state of backlog will be assigned.

Examples:

- `add n/Print bug d/Prints the wrong message s/todo t/Ui`, adds a bug with name *Print Bug*, Description of *Prints the wrong message*, state of *To do* and a tag of *Ui*.
- `add n/Move bug d/Moves bug to wrong column s/backlog note/This bug is likely caused by issues in multiple classes`, adds a bug with name *Move bug*, Description of *Moves bug to wrong column*, state of *Backlog* and a note of *This bug is likely caused by issues in multiple classes*.
- `add n/Move bug d/Moves the wrong bug when run pr/high`, adds a bug with name *Move bug*, Description of *Moves the wrong bug when run*, default state of *Backlog* and priority of *high*.

### 3.5 Deleting a bug : `delete`

After a bug is fixed and a project is done, its time to remove the bug from the application.

Format: `delete INDEX [c/COLUMN]`

- The command to be used depends on which view the user is in. The user can either be in **Kanban view** or **List view**.
  - **Kanban view**: User must supply the `COLUMN`. The bugs are filtered such that only bugs that have a `STATE` matching the `COLUMN` selected are considered. The bug at the specified `INDEX` of this filtered list is selected to be edited.
  - **List view**: `COLUMN` should **not** be supplied. The bug at the specified `INDEX` is edited. The index refers to the index number shown in the displayed list of bugs.


- Deletes the bug at the specified index

Example:

- `delete 1`, deletes the bug at index **1** of bug list in main view.
- `delete 2 c/backlog`, deletes the second bug in the backlog column

### 3.6 Editing a bug : `edit`

Made a mistake when adding in a bug or simply changed your mind on what the description should be? Fret not, thats what the edit command is for.

Format: `edit INDEX [c/COLUMN] [n/NEW_NAME] [d/NEW_DESCRIPTION] [note/NEW_NOTE] [t/NEW_TAG]`

- The command to be used depends on which view the user is in. The user can either be in **Kanban view** or **List view**.
  - **Kanban view**: User must supply the `COLUMN`. The bugs are filtered such that only bugs that have a `STATE` matching the `COLUMN` selected are considered. The bug at the specified `INDEX` of this filtered list is selected to be edited.
  - **List view**: `COLUMN` should **not** be supplied. The bug at the specified `INDEX` is edited. The index refers to the index number shown in the displayed list of bugs.

- Edits the specified bug.
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- **Multiple tags** can be added or edited.

Examples:

- `edit 1 c/backlog n/Wrong list numbers when displaying list d/List column printed as all 1's`, edits the name and description of the 1st bug in the backlog column to *Wrong list numbers when displaying list* and "List column printed as all 1's" respectively.
- `edit 2 d/When listing items, duplicates are printed note/Tried a fix using iterator, did not work`, edits the description of the 2nd bug to be *When listing items, duplicated are printed* and changes the note to *Tried a fix using iterator, did not work*
- `edit 3 t/Logger t/Logging`, edits/adds the two tags provided *Logger* and *Logging*.

### 3.7 Editing a tag of a bug : `editTag`

Edits an existing tag of a bug in the tracker

Format: `editTag INDEX [c/COLUMN] ot/OLD_TAG nt/NEW_TAG`

- The command to be used depends on which view the user is in. The user can either be in **Kanban view** or **List view**.
  - **Kanban view**: User must supply `COLUMN`. The bugs are filtered such that only bugs that have a `STATE` matching the `COLUMN` selected are considered. The bug at the specified `INDEX` of this filtered list is selected to be edited.
  - **List view**: `COLUMN` should **not** be supplied. The bug at the specified `INDEX` is edited. The index refers to the index number shown in the displayed list of bugs.
  
- The `OLD_TAG` supplied must be an existing tag.
- The `NEW_TAG` supplied must **not** be an existing tag.
- The `OLD_TAG` will be updated to the `NEW_TAG`

Examples:

- `editTag 1 ot/UI nt/UserDisplay` edits the bug at index **1** and replaces the tag **UI** with the tag **UserDisplay**.
- `editTag 1 c/backlog ot/CommandResult nt/CommandParser` filters all bugs and only considers those that have a **state** of **backlog**. It then edits the bug at index **1** of this list and replaces the tag **CommandResult** with the tag **CommandParser**.

### 3.8 Adding a tag to a bug : `addTag`

Adds a tag to a bug in the tracker

Format: `addTag INDEX [c/COLUMN] nt/NEW_TAG`

- The command to be used depends on which view the user is in. The user can either be in **Kanban view** or **List view**.
  - **Kanban view**: User must supply `COLUMN`. The bugs are filtered such that only bugs that have a `STATE` matching the `COLUMN` selected are considered. The bug at the specified `INDEX` of this filtered list is selected to be edited.
  - **List view**: `COLUMN` should **not** be supplied. The bug at the specified `INDEX` is edited. The index refers to the index number shown in the displayed list of bugs.

- The `NEW_TAG` supplied must **not** be an existing tag.
- The `NEW_TAG` will be added to the bug.

Examples:

- `addTag 2 nt/UserDisplay` edits the bug at index **2** and adds the tag **UI** to the bug.
- `addTag 1 c/backlog nt/CommandParser` filters all bugs and only considers those that have a **state** of **backlog**. It then edits the bug at index **1** of this list and adds the tag **CommandParser** to the bug.

### 3.9 Moving a bug : `move`

Moves an existing bug in the tracker from one state to another

Format: `move INDEX [c/COLUMN] s/STATE`

- The command to be used depends on which view the user is in. The user can either be in **Kanban view** or **List view**.
  - **Kanban view**: User must supply `COLUMN`. The bugs are filtered such that only bugs that have a `STATE` matching the `COLUMN` selected are considered. The bug at the specified `INDEX` of this filtered list is selected to be edited.
  - **List view**: `COLUMN` should **not** be supplied. The bug at the specified `INDEX` is edited. The index refers to the index number shown in the displayed list of bugs.

- Moves the specified bug.
- The state field is **mandatory** and must be provided.
- State can either be **backlog, todo, ongoing** or **done**.
- Existing state will be updated to the new state.

Examples:

- `move 1 s/todo`, moves the bug at index **1** from its initial state to the “To Do” state.
- `move 3 s/done`, moves the bug  at index **3** from its initial state to the “Done” state.

### 3.10 Clearing all bugs : `clear`

<Input the description for search here>

### 3.11 Exiting the program : `exit`

End and close the app.

Format: `exit`

- Saves all of the local data and exit.

### 3.12 Saving the data : automatically

Data is saved into the hard disk every time a change is made.

------

## 4. Command Summary

|   Action    |                            Format                            |
| :---------: | :----------------------------------------------------------: |
|  **help**   |                            `help`                            |
|  **list**   |                            `list`                            |
| **search**  |                   <Fill in implementation>                   |
|   **add**   |   `add n/NAME d/DESCRIPTION [s/STATE] [note/NOTE] [t/TAG]`   |
| **delete**  |                        `delete INDEX [c/COLUMN]`                        |
|  **edit**   | `edit INDEX [c/COLUMN] [n/NEW_NAME] [d/NEW_DESCRIPTION] [note/NOTE] [t/NEW_TAG]` |
| **editTag** |       `editTag INDEX [c/COLUMN] ot/OLD_TAG nt/NEW_TAG`       |
| **addTag**  |             `addTag INDEX [c/COLUMN] nt/NEW_TAG`             |
|  **clear**  |                   <Fill in implementation>                   |
|  **move**   |               `move INDEX [c/COLUMN] s/STATE`                |
|  **exit**   |                            `exit`                            |

[Back to top](#what-is-kanbug-tracker)

Team Name: AY2021S1-CS2103T-W17-1
