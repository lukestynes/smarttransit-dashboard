# SENG202 - (Team 5) SmartTransit Dashboard

SmartTransit Dashboard provides you the ability to view and interpret crash data in ways never before seen.

![picture of program](misc/app-image.png)

## Features
- [x] Advanced table querying and filtering of the crash data.
- [x] Pie chart visualisation of crash data.
- [x] Modern and intuitive UI.
- [x] Supporting multiple crash tables within a crash storage file.
- [x] Persistent data storage.
- [x] Other charting visualisation of data eg. Bar Chart, Line Chart, e.t.c.
- [x] Data overlayed on a map view.
- [x] Data displayed alongside routing information on map.

# Run Application
## Set up environment

This application is targeted for JDK 17, so install this or newer to run the application.
You will also need a copy of the stripped crash data, which is available under `misc/crash-data` in the project directory.

## Getting started

Launch the application by running `java -jar SmartTransit-Dashboard-v3.0.jar` from the terminal.

This will generate a folder named `log` and a file named `App Crash Data.std`, and launch the application.

The log folder contains logs of the program operation in-case you need to check for an error.

The `App Crash Data.std` file is the applications file to store crash record information.

### Importing crash data
The application supports data sets that match the stripped set provided in SENG202, any data will work as long as the CSV has the correct 69 headers from the CAS data.

# Build Code Locally
## Dependencies
- JDK >= 17 [click here to get the latest stable OpenJDK release (as of writing this README)](https://jdk.java.net/18/)
- Gradle [Download](https://gradle.org/releases/) and [Install](https://gradle.org/install/)

## Running the project
You can run the project through your IDE from the main class (App.java) or use the `./gradlew run` command from your terminal

## Build Project 
1. Open a command line interface inside the project directory and run `./gradlew jar` to build a .jar file. The file will be located under `build/libs/seng202_team5-3.0.jar`

## Authors
- [Luke Stynes](https://github.com/lukestynes)
- Dominic Gorny
- Isla Smyth
- Rinlada Tolley
- Zoe Perry
- Sergey Antonets
