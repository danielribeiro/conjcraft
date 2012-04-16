#!/usr/bin/env bash
set -e
if [ 'Linux' = `uname -s` ]
then minecraft_path="$HOME/.minecraft"
else  
  minecraft_path="$HOME/Library/Application Support/minecraft"
fi

echo "-> Releasing mod to your minecraft folder"
cp bin/*.jar "$minecraft_path/mods/"
cp java/* minecraft_modding/src/minecraft/net/minecraft/src/
cd minecraft_modding
yes Yes | ./updatemcp.sh
./recompile.sh
./reobfuscate.sh
zip "$minecraft_path/mods/conjcraft.zip" -j reobf/minecraft/*.class
