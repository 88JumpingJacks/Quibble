#!/bin/bash
# User must run the shell script from the Java Project directory as
# we store the shell scripts in the base folder for the Java Project.
# The working directory is specified as a relative path based on the above
# assumption
cd ../src
javac Quibble.java Constants.java

DIR=$1

for INPUT_FILE in $(find "$DIR" -name "*.txt");
do
    # Component name + number
    # Ex) add1, create3, etc.
    COMPONENT_NAME_NUMBER=`basename $INPUT_FILE _in.txt`
    echo "running test $COMPONENT_NAME_NUMBER"

    # Linux ">" (write command output to file, used in line above) automatically
    # adds a newline character to the end of the file so we need to remove this
    # as it is not part of Quibble output by using printf %s
    printf %s "$(java Quibble ../Current_Events_File ../Input_and_Output_Files/Output_Event_Transaction_Log_Files/"$COMPONENT_NAME_NUMBER".trn < $INPUT_FILE)" > ../Input_and_Output_Files/Output_Console_Log_Files/"$COMPONENT_NAME_NUMBER"_out.log
done
