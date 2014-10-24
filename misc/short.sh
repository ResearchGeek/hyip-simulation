#!/bin/bash
awk -F";" '$1 % 5 == 0' $1 > $2