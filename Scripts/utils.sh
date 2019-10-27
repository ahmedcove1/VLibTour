#!/bin/bash

# in this shell script, only idempotent assignments of shell variables for
# the Java class-path

export MAVEN_REPOS=${HOME}/.m2/repository

export PATHSEP=':'

export RABBITMQ_CLIENT_VERSION=5.4.1
export AMQPCLIENT=${MAVEN_REPOS}/com/rabbitmq/amqp-client/${RABBITMQ_CLIENT_VERSION}/amqp-client-${RABBITMQ_CLIENT_VERSION}.jar
export SL4J=${MAVEN_REPOS}/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar
export SL4JSIMPLE=${MAVEN_REPOS}/org/slf4j/slf4j-simple/1.7.25/slf4j-simple-1.7.25.jar

export COMMONLANG3=${MAVEN_REPOS}/org/apache/commons/commons-lang3/3.6/commons-lang3-3.6.jar
export COMMONLOGGING=${MAVEN_REPOS}/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar

export LOG4J=${MAVEN_REPOS}/log4j/log4j/1.2.17/log4j-1.2.17.jar

export GSON=${MAVEN_REPOS}/com/google/code/gson/gson/2.8.5/gson-2.8.5.jar
export GEOCALC=${MAVEN_REPOS}/com/grum/geocalc/0.5.1/geocalc-0.5.1.jar
export JXMAPVIEWER=${MAVEN_REPOS}/org/jxmapviewer/jxmapviewer2/2.2/jxmapviewer2-2.2.jar
export OPENSTREEPMAP=${MAVEN_REPOS}/org/openstreetmap/jmapviewer/jmapviewer/2.3/jmapviewer-2.3.jar

export VLIBTOUR=${MAVEN_REPOS}/eu/telecomsudparis/vlibtour

export TOURMANAGEMENTENTITY=${VLIBTOUR}/vlibtour-tour-management/vlibtour-tour-management-entity/1.0-SNAPSHOT/vlibtour-tour-management-entity-1.0-SNAPSHOT.jar
export TOURMANAGEMENTAPI=${VLIBTOUR}/vlibtour-tour-management/vlibtour-tour-management-api/1.0-SNAPSHOT/vlibtour-tour-management-api-1.0-SNAPSHOT.jar
export TOURMANAGEMENTBEAN=${VLIBTOUR}/vlibtour-tour-management/vlibtour-tour-management-bean/1.0-SNAPSHOT/vlibtour-tour-management-bean-1.0-SNAPSHOT.jar

export LOBBYROOMAPI=${VLIBTOUR}/vlibtour-lobby-room-system/vlibtour-lobby-room-api/1.0-SNAPSHOT/vlibtour-lobby-room-api-1.0-SNAPSHOT.jar
export LOBBYROOMSERVER=${VLIBTOUR}/vlibtour-lobby-room-system/vlibtour-lobby-room-server/1.0-SNAPSHOT/vlibtour-lobby-room-server-1.0-SNAPSHOT.jar

export CLIENTLOBBYROOM=${VLIBTOUR}/vlibtour-client/vlibtour-client-lobby-room/1.0-SNAPSHOT/vlibtour-client-lobby-room-1.0-SNAPSHOT.jar
export CLIENTGROUPCOMMSYSTEM=${VLIBTOUR}/vlibtour-client/vlibtour-client-group-communication-system/1.0-SNAPSHOT/vlibtour-client-group-communication-system-1.0-SNAPSHOT.jar
export CLIENTEMULATIONVISIT=${VLIBTOUR}/vlibtour-client/vlibtour-client-emulation-visit/1.0-SNAPSHOT/vlibtour-client-emulation-visit-1.0-SNAPSHOT.jar

