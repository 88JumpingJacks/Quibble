#!/bin/bash



for i in $(find $(1) -name "*.txt");
do
    echo "running test $i"
    javac Quibble3/src/Quibble.class Quibble3/src/Constants.class
    java Quibble3/src/Quibble Quibble3/Current_Events_File Quibble3/eventTransactions < $i.txt #> /QuibbleOutputs/$i.log

done
