#!/bin/bash

while true ; do
	read N
	if `test "$N" == "stop"`; then
		break
	fi
done

for I in $@; do
	if [[ -f $I ]] ; then
		echo "$I It's a file";

	elif [[ -d $I ]] ; then
		echo "$I It's a directory";

	else
		echo "$I It's something else";
	fi
done

if [[ -d /lab7 ]]
then
    echo "/lab7 exists on your filesystem."
fi
