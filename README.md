# Overview
## SkimoZoomConnector
This repository has the connector to connect to zoom service provider

The connector extends the SkimoConnectorTemplate, which utilizes Springboot and Thymeleaf

(refer to the template README for more info)

The connector uses the Zoom REST API to obtain data.

Visit the [Skimo Website](https://skimo.tv/) to test functionality. 

## Functional Capabilities
**FULL ELABORATION FOR FUNCTIONS LISTED IN NEXT SECTION**

Allow users to login to Zoom account using OAuth 2.0

Retrieve a list of rooms that the user is a member of

Retrieve a list of items in the selected room's drive 

Retrieve a list of meetings from the items in the drive

Display a list of Zoom meetings on the Skimo website for the user

Allow user to select a recording and upload recording to the Skimo server

# Specific Functions 
For each function - summarize step by step how the function works at a user level (UI inputs outputs)

                  - summarize the code used to achieve said functionality 
## Zoom login using OAuth 2.0
Must use a registered company/university zoom account

## Retrieving List of User ID's
Use Get https://api.zoom.us/v2/users/{userId} : produces a list of each individual users ID's that joined the meeting.
## Retrieving List of Joined Meetings  
Use GET 
## Retrieve List of Recordings 
Use GET 
## Display List of Meetings in Skimo

## Meetings Select and Upload 

# Source
https://marketplace.zoom.us/docs/guides/getting-started

# Contributing and Support
We'd love that you contribute to the project. Before doing so, please contact krishnaa.movva@gmail.com for more info. 
