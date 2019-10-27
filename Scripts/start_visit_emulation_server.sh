#!/bin/bash

# test Java 8
if ! $(java -version 2>&1 | head -1 | grep '\"1\.8' > /dev/null); then
    echo "Not a JAVA 8 version: '$(java -version 2>&1 | head -1)'"
    exit 1
fi

# configure variables
. "$(cd $(dirname "$0") && pwd)"/utils.sh

ARGS=$*

CLASSPATH=${CLASSPATH}${PATHSEP}${VISITCOMMON}${PATHSEP}${GSON}${PATHSEP}${GEOCALC}${PATHSEP}${LOG4J}${PATHSEP}${EMULATIONVISITSERVER}${PATHSEP}${JERSEYHTTP}${PATHSEP}${JERSEYJSON}${PATHSEP}

CLASS=vlibtour.vlibtour_emulation_visit.VisitEmulationServer

# Start the client
CMD="java -cp ${CLASSPATH} ${CLASS} ${ARGS}"

# this script is launched by sourcing => & and export
$CMD &
echo "$!" > ~/.vlibtour/visit_emulation_server
