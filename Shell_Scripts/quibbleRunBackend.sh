#!/bin/bash
# This script is called by quibbleRunTests.sh (located in Shell_Scripts 
# folder of Quibble Java project)
# That script is assumed to be run from the Shell_Scripts directory of 
# the Java Quibble project as noted in quibbleRunTests.sh
# This script represents the overnight batch processor for the backend.
DIR=$1

# Back Office files
NEW_MASTER_EVENTS_FILE="../Master_Events_File"
NEW_CURRENT_EVENTS_FILE="../Current_Events_File"
MERGED_EVENT_TRANSACTION_FILES="../Merged_Event_Transaction_Files"

for TRANSACTION_FILE in $(find "$DIR" -name "*.trn")
do
	# Merge every individual transaction file to MERGED_EVENT_TRANSACTION_FILES
	paste $TRANSACTION_FILE >> $MERGED_EVENT_TRANSACTION_FILES
done

# Go to src folder of Java project
cd ../src
javac Quibble.java Constants.java BackOffice.java MasterEventTransactionsInfo.java

java Quibble


