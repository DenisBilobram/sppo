#!/bin/bash

while true
do
    for i in {100..300}
    do
        screen -S "terminal$i" -X stuff "add\n"
        screen -S "terminal$i" -X stuff "yes\n"
    done
done