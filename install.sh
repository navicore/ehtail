#!/usr/bin/env bash

sbt clean assembly

mkdir -p ~/bin
cp bin/* ~/bin/
cp target/scala-2.12/EhTail.jar ~/bin/

