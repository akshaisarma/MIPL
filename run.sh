#!/bin/bash

#1. build the project
#ant

#2. Compile the sample program into java source with MIPL compiler
java -cp build edu.columbia.mipl.Main test/input/hello.mipl

#3. compile the java source
javac -cp build build/MiplProgram.java

#4. Launch
java -cp build MiplProgram
