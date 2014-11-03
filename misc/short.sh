#!/bin/bash
awk -F";" 'NR == 1 || $3 % 20 == 0' $1 > $2