This is the case study "VlibTour".

Since Glassfish requires JAVA 1.8, we assume that you have a shell script 'java8', which is acessible via the shell variable PATH:
$cat java8
#! /bin/bash
JAVA_HOME=... # adapt the path to your configuration
CLASSPATH=$JAVA_HOME/lib
export PATH CLASSPATH JAVA_HOME

To compile and install, execute the following command:
$ (. java8;mvn clean install)

To run the scenario of the demonstrator:
$ (. java8;./run_scenario_w_mapviewer.sh)

Here follows some explanations on the content.
The shell script of the scenario is 'run_scenario_w_mapviewer.sh'. It uses the
shell scripts of the directory 'Scripts'.
The Maven modules are the following ones,
mainly in the order of their compilation:
- root: the VLibTour project;
- vlibtour-libraries: the libraries for VLibTour:
  - Geocalc: external libraries without any Maven repository, thus explaining
    why the source code is included here;
  - vlibtour-common: the VLibTour library for common classes;
- vlibtour-tour-management: The VLibTour Tour Management (EJB technology):
  - vlibtour-tour-management-api: the VLibTour Tour Management API,
  - vlibtour-tour-management-entity: the VLibTour Tour Management entity;
  - vlibtour-tour-management-bean: the VLibTour Tour Management bean server;
- vlibtour-lobby-room-system: the VLibTour Lobby Room System (AMQP technology):
  - vlibtour-lobby-room-api: the API of the VLibTour lobby room;
  - vlibtour-lobby-room-server: the VLibTour lobby room server;
- vlibtour-emulation-visit: the REST server of the VlibTour emulation
  of a visit in a graph positions (Bikestation services and
  POIs [points of interest])
- vlibtour-client: the VLibTour Client modules:
  - vlibtour-admin-client-tour-management: the VLibTour Tour Management client
    (to populate the data base for the EJB entities);
  - vlibtour-client-lobby-room: the VLibTour client of the lobby room
    (AMQP technology);
  - vlibtour-client-group-communication-system: the VLibTour client
    of the group communication system (AMQP technology);
  - vlibtour-client-emulation-visit: the client of the VlibTour emulation
    of a visit (REST technology)
- vlibtour-scenario: the VLibTour Scenario module, which includes the client
  application VLibTourVisitTouristApplication and the classes to manage an
  OpenStreetMap map viewer.
The directory structure of these Maven modules is as follows:
.
├── pom.xml
├── README.txt
├── run_scenario_w_mapviewer.sh
├── Scripts
├── vlibtour-libraries
    ├── geocalc
    └── vlibtour-common
├── vlibtour-tour-management
    ├── vlibtour-tour-management-api
    ├── vlibtour-tour-management-bean
    └── vlibtour-tour-management-entity
├── vlibtour-lobby-room-system
    ├── vlibtour-lobby-room-api
    └── vlibtour-lobby-room-server
├── vlibtour-emulation-visit
├── vlibtour-client
    ├── vlibtour-admin-client-tour-management
    ├── vlibtour-client-emulation-visit
    ├── vlibtour-client-group-communication-system
    └── vlibtour-client-lobby-room
└── vlibtour-scenario
The dependencies of the Maven modules is as follows (we detail only the
dependencies upon modules of functional components, hence ignoring dependencies
upon modules of the technologies used [EJB, AMQP, REST, etc.]):
- vlibtour-libraries:
  - Geocalc: n/a;
  - vlibtour-common: none;
- vlibtour-tour-management:
  - vlibtour-tour-management-entity: none;
  - vlibtour-tour-management-api: depends upon
    * vlibtour-tour-management-entity;
  - vlibtour-tour-management-bean: dependen upon
    * vlibtour-tour-management-entity,
    * vlibtour-tour-management-api;
- vlibtour-lobby-room-system: 
  - vlibtour-lobby-room-api: none;
  - vlibtour-lobby-room-server: depends upon
    * vlibtour-lobby-room-api;
- vlibtour-emulation-visit: depends upon
    * vlibtour-common;
- vlibtour-client:
  - vlibtour-admin-client-tour-management: depends upon
    * vlibtour-tour-management-entity,
    * vlibtour-tour-management-api;
  - vlibtour-client-lobby-room: depends upon
    * vlibtour-lobby-room-api,
    * vlibtour-lobby-room-server;
  - vlibtour-client-group-communication-system: depends upon
    * vlibtour-lobby-room-api;
  - vlibtour-client-emulation-visit: depends upon
    * vlibtour-common;
- vlibtour-scenario: depends upon
    * vlibtour-common,
    * vlibtour-tour-management-entity,
    * vlibtour-tour-management-api,
    * vlibtour-lobby-room-server,
    * vlibtour-client-lobby-room,
    * vlibtour-client-group-communication-system,
    * vlibtour-client-emulation-visit.
