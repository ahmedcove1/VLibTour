This module is provided for the emulation of a visit in Paris. It
proposes generic methods to build the graph of positions as a set of
adjacency list and to search for paths from a departure node to a
destination node, and utility methods to manage the pathway in the
choosen path.

In the class GraphOfPositionsForEmulation, the graph of positions is
built in the method initTourWithPOIs. In the same class, the generic
method addEdge builds the graph as a set of adjacency sets and the
generic method computePathsFromDepartureToDestination computes all the
paths from a departure position to a destination position.

The classVisitEmulationServer proposes public methods that are the API
of the REST server---i.e., getNextPOIPosition, getCurrentPosition,
stepInCurrentPath, stepsInVisit. Please refer to Javadoc class
documentation and the JUnit test classes to learn how to use this
module.

The code of this module is complete. We thus provide some code to test the
server:
(1) as JUnit test (vlibtour.vlibtour_emulation_visit.TestScenario), and
(2) a pseudo-client class with a main
    (vlibtour.vlibtour_emulation_visit.VisitEmulationTestClient).
Thus, you can execute the following commands:
$ mvn clean install # it includes the execution of the JUnit test.
$ mvn exec:java@server
# wait for the line
# Jersey app started with WADL available at http://localhost:8888/VisitEmulation/application.wadl
# the following command in another terminal
$ mvn exec:java@client
