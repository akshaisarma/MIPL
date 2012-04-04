#!/bin/bash

echo "1. build the project"
#ant

echo "2. Compile the sample program into java source with MIPL compiler"
java -cp build edu.columbia.mipl.Main test/input/hello.mipl

echo "3. compile the java source"
javac -cp build build/MiplProgram.java

echo "4. Launch"
java -cp build MiplProgram
