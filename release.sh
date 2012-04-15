#!/usr/bin/env bash
set -e
minecraft_path="$HOME/Library/Application Support/minecraft"
cp bin/*.jar "$minecraft_path/mods/"
cp java/* minecraft_modding/src/minecraft/net/minecraft/src/
cd minecraft_modding
yes Yes |./updatemcp.sh
./recompile.sh
./reobfuscate.sh
zip "$minecraft_path/mods/conjcraft.zip" -j reobf/minecraft/*.class
