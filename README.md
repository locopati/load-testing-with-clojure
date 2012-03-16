## What is this?
Demo code for a talk at Clojure/West 2012 called <i>Load Testing with Clojure</i>. The demo code consists of a simplistic mathematics server built with [Compojure](https://github.com/weavejester/compojure) and [Incanter](http://incanter.org/), some test functions using [clojure.test](http://richhickey.github.com/clojure/clojure.test-api.html), and [Grinder](http://grinder.sourceforge.net/) test scripts.

## The Math Server
To start the server

lein ring server-headless 9999

To make requests to the server

http://localhost:9999/1%20+%202

(the expression must have spaces around the operator - some browsers will automatically convert a space to %20)

Where is the server code?

src/math/core.clj

test/math/test.clj

## The Grinder
To start an agent

bin/grinder agent start [optional number of agents to start - defaults to 1]

To start the console

bin/grinder console start

To run tests

In the Script tab, set the root directory to the $PROJECT_HOME/grinder

Select working.properties and set it as proprties file to use (the star button)

Open working.properties and uncomment the script file to use

Click the play button

In the results tab, you should see test results
