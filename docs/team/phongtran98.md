---
layout: page
title: Tran Gia Phong's Project Portfolio Page
---

## Project: KanBug Tracker

### Overview

This portfolio page highlights my contributions to KanBug Tracker - a Software Engineering project developed as part of the module CS2103T during my second year of undergraduate studies in the National University of Singapore. 

### About the Team

We are five Year 2 Computer Science undergraduates reading CS2103T: Software Engineering. 

### About the project

KanBug Tracker is a lightweight, easy to use bug management tool that aims to empower developers to keep better track of the bugs in their projects. It provides users not only with a convenient way to record their bugs, but also allows them to better coordinate their efforts between various bugs and track the state of each bug throughout its lifecycle. The team has also developed various other user-centric features to provide our users with the functionality they need to empower their bug tracking efforts. 

## Summary of contributions

* **New Feature**: I partly implemented the `move` command feature
 
    * What it does: It behaves similar to the `edit` command, but only change the `State` of the bug. 
    * Justification: This feature improves the app significantly because the idea of Kanban Board revolves around "state" (the 
 point of a Kanban Board is to divide different tasks into various stages on a table). By not using the `edit` command
 to edit the `State`, we could emphasize the idea of using a Kanban Board to visualize different stages of dealing with
 a bug.

* **New Feature**: I implemented the `Priority` field in each `Bug` object

    * What it does: It represent the priority of the bug considering, with only 4 possible state: High, Medium, Low, or not indicated.
    * Justification: This feature improves the app significantly because it enables users to tag a bug with a priority, 
 guarantees that the priorities are consistent (e.g. using `Tag` might result in tags called "High priority", "Priority.HIGH", 
 "PR-H" although they refer to the same Priority level).
    * Highlights: This enhancement requires a deep understanding of the `Model` component implementation and usage, 
 as well as knowing how to handle the "not indicated" state carefully without complicate the code.
 
* **New Feature**: I improved how the UI is rendered on the screen.

    * What it does: It makes sure that the UI is consistent on all operating systems concerned, and also makes sure that the
 UI does not break in any circumstances (e.g. resizing the app window, very long strings).
    * Justification: UI is important because this is the first thing users see.
    * Highlights: This enhancement requires a deep understanding of JavaFX syntax and usage, which is much different
 from the code in other components. To make sure that the UI works well in any circumstances, a lot of manual testing 
 is also needed.
 
* **New Feature**: I implement app exiting on pressing Esc

    * What it does: It conceptually execute `exit` command whenever the Esc key is pressed.
    * Justification: KanBug Tracker prioritizes using keyboard over using mouse, so this small enhancement helps improve
 user experience. 
 
* **Code contributed**: You can view my functional code and test code contributions to KanBug Tracker [here](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=phongtran98&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **Other contribution**:

    * **Project management**:
        * As the in charge of Testing, I make sure to do intensive testing on the app to minimize the number of bugs in the app.
        * Ensure that newly added features work as expected.
        * Contribute to the feature ideas and implementation ideas.  
 
    * **Enhancements to existing features**:
        * Thoroughly rename instances and classes name that were misses in previous refactoring.

    * **Documentation**:
        * Wrote sections on `move` and `exit` command in the User Guide.
        * Wrote section on the implementation of `Priority` in the Developer Guide.
