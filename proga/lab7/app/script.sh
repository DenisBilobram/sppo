#!/bin/bash

# Создаем фоновые сеансы screen с именами terminal1, terminal2 и terminal3...
for i in {1..600}
do
    screen -dmS "terminal$i"
    screen -S "terminal$i" -X stuff "java -jar /home/notttk/sppo/proga/lab7/app/client/build/libs/client-all.jar localhost 5050\n"
    screen -S "terminal$i" -X stuff "login\n"
    screen -S "terminal$i" -X stuff "notttk\n"
    screen -S "terminal$i" -X stuff "20045\n"
    echo "$i"
done
