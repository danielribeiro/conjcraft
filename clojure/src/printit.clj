(ns printit
  (:use [clojure.string :only [join]])
  )

(import '(net.minecraft.src Block Item Material))


(defn- field->map
  [cls field]
  (let [name (.getName field)]
    (try
      (let [value (clojure.lang.Reflector/getStaticField cls name)]
      (if (.isInstance cls value)
        {name value} {}))
      (catch Exception e {})
      )

    ))

(defn- declared-fields
  [cls]
  (apply merge
    (map
      (partial field->map cls)
      (.getDeclaredFields cls))))

(defn- outclass [cls]
  (for [[k _] (declared-fields cls)]
    (str "    " (-> cls .getSimpleName .toLowerCase) "s.put(\"" k "\", "
      (.getName cls) "." k ");\n")
    )
  )

(defn- class-str [inner-block]
  (format "package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

public class MinecraftConstants {
  final public static Map<String, Block> blocks = new HashMap<String, Block>();
  final public static Map<String, Item> items = new HashMap<String, Item>();
  final public static Map<String, Material> materials = new HashMap<String, Material>();
  static {
%s    }
  }" inner-block)
  )

(->> [Block Item Material] (mapcat outclass) join class-str
  (spit "src/java/MinecraftConstants.java"))

;(doall (map println (mapcat outclass [Block Item])))


