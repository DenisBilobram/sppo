#!/bin/bash

while true
do
    for i in {30..59}
    do
        screen -S "terminal$i" -X stuff "add\n"
        screen -S "terminal$i" -X stuff "yes\n"
        echo "$i"
    done
done