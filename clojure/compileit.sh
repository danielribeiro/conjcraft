#!/usr/bin/env bash
set -e
cd ..
./recompile.sh
./reobfuscate.sh
zip ~/minecraft/mods/mymod.zip -j reobf/minecraft/*.class
