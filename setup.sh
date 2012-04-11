#!/usr/bin/env bash
set -e 
modpath=~/minecraft_modding
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
echo == Minecraft Setup Done ==
# todo clojure - > modloader/lib on: here
# todo assets -> mods on: install_modlader
# rename mymod -> clojure_mod.jar
# rename assets -> clojure_mod_assets