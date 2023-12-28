#!/bin/bash

function build_dir()  # $1 is the dir to get it
{
    cd $1
    bash build.sh
    cd ..
}

echo "** Building all"

build_dir "PrimaBank-Banker"
build_dir "PrimaBank-Transaction"
build_dir "PrimaBank-TransactionSaver"
build_dir "PrimaBank-Offers"
build_dir "PrimaBank-Client"
build_dir "PrimaBank-Gateway"
build_dir "PrimaBank-Marketing"
build_dir "PrimaBank-Marketing-Decision-Tree"
build_dir "PrimaBank-Statistics"

echo "** Done all"

