#!/usr/bin/env bash

# input files
dictionary_file=$1
input_file=$2

sbt clean compile

sbt "runMain com.pepperstone.challenge.ScrambledWordAnalysis $dictionary_file $input_file"