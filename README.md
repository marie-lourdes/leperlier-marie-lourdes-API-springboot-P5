# Api SafetyNet Alerts
An Api  for managing datas of residents with their medical record 
and  datas of firestations  in order to prevent them in case of danger . 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them
- Java 17
- Maven 3.9.4
-SpringBoot 6.0.11
-Spring Tools 4


### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install Spring Tools for Eclipse

https://www.eclipse.org/community/eclipse_newsletter/2018/february/springboot.php

Follow this [link](https://www.jmdoudoux.fr/java/dej/chap-jdbc.htm) to help you creating connection with DB. 

### Running App

Post installation of MySQL, Java and Maven, you will have to run app to load data and all application with  Boot DashBoard of Spring Tools 
or with your CLI , 'mvn spring-boot:run '.

Finally, you will be ready to  use API and request 
The host and port is :'http://localhost:8080/'

### Endpoints  CRUD DataServices(except READ)

-/person
-/firestation
-/medicalrecord

### URL AlertsSafetyNetService(Only Read)
-/firestation?stationNumber=<station_number>
-/childAlert?address=<address>
-/phoneAlert?firestation=<firestation_number>
-fire?address=<address>
-/flood/stations?stations=<a list of station_numbers>
-personInfo?firstName=<firstName>&lastName=<lastName>
-/communityEmail?city=<city>

### Testing
 For testing use:
`run as` , then, 'Maven test' for unit test
