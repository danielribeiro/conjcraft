(ns minecraftcl)

(import clojure.lang.Reflector)
(import java.io.File)

(defn inst [clas & args]
  (Reflector/invokeConstructor clas (to-array args)))

(defn callit [target method & args]
  (Reflector/invokeStaticMethod target (str method)
    (to-array args)))


(defn mapit
  "Convert java hashmap into a clojure hashmap"
  [hashmap] (into {} hashmap))

(def Block)
(def ItemStack)
(def ModLoader)
(def blocks)
(def items)
(def materials)
(def cur-block-id 180)
(defn intg [x] (Integer. x))

(defn add-recipe [stack array]
  (callit ModLoader 'addRecipe stack (to-array array)))

(defn register-block [block]
  (callit ModLoader 'registerBlock block))

(defn name-block [block name]
  (callit ModLoader 'addName block name))

(defn item-stack [block n]
  (inst ItemStack block n))

(defn recipe [blocks items materials]
  (let [dirt (blocks "dirt")]
    (add-recipe (item-stack dirt 7) ["#" \# dirt])))


(load-file
  (.getAbsolutePath (File. (-> *file* File. .getParent) "minecraft_base.clj")))

(defn create-block [name texturefile]
  (let [droppedfn (constantly (intg cur-block-id))
        quantityfn (constantly (intg 7))
        ;Integer. required: http://dev.clojure.org/jira/browse/CLJ-445
        block (inst Block (intg cur-block-id) (intg 0)
      (materials "sand") droppedfn quantityfn)
        texture (callit ModLoader 'addOverride "/terrain.png" texturefile)]
    (doto block
      (.hardness 0.0)
      (.lightValue 1.0)
      (.blockName name)
      (.setTexture texture))
    (register-block block)
    (name-block block name)
    (def cur-block-id (inc cur-block-id))
    block))

(defn clojure-block []
  (let [block (create-block "Clojure Block" "/16_clojure.png")]
    ;(add-recipe (item-stack block 7) ["#" \# (blocks "dirt")])
    (add-recipe (item-stack (items "swordDiamond") 1) ["x", "x", "x" \x block])
    ))

(defn github-block []
  (let [block (create-block "Github Block" "/16_github.png")]
    block
;    (add-recipe (item-stack block 7) ["#" \# (blocks "cobblestone")])

    ))


(defn create-block-chars []
  (let [input '(t dirt
                 s sand
                 c cobblestone
                 a sandStone
                 o oreCoal
                 i oreIron
                 l oreLapis
                 r oreRedstone
                 g oreGold
                 d oreDiamond)]
    (reduce (fn [val [k v]] (assoc val (->> k name first) (name v))) {} (partition 2 input))
    )
  )
(defn reverse-map
  "Reverse the keys/values of a map"
  [m]
  (into {} (map (fn [[k v]] [v k]) m)))

(def char-block (create-block-chars))
(def block-char (reverse-map char-block))
(def seq-recipe-input '(dirt
                         sand
                         cobblestone
                         sandStone
                         stone
                         oreCoal
                         blockSnow
                         oreIron
                         oreLapis
                         oreRedstone
                         oreGold
                         oreDiamond
                         blockDiamond))

;Error: clojure.lang.Var$Unbound cannot be cast to clojure.lang.DynamicClassLoader
;    (println (eval `(new ~Block-cls 180 0 ~(materials "sand"))))
(defn call [jblocks jitems jmaterials Block-cls ItemStack-cls ModLoader-cls]
  (do
    (def Block Block-cls)
    (def ItemStack ItemStack-cls)
    (def ModLoader ModLoader-cls)
    (def blocks (mapit jblocks))
    (def items (mapit jitems))
    (def materials (mapit jmaterials))
    (github-block)
    (clojure-block)
    (doseq [[in out] (partition 2 1 seq-recipe-input)]
      (add-recipe (item-stack (->> out name blocks) 1) ["x" \x (->> in name blocks)])
      )
    (single-recipe-dsl {\x :dirt}
      "_x_
       x_x
       _x_" :plateDiamond 10
      )
    ))
