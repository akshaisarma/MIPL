#!/bin/bash

INPUT=$*
[ $# -lt 1 ] && INPUT=test/input/classification_mr.mipl

rm -rf temp

rm -f build/MiplProgram.java
rm -f build/MiplProgram.class

i=1

i=`expr $i`
echo "$i. Compile the sample program"

java -ea -esa -cp build:./lib/bcel-5.2.jar edu.columbia.mipl.Main -output MiplProgram $INPUT

[ -e build/MiplProgram.java ] && i=`expr $i + 1` && echo "$i. compile the java source" && javac -cp build build/MiplProgram.java


