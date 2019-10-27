#!/bin/bash

# test Java 8
if ! $(java -version 2>&1 | head -1 | grep '\"1\.8' > /dev/null); then
    echo "Not a JAVA 8 version: '$(java -version 2>&1 | head -1)'"
    exit 1
fi

# configure variables
. "$(cd $(dirname "$0") && pwd)"/utils.sh

ARGS=$*

CLASSPATH=${CLASSPATH}${PATHSEP}${AMQPCLIENT}${PATHSEP}${SL4J}${PATHSEP}${SL4JSIMPLE}${PATHSEP}${COMMONLANG3}${PATHSEP}${COMMONLOGGING}${PATHSEP}${LOG4J}${PATHSEP}${GEOCALC}${PATHSEP}${JXMAPVIEWER}${PATHSEP}${OPENSTREEPMAP}${PATHSEP}${TOURMANAGEMENTENTITY}${PATHSEP}${TOURMANAGEMENTAPI}${PATHSEP}${LOBBYROOMAPI}${PATHSEP}${LOBBYROOMSERVER}${PATHSEP}${VISITCOMMON}${PATHSEP}${CLIENTLOBBYROOM}${PATHSEP}${CLIENTGROUPCOMMSYSTEM}${PATHSEP}${CLIENTEMULATIONVISIT}${PATHSEP}${SCENARIO}${PATHSEP}${UTILPASSAY}${PATHSEP}${GSON}${PATHSEP}${JERSEYHTTP}${PATHSEP}${JERSEYJSON}${PATHSEP}

CLASS=vlibtour.vlibtour_client_scenario.VLibTourVisitTouristApplication

# Start the client
CMD="java -cp ${CLASSPATH} ${CLASS} ${ARGS}"

# this script is launched by sourcing => & and export
$CMD &
echo "$!" >> ~/.vlibtour/tourist_applications
