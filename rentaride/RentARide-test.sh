#!/bin/sh

# IMPORTANT:  this test is not repeatable as not all of the data is deleted

# run the test
#
echo "==========================================================================="
echo "== Writing sample data"
echo
java -cp classes:/opt/classes/mysql-connector-java-5.1.37-bin.jar:/opt/classes/freemarker.jar:/opt/classes/servlet-api.jar edu.uga.cs.rentaride.test.object.RentARideTester
