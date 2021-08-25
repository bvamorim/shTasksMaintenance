# SH Challenge

### Table of Contents
- [Description](#description)
- [Technical Specification](#technical-specification)
- [Development Tools](#development-tools)
- [Getting Started](#getting-started)
    - [API](#api)
        - [User API](#user-api)    
        - [Task API](#task-api)       
        - [Code Documentation](#code-documentation)
        - [Docker Image](#docker-image)

## Description

The goal of this exercise is to build a daily task maintenance API. 
Below are some business rules:

- You can manager one or more Task(s) in the Task API.
- The technician performs tasks and is only able to see, create or update his own performed tasks.
  (We automatically add the task to the user that is logged in, only works if he is a technician)
- The manager can see tasks from all the technicians, delete them, and should be notified when some tech performs a task.

## Technical Specification

- Language: Java, version 8
- Framework: Spring Boot, version 2.1.0

## Development Tools

- Eclipse IDE 2018-12, version 4.9.0
- Apache Maven, version 3.6.0
- Apache Maven Javadoc Plugin, version 3.0.1
- Docker Maven Plugin, version 0.20.1

## Getting Started

### API

This application exposes 2 main APIs.

#### User API

    /authenticate
    
The API must accept `POST` requests to the `/authenticate` endpoint to login and obtain the session token. 
You can use the users who is already registered by default:

```
{
    "username":"manager",
    "password":"manager2021"
}
```


```
{
    "username":"technician",
    "password":"tech2021"
}
```

```
{
    "username":"john",
    "password":"john2021"
}
```


#### Task API
    
The API must be used by the Technician to Add, Update, setTaskPerformed, List or Find his own tasks.
The API must be used by the Manager to listAll, Find or Delete Tasks(s).

### Code Documentation

To access the code documentation, just run the server, then the documentation will be accessible at:

[![N|Solid](https://i.ibb.co/VSJ2kVJ/sw-readme.png)](https://i.ibb.co/VSJ2kVJ/sw-readme.png)

    http://localhost:8080/swagger-ui.html


### Docker Image

To run:

    docker-compose up web
