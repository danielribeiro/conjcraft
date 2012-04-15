(ns clojurecraft)


(def char-block (create-input-char-binding
                  '(
                     d dirt
                     o cobblestone
                     g github
                     c clojure
                     )))

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

(defn block-sequence-recipe
  "Creates recipes that takes a low quality block and outputs a higher one. Starts at dirt,
  and ends at blockDiamond!"
  [] (doseq [[in out] (partition 2 1 (map (comp blocks name) seq-recipe-input))]
       (add-recipe (item-stack out 1) ["x" \x in])))

(defn recipes []
  (recipe-dsl char-block
     "d
      d" 'github 2

     "o
      o" 'clojure 2

     "g_g
      ggg
      ggg" 'plateDiamond 6
    )
  )

(defn create-recipes []
  (do
    (block-sequence-recipe)
    (prn "now loading recipes")
    (recipes)
    ))
