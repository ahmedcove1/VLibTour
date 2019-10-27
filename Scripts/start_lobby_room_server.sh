#!/bin/bash

# test Java 8
if ! $(java -version 2>&1 | head -1 | grep '\"1\.8' > /dev/null); then
    echo "Not a JAVA 8 version: '$(java -version 2>&1 | head -1)'"
    exit 1
fi

# configure variables
. "$(cd $(dirname "$0") && pwd)"/utils.sh

ARGS=$*

CLASSPATH=${CLASSPATH}${PATHSEP}${AMQPCLIENT}${PATHSEP}${SL4J}${PATHSEP}${SL4JSIMPLE}${PATHSEP}${COMMONLANG3}${PATHSEP}${COMMONLOGGING}${PATHSEP}${LOG4J}${PATHSEP}${PATHSEP}${LOBBYROOMAPI}${PATHSEP}${LOBBYROOMSERVER}${PATHSEP}${UTILPASSAY}${PATHSEP}

CLASS=vlibtour.vlibtour_lobby_room_server.VLibTourLobbyServer

# Start the client
CMD="java -cp ${CLASSPATH} ${CLASS} ${ARGS}"

# this script is launched by sourcing => & and export
$CMD &
echo "$!" > ~/.vlibtour/lobby_room_server
