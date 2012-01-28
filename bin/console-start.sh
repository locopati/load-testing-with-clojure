#!/bin/bash -x

. `dirname "$0"`/grinder-env.sh

[ -z "$1" ] && { echo "USAGE: console-start dev-username [remote-ip,...]"; exit 1; }
[ -n "$2" ] && remote=$2

ps -ef | grep ssh | grep 6372
[ $? -ne 0 ] && { echo Opening SSH tunnel; ssh -f -N -R6372:127.0.0.1:6372 $1@dev.junlabs.com; } || { echo SSH tunnel is already open; }

java -Djun.app=grinder-console -Xms128m -Xmx1024m -cp $CLASSPATH net.grinder.Console `[ -n "$remote" ] && echo "-headless "` &> console.log &

# TODO handle multiple IPs
if [ -n "$remote" ]; then

    lein run :remote-run
    
    wait_interval=45s

    while true; do	
	grep -i finished console.log
	[ $? -eq 0 ] && break
	echo "Tests are still running - checking again in $wait_interval" && sleep $wait_interval
    done	

    echo "Fetching logs"
    dir_name="player/request/grinder/log"
    tar_name=remote-run.tbz
    remote_addr=`whoami`@$remote
    ssh $remote_addr "cd $dir_name && ls -t {out,data}* | head -2 | tar cjf $tar_name -T -"
    scp $remote_addr:$dir_name/$tar_name .

    echo "Checking success"
    tar xf $tar_name
    grep FAIL out*.log
    [ $? -eq 0 ] && echo There were test failures || echo All tests were successful

    kill `jps | grep Console | cut -d" " -f1`
    kill `ps -ef | grep ssh | grep 6372 | cut -f2 -d" "`

fi