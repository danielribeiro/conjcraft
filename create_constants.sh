#!/usr/bin/env bash
java -cp .:bin/clojure-1.3.0.jar:../bin/minecraft:../jars/bin/jinput.jar:../jars/bin/lwjgl.jar:../jars/bin/lwjgl_util.jar:../jars/bin/md5s:../jars/bin/minecraft.jar:../jars/bin/minecraft.jar.bak:../jars/bin/natives:../jars/bin/version  clojure.main create_constants.clj
