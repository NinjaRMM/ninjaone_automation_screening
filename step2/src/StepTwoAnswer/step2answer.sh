#!/bin/bash

echo $1
echo $2

dir=build/libs
jar_name=StepTwoAnswer-1.0-SNAPSHOT.jar

java -jar $dir/$jar_name $1 $2