#!/bin/bash

if [ ! -d $1 ] ; then
	echo "$1 is not a directory"
	exit 1
fi
