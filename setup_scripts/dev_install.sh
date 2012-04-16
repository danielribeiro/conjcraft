#!/usr/bin/env bash
set -e
echo "-> Setting up conjcraft's development environment"
cd ~
git clone git://github.com/danielribeiro/conjcraft.git
cd conjcraft
setup_scripts/setup.sh

echo == Conjcraft development environment installed ==
./run_tests.sh
