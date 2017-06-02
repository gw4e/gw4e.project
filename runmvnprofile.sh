#!/bin/bash

if [ "$1" != "" ]; then
    mvn clean install -P$1
else
    echo "Positional parameter 1 is empty"
fi