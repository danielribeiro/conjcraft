#!/usr/bin/env bash
set -e
minecraft_path="$HOME/Library/Application Support/minecraft"
modloader_url=http://dl.dropbox.com/u/20629262/Latest/ModLoader.zip

cd "$minecraft_path"
wget $modloader_url
unzip ModLoader.zip -d modloader
cd modloader
echo backing up minecraft.jar
cp ../bin/minecraft.jar ../bin/minecraft.jar.bak
jar uf ../bin/minecraft.jar *.class
zip -d  ../bin/minecraft.jar 'META-INF/*'
mv Modloader.zip Modloader.zip.bak
echo == Modloader installed ==
#possible: mkdir mods
