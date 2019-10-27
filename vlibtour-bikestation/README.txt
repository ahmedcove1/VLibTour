To compile and install, execute the following command:
$ mvn clean install

The services are first of all tested in JUnit tests.

Secondly, you can start a server and then some clients using two consoles.
In the first console, execute the following command:
$ mvn exec:java@server
...
Hit enter to stop it...
Then, in the second console, execute the following commands:

$ mvn exec:java@client

To stop the server, hit return in the first console.

Thirdly, the server can be tested using a Web browser.
In the console,  execute the following command:
$ mvn exec:java@server
...
Hit enter to stop it...
Then,test some of the services from a Web browser with those URLs
http://localhost:9999/MyServer/application.wadl

TODO put URL for the JCDECAUX server and from the emulation server

1) request a key to the API


The classes Position.java and Station.java have been generated with
http://www.jsonschema2pojo.org/
by examing an example of a JSON station
https://api.jcdecaux.com/vls/v1/stations/7102?contract=paris&apiKey=91f170cdabb4c3227116c3e871a63e8d3ad148ee
{
    "number":7102,
    "name":"07102 - SAINTE CLOTHILDE",
    "address":"FACE 19 RUE CASIMIR PERIER - 75007 PARIS",
    "position":{"lat":48.857829110709226,"lng":2.319149052579355},
    "banking":true,
    "bonus":false,
    "status":"OPEN",
    "contract_name":"Paris",
    "bike_stands":42,
    "available_bike_stands":13,
    "available_bikes":25,
    "last_update":1505671910000
}

Be careful! Chose "long integer" and "jackson 2"
