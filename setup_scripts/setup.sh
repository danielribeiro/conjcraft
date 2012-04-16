#!/usr/bin/env bash
modpath=~/conjcraft/minecraft_modding
mcp_url=http://mcp.ocean-labs.de/files/mcp62.zip
modloader_url=http://dl.dropbox.com/u/20629262/Latest/ModLoader.zip
if [ 'Linux' = `uname -s` ]
then minecraft_path="$HOME/.minecraft"
else  
  minecraft_path="$HOME/Library/Application Support/minecraft"
fi

echo "-> Installing and configuring MCP"
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
yes Yes | ./updatemcp.sh
./decompile.sh
cd ..
./create_constants.sh
cp bin/*.jar minecraft_modding/lib/
cp java/* minecraft_modding/src/minecraft/net/minecraft/src/
echo == Minecraft Setup Done ==
