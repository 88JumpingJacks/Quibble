#!/bin/bash
cd ../Input_and_Output_Files/Expected_Output_Files

for EXPECTED_OUTPUT in $(find . -name "*.txt");
do
    echo "Checking outputs of test $EXPECTED_OUTPUT"

    COMPONENT_NAME_NUMBER=`basename $EXPECTED_OUTPUT _out.txt`
    diff -w ../Log_Files/"$COMPONENT_NAME_NUMBER"_out.log $EXPECTED_OUTPUT
done