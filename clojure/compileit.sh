#!/usr/bin/env bash
set -e
cd ..
./recompile.sh
./reobfuscate.sh
zip ~/minecraft/mods/clojurecraft.zip -j reobf/minecraft/*.class
