#!/bin/bash
echo "Hello world"
echo $@

S=0
for I in `ls *.txt` ; do 
	echo $I
	N=`grep -E "^$" $I | wc -l`
	S=`expr $S + $N`
done

echo $S

S=0

for I in `find -type f -name "*.txt"`; do
	echo $I
	N=`grep -E "^$" $I | wc -l`
	S=`expr $S + $N`
done

echo $S
