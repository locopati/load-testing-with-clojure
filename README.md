## What is this?
Demo code for a talk at Clojure/West 2012 called <i>Load Testing with Clojure</i>. The demo code consists of a simplistic mathematics server built with [Compojure](https://github.com/weavejester/compojure) and [Incanter](http://incanter.org/), some test functions using [clojure.test](http://richhickey.github.com/clojure/clojure.test-api.html), and [Grinder](http://grinder.sourceforge.net/) test scripts. The talk slides, mind map, and PDF are in the docs directory.

## Preliminaries
<b>Setup the project</b>

```bash
git clone git://github.com/locopati/load-testing-with-clojure.git
cd load-testing-with-clojure
lein deps # see https://github.com/technomancy/leiningen if you need leiningen
```

## The Math Server
<b>To start the server</b>

lein ring server-headless 9999

<b>To make requests to the server</b>

http://localhost:9999/1%20+%202

(the expression must have spaces around the operator - some browsers will automatically convert a space to %20)

<b>Where is the server code?</b>

src/math/core.clj

test/math/test.clj

## The Grinder
<b>To start an agent</b>

bin/grinder agent start [optional number of agents to start - defaults to 1]

<b>To start the console</b>

bin/grinder console start

<b>To run tests</b>

In the Script tab, set the root directory to the $PROJECT_HOME/grinder

Select working.properties and set it as proprties file to use (the star button)

Open working.properties and uncomment the script file to use

Click the play button

In the results tab, you should see test results

## Oh the Places we Could Go
Use [Pallet](http://palletops.com/) to create Grinder agent instances

User [Avout](http://avout.io/) to create a concurrent, distributed test source