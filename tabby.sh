#!/bin/sh

command=$1

pIDa=`lsof -i :8080|grep -v "PID" | awk '{print $2}'`

if [ $command = "start" ]; then
    if [ $pIDa ! = "" ]; then
        echo "port 8080 has been used by proccess(PID=$pIDa)!"
    else
        echo ""
        echo "--------------------------------------------"
        echo "| _____          ___  ___  _    _          |"
        echo "|   |      /\    |  \ |  \  \  /    web    |"
        echo "|   |     /__\   |__/ |__/   \/     server |"
        echo "|   |    /    \  |  \ |  \    |            |"
        echo "|   |   /      \ |__/ |__/    |     v 0.0.2|"
        echo "--------------------------------------------"
        echo ""
        echo "tabby started!"
        echo "http:127.0.0.1:8080"
        echo ""
        java -jar tabby-1.0-SNAPSHOT.jar
    fi
elif [ $command = "restart" ]; then
    if [ $pIDa = "" ]; then
        echo ""
        echo "tabby has never been started！"
    else
        echo ""
        echo "tabby restating..."
        kill -9 $pIDa
        nowpIDa=`lsof -i :8080|grep -v "PID" | awk '{print $2}'`
        if [ $nowpIda = "" ]; then
            echo ""
            echo "tabby restarted! "
            java -jar tabby-1.0-SNAPSHOT.jar
        else
            echo ""
            echo "tabby restart failed,port 8080 has been used by proccess(PID=$nowpIda)!"
        fi
    fi
elif [ $command = "stop" ]; then
    echo "tabby stoping..."
else
    echo ""
    echo "Unknow command $command! You can use:start stop restart"
fi
