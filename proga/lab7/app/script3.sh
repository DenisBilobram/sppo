#!/bin/bash

while true
do
    for i in {200..300}
    do
        screen -S "terminal$i" -X stuff "add\n"
        screen -S "terminal$i" -X stuff "yes\n"
        echo "$i"
    done
done