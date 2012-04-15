(ns clojurecraft)

(import clojure.lang.Reflector)
(import java.io.File)

; Reflection helpers
(defn inst [clas & args]
  (Reflector/invokeConstructor clas (to-array args)))

(defn callit [target method & args]
  (Reflector/invokeStaticMethod target (str method)
    (to-array args)))

; Core helpers
(defn load-sibling
  "Load a clojure file that is on the same folder as this"
  [filename]
  (load-file
    (.getAbsolutePath (File. (-> *file* File. .getParent) (str (name filename) ".clj")))))

(defn mapit
  "Convert java hashmap into a clojure hashmap"
  [hashmap] (into {} hashmap))

; The state
(def Block)
(def ItemStack)
(def ModLoader)
(def blocks)
(def items)
(def materials)
(def cur-block-id 180)

; Wrapping the reflection calls into more frindly functions
(defn add-recipe [stack array]
  (callit ModLoader 'addRecipe stack (to-array array)))

(defn register-block [block]
  (callit ModLoader 'registerBlock block))

(defn name-block [block name]
  (callit ModLoader 'addName block name))

(defn item-stack [block n] (inst ItemStack block n))

(defn intg [x] (Integer. x))
(defn create-block
  "Example of how to a basic block in clojure."
  [name texturefile]
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

; Function loaded from java, and other modules.
(load-sibling :recipe_dsl)
(load-sibling :recipes)

(defn call [jblocks jitems jmaterials Block-cls ItemStack-cls ModLoader-cls]
  (do
    (def Block Block-cls)
    (def ItemStack ItemStack-cls)
    (def ModLoader ModLoader-cls)
    (def items (mapit jitems))
    (def materials (mapit jmaterials))
    (def blocks (assoc (mapit jblocks)
      "clojure" (create-block "Github Block" "/16_github.png")
      "github" (create-block "Clojure Block" "/16_clojure.png")))
    (create-recipes)))