export JERSEYHTTP=${MAVEN_REPOS}/org/glassfish/jersey/containers/jersey-container-grizzly2-http/2.17/jersey-container-grizzly2-http-2.17.jar:\
${MAVEN_REPOS}/org/glassfish/hk2/external/javax.inject/2.4.0-b10/javax.inject-2.4.0-b10.jar:\
${MAVEN_REPOS}/org/glassfish/grizzly/grizzly-http-server/2.3.16/grizzly-http-server-2.3.16.jar:\
${MAVEN_REPOS}/org/glassfish/grizzly/grizzly-http/2.3.16/grizzly-http-2.3.16.jar:\
${MAVEN_REPOS}/org/glassfish/grizzly/grizzly-framework/2.3.16/grizzly-framework-2.3.16.jar:\
${MAVEN_REPOS}/org/glassfish/jersey/core/jersey-common/2.17/jersey-common-2.17.jar:\
${MAVEN_REPOS}/javax/annotation/javax.annotation-api/1.2/javax.annotation-api-1.2.jar:\
${MAVEN_REPOS}/org/glassfish/jersey/bundles/repackaged/jersey-guava/2.17/jersey-guava-2.17.jar:\
${MAVEN_REPOS}/org/glassfish/hk2/hk2-api/2.4.0-b10/hk2-api-2.4.0-b10.jar:\
${MAVEN_REPOS}/org/glassfish/hk2/hk2-utils/2.4.0-b10/hk2-utils-2.4.0-b10.jar:\
${MAVEN_REPOS}/org/glassfish/hk2/external/aopalliance-repackaged/2.4.0-b10/aopalliance-repackaged-2.4.0-b10.jar:\
${MAVEN_REPOS}/org/glassfish/hk2/hk2-locator/2.4.0-b10/hk2-locator-2.4.0-b10.jar:\
${MAVEN_REPOS}/org/javassist/javassist/3.18.1-GA/javassist-3.18.1-GA.jar:\
${MAVEN_REPOS}/org/glassfish/hk2/osgi-resource-locator/1.0.1/osgi-resource-locator-1.0.1.jar:\
${MAVEN_REPOS}/org/glassfish/jersey/core/jersey-server/2.17/jersey-server-2.17.jar:\
${MAVEN_REPOS}/org/glassfish/jersey/core/jersey-client/2.17/jersey-client-2.17.jar:\
${MAVEN_REPOS}/org/glassfish/jersey/media/jersey-media-jaxb/2.17/jersey-media-jaxb-2.17.jar:\
${MAVEN_REPOS}/javax/validation/validation-api/1.1.0.Final/validation-api-1.1.0.Final.jar:\
${MAVEN_REPOS}/javax/ws/rs/javax.ws.rs-api/2.0.1/javax.ws.rs-api-2.0.1.jar

export JERSEYJSON=${MAVEN_REPOS}/org/glassfish/jersey/media/jersey-media-json-jackson/2.17/jersey-media-json-jackson-2.17.jar:\
${MAVEN_REPOS}/org/glassfish/jersey/ext/jersey-entity-filtering/2.17/jersey-entity-filtering-2.17.jar:\
${MAVEN_REPOS}/com/fasterxml/jackson/jaxrs/jackson-jaxrs-base/2.3.2/jackson-jaxrs-base-2.3.2.jar:\
${MAVEN_REPOS}/com/fasterxml/jackson/core/jackson-core/2.3.2/jackson-core-2.3.2.jar:\
${MAVEN_REPOS}/com/fasterxml/jackson/core/jackson-databind/2.3.2/jackson-databind-2.3.2.jar:\
${MAVEN_REPOS}/com/fasterxml/jackson/jaxrs/jackson-jaxrs-json-provider/2.3.2/jackson-jaxrs-json-provider-2.3.2.jar:\
${MAVEN_REPOS}/com/fasterxml/jackson/module/jackson-module-jaxb-annotations/2.3.2/jackson-module-jaxb-annotations-2.3.2.jar:\
${MAVEN_REPOS}/com/fasterxml/jackson/core/jackson-annotations/2.3.2/jackson-annotations-2.3.2.jar

export VISITCOMMON=${VLIBTOUR}/vlibtour-libraries/vlibtour-common/1.0-SNAPSHOT/vlibtour-common-1.0-SNAPSHOT.jar

export EMULATIONVISITSERVER=${VLIBTOUR}/vlibtour-emulation-visit/1.0-SNAPSHOT/vlibtour-emulation-visit-1.0-SNAPSHOT.jar



export SCENARIO=${VLIBTOUR}/vlibtour-scenario/1.0-SNAPSHOT/vlibtour-scenario-1.0-SNAPSHOT.jar

export UTILPASSAY=${MAVEN_REPOS}/org/passay/passay/1.3.0/passay-1.3.0.jar
