#!/usr/bin/env bash
set -e
if [ 'Linux' = `uname -s` ]
then minecraft_path="$HOME/.minecraft"
else  
  minecraft_path="$HOME/Library/Application Support/minecraft"
fi

echo "-> Setting up conjcraft's environment"
cd ~
wget https://github.com/downloads/danielribeiro/conjcraft/conjcraft-0.1.zip
unzip conjcraft-0.1.zip
mv conjcraft-0.1.zip conjcraft-0.1.zip.bak
cd conjcraft
cp *.jar *.zip "$minecraft_path/mods/"
