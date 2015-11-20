#!/bin/bash
# We accept the Quibble component name as input because the file names are
# parsed based on this
# The user must run this shell script from
# <user path>/QuibbleAssignment3/Shell_Scripts directory

# User inputs test result directories
CONSOLE_LOGS_DIR=$1
TRANSACTION_LOG_DIR=$2

# Go to Expected_Console_Output_Files directory to compare console output logs
for EXPECTED_CONSOLE_OUTPUT_FILE in $(find "$CONSOLE_LOGS_DIR" -name "*.txt");
do
    # Component name + number
    # Ex) add1, create3, etc.
    COMPONENT_NAME_NUMBER=`basename $EXPECTED_CONSOLE_OUTPUT_FILE _out.txt`

    echo "Checking console outputs of test $COMPONENT_NAME_NUMBER"

    # Compare expected and actual console output logs
    diff -w $CONSOLE_LOGS_DIR/Console_test_output/"$COMPONENT_NAME_NUMBER"_out.log $EXPECTED_CONSOLE_OUTPUT_FILE
done

# Go to Expected_Event_Transaction_Files to compare event transactions logs
for EXPECTED_EVENT_TRANSACTION_LOG_FILE in $(find "$TRANSACTION_LOG_DIR" -name "*.trn");
do
    COMPONENT_NAME_NUMBER=`basename $EXPECTED_EVENT_TRANSACTION_LOG_FILE .trn`

    echo "Checking event transactions log outputs of test $COMPONENT_NAME_NUMBER"

    # Compare expected and actual event transaction logs
    diff -w $TRANSACTION_LOG_DIR/Console_test_output/"$COMPONENT_NAME_NUMBER".trn $EXPECTED_EVENT_TRANSACTION_LOG_FILE
done