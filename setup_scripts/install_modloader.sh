#!/usr/bin/env bash
set -e
minecraft_path="$HOME/Library/Application Support/minecraft"
modloader_url=http://dl.dropbox.com/u/20629262/Latest/ModLoader.zip

cd "$minecraft_path"
wget $modloader_url
unzip ModLoader.zip -d modloader
mv Modloader.zip Modloader.zip.bak
cd modloader
echo backing up minecraft.jar
cp ../bin/minecraft.jar ../bin/minecraft.jar.bak
jar uf ../bin/minecraft.jar *.class
zip -d  ../bin/minecraft.jar 'META-INF/*'
mkdir -p ../mods

echo == Modloader installed ==