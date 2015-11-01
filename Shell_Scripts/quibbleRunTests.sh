# The user needs to run this file from the Quibble Java src folder
#!/bin/bash
cd ../src
javac Quibble.java Constants.java

DIR=$1

for i in $(find "$DIR" -name "*.txt");
do
    echo "running test $i"

    LOG_NAME=`basename $i _in.txt`
    java Quibble ../Current_Events_File ../eventTransactions < $i > ../Input_and_Output_Files/Log_Files/"$LOG_NAME"_out.log
    # call Quibble exit command to terminate session
done
