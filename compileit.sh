#!/usr/bin/env bash
./recompile.sh
./reobfuscate.sh
zip ~/minecraft/mods/mymod.zip -j reobf/minecraft/*.class
