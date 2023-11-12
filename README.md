# API SafetyNet Alerts

An API RESTful  for managing datas of residents with their medical record 
and  datas of firestations  in order to prevent them in case of danger . 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them
- Java 17
- Maven 3.9.4
- SpringBoot 6.0.11
- Spring Tools 4

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install Spring Tools for Eclipse

https://www.eclipse.org/community/eclipse_newsletter/2018/february/springboot.php

### Running App

Post installation of Java, Maven and Spring Tools 4, you will have to run app to load data and all application with  Boot DashBoard of Spring Tools 
or with your CLI , mvn spring-boot:run .

Finally, you will be ready to  use API and request 
The host and port is :http://localhost:8080/

### Endpoints  CRUD DataServices(except READ)

## Person service

- POST: ** /person **
- PUT:  **/person?id='firstName lastName'**
- DELETE: ** /person?id='firstName lastName' **

## Firestation Service

- POST: **/firestation/'station_number'**
- POST: **/firestation/'address of firestation'**
- PUT: **/person?address='address of firestation'**
- DELETE: **/firestation/'station_number'**
- DELETE: **/firestation/'address of firestation'**

## MedicalRecord service

- POST: **/medicalRecord**
- PUT:  **/medicalRecord?id='firstName lastName'**
- DELETE: **/medicalRecord?id='firstName lastName'**

### URL AlertsSafetyNetService(only Read)

- GET: **/firestation?stationNumber='station_number'**
- GET: **/childAlert?address='address'**
- GET: **/phoneAlert?firestation='firestation_number'**
- GET: **/fire?address='address'**
- GET: **/flood/stations?stations='a list of station_numbers'**
- GET: **/personInfo?firstName='firstName'&lastName='lastName'**
- GET: **/communityEmail?city='city'**

### Testing

 For testing use:
`run as` , then, Maven test for unit test
