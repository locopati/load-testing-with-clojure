#!/bin/sh

# load vars 
. `dirname "$0"`/grinder-env.sh

# before running, sanity checks
test -f $GRINDER_PID && { echo "Agent is already running"; exit 1; }
test -z "$1" && { echo "You must pass the console host as a command-line parameter"; exit 1; }
test -z "$RAILS_ENV" && { echo "RAILS_ENV has not been set - did you want to set RAILS_ENV?"; }

# run agent
java -Djun.app=grinder-agent -Dgrinder.consoleHost=$1 -cp $CLASSPATH net.grinder.Grinder -daemon 30 $GRINDER_CONFIG/base.properties > $GRINDER_LOG/agent.log &

# on successful run
if [ $? -eq 0 ] 
then 
echo $! > $GRINDER_PID
echo "Grinder agent started"
fi
