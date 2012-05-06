#!/bin/bash

INPUT=$*
[ $# -lt 1 ] && INPUT=test/input/multi_jobs.mipl

java -ea -esa -cp build:./lib/bcel-5.2.jar edu.columbia.mipl.Main $INPUT


