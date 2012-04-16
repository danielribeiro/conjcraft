#!/usr/bin/env bash
set -e
echo "-> Setting up conjcraft's environment"
cd ~
wget https://github.com/downloads/danielribeiro/conjcraft/conjcraft-0.1.zip
unzip conjcraft-0.1.zip
mv conjcraft-0.1.zip conjcraft-0.1.zip.bak
cd conjcraft
cp *.jar *.zip "$minecraft_path/mods/"
