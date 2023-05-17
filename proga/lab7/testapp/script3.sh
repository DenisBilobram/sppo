#!/bin/bash

while true
do
    for i in {60..100}
    do
        screen -S "terminal$i" -X stuff "add\n"
        screen -S "terminal$i" -X stuff "yes\n"
        echo "$i"
    done
done