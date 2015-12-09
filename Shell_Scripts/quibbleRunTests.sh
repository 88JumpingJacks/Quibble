#!/bin/bash
# User must run the shell script from the Java Project directory as
# we store the shell scripts in the base folder for the Java Project.
# The working directory is specified as a relative path based on the above
# assumption
cd ../src
javac Quibble.java Constants.java

# Input file directory
DIR=$1

# Get current date and time
YEAR=`date '+%Y'`
MONTH=`date '+%m'`
DAY=`date '+%d'`
HOUR=`date '+%H'`
MINUTE=`date '+%M'`
SECOND=`date '+%S'`

# Create output directory for this test run
# Directory name format:
# Ex) ../Input_and_Output_Files/Test_Runs_Output/Test_Run_2015_12_25-11_12_43
TEST_OUTPUT_DIR=../Input_and_Output_Files/Test_Runs_Output/Test_Run_${YEAR}_${MONTH}_${DAY}-${HOUR}_${MINUTE}_${SECOND}
mkdir $TEST_OUTPUT_DIR

CONSOLE_OUTPUT_DIR=$TEST_OUTPUT_DIR/Console_test_output
mkdir $CONSOLE_OUTPUT_DIR

TRANSACTIONS_OUTPUT_DIR=$TEST_OUTPUT_DIR/Transactions_test_output
mkdir $TRANSACTIONS_OUTPUT_DIR

for INPUT_FILE in $(find "$DIR" -name "*.txt");
do
    # Component name + number
    # Ex) add1, create3, etc.
    COMPONENT_NAME_NUMBER=`basename $INPUT_FILE _in.txt`
    echo "running test $COMPONENT_NAME_NUMBER"

    # Linux ">" (write command output to file, used in line above) automatically
    # adds a newline character to the end of the file so we need to remove this
    # as it is not part of Quibble output by using printf %s
    printf %s "$(java Quibble < $INPUT_FILE)" > $CONSOLE_OUTPUT_DIR/"$COMPONENT_NAME_NUMBER"_out.log

    DAILY_EVENT_TRANSACTION_FILE="../../Quibble_Files_Assignment6/Daily_Event_Transaction_File"
    cp $DAILY_EVENT_TRANSACTION_FILE $TRANSACTIONS_OUTPUT_DIR/"$COMPONENT_NAME_NUMBER".trn
done