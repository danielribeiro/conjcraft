#!/usr/bin/env bash
set -e 
modpath=~/conjcraft/minecraft_modding
minecraft_path="$HOME/Library/Application Support/minecraft"
mcp_url=http://mcp.ocean-labs.de/files/mcp62.zip
modloader_url=http://dl.dropbox.com/u/20629262/Latest/ModLoader.zip

cd ~
mkdir -p $modpath
cd $modpath
wget $mcp_url
unzip mcp62.zip
cp -rf "$minecraft_path/bin" "$minecraft_path/resources" jars
cd  jars
wget $modloader_url
unzip ModLoader.zip -d modloader
cd modloader
jar uf ../bin/minecraft.jar *.class
zip -d  ../bin/minecraft.jar 'META-INF/*'
cd ../../
./decompile.sh
cd ..
cp bin/*.jar minecraft_modding/lib/
echo == Minecraft Setup Done ==
