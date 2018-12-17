# National Archives technical test

A solution to the technical test set by the National Archives.

## Instructions

### Run the application

Run the Gradle wrapper provided, using `--args` to specify the the parameters:

* The file to update (either as an absolute or relative path)
* The name of the column to update
* The row number to update, indexed from 1 (e.g. the first row of data is 1 rather than 0)
* The new value of the field

For example, to update column "origin" in row 3 to the value "London", on Mac or Linux:

```
./gradlew run --args "/some/input/file.csv origin 3 London"
```

or on Windows:

```
.\gradlew.bat run --args "/some/input/file.csv origin 3 London"
```

### Run the tests

To run all tests on Mac or Linux:

```
./gradlew cleanTest test"
```

on Windows:

```
.\gradlew.bat cleanTest test"
```

(If you skip `cleanTest`, gradle will only run tests for code that has changed since the last test run.)

## Specification

Given a CSV file(text file that uses a comma to separate values) with the following structure:


filename, origin, metadata, hash
file1, London, "a file about London", e737a6b0734308a08b8586720b3c299548ff77b846e3c9c89db88b63c7ea69b6
file2, Surrey, "a file about The National Archives", a4bf0d05d8805f8c35b633ee67dc10efd6efe1cb8dfc0ecdba1040b551564967
file55, Londom, "London was initially incorrectly spelled as Londom", e737a6b0734308a08b8586720b3c299548ff77b846e3c9c89db88b63c7ea69b6
file4, Penrith, "Lake District National Park info", a4bf0d05d8805f8c35b633ee67dc10efd6efe1cb8dfc0ecdba1040b551564968


Can you please write a piece of code (in Java or Scala) following TDD principles that allows a specified value in a specified column (by title)with a new value.

For example, it should enable column 'origin',  value Londom to be replaced with London

## Assumptions

* The user should be able to specify the input parameters when running the application from the command line
* It's OK for the application to only update one value each time it is run
* The CSV can have any number of rows, and any column names
* The user is a developer or someone who won't be confused by a stack trace shown if the application throws an
  exception. I've tried to give exceptions good error messages to make it easier to understand errors, but I would
  wrap these in friendlier messages (and add logging to capture the full stack trace) if it would be helpful to the
  user.

## Implementation notes

I used a CSV parsing library, Apache Commons CSV, to avoid having to handle all the edge cases in manual CSV parsing.
This means that the output CSV is saved in a slightly different format to the input CSV (but still a valid one):

* There are no spaces after the comma separators
* There are no quotes around the strings (unless the strings contain special characters like commas)
