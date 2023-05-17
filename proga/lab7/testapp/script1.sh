#!/bin/bash

while true
do
    for i in {1..29}
    do
        screen -S "terminal$i" -X stuff "add\n"
        screen -S "terminal$i" -X stuff "yes\n"
        echo "$i"
    done
done