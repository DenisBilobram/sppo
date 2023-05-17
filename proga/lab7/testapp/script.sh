#!/bin/bash

# Создаем 3 фоновых сеанса screen с именами terminal1, terminal2 и terminal3
for i in {1..100}
do
    screen -dmS "terminal$i"
    screen -S "terminal$i" -X stuff "java -jar /home/notttk/sppo/proga/lab7/testapp/client/build/libs/client-all.jar localhost 4040\n"
    screen -S "terminal$i" -X stuff "login\n"
    screen -S "terminal$i" -X stuff "notttk\n"
    screen -S "terminal$i" -X stuff "20045\n"
    echo "$i"
    sleep 0.1
done
