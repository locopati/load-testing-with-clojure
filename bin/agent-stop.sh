#!/bin/bash

. `dirname "$0"`/grinder-env.sh

if [ -f $GRINDER_PID ]
then
kill `cat $GRINDER_PID`
rm $GRINDER_PID
fi
