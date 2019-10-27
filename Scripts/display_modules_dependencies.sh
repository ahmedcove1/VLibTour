#! /bin/bash

mvn dependency:tree \
    | grep -v javax \
    | grep -v junit \
    | grep -v hamcrest \
    | grep -v slf4j \
    | grep -v rabbitmq \
    | grep -v apache \
    | grep -v jackson \
    | grep -v passay \
    | grep -v springframework \
    | grep -v commons \
    | grep -v glassfish \
    | grep -v javassist \
    | grep -v codehaus \
    | grep -v eclipse \
    | grep -v jboss \
    | grep -v shoal \
    | grep -v sun \
    | grep -v google \
    | grep -v jvnet \
    | grep -v 'Building ' \
    | grep -v 'maven-dependency-plugin' \
    | grep -v Reactor \
    | grep -v SUCCESS \
    | grep -v "Total time" \
    | grep -v Finished \
    | grep -v '\-\-\<' \
    | grep -v '\-\-\-\-'

exit
