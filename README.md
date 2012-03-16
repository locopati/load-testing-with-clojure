## What is this?
Demo code for a talk at Clojure/West 2012 called <i>Load Testing with Clojure</i>. The demo code consists of a simplistic mathematics server built with [Compojure](https://github.com/weavejester/compojure) and [Incanter](http://incanter.org/), some test functions using [clojure.test](http://richhickey.github.com/clojure/clojure.test-api.html), and [Grinder](http://grinder.sourceforge.net/) test scripts.

## The math server
<i>To start the server</i>
lein ring server-headless 9999

<i>To make requests to the server</i>
http://localhost:9999/1%20+%202
(the expression must have spaces around the operator - some browsers will automatically convert a space to %20)

<i>Where is the server code?</i>
src/math/core.clj
test/math/test.clj

## The Grinder
<i>To start an agent</i>
bin/grinder agent start [optional number of agents to start - defaults to 1]

<i>To start the console</i>
bin/grinder console start

<i>To run tests</i>
In the Script tab, set the root directory to the $PROJECT_HOME/grinder
Select working.properties and set it as proprties file to use (the star button)
Open working.properties and uncomment the script file to use
Click the play button
In the results tab, you should see test results
