#!/usr/bin/env bash
set -e
cp src/java/* ../src/minecraft/net/minecraft/src/
cd ..
./recompile.sh
./reobfuscate.sh
zip ~/minecraft/mods/clojurecraft.zip -j reobf/minecraft/*.class
