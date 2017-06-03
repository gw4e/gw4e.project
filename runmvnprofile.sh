#!/bin/bash

export DISPLAY=:99.0
sh -e /etc/init.d/xvfb start
sleep 10 # give xvfb some time to start

printenv

if [ "$1" != "" ]; then
    mvn clean install -P$1
else
    echo "Positional parameter 1 is empty"
fi