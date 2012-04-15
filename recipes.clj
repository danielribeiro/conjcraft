(ns clojurecraft)

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
                         blockDiamond
                         ))


(defn block-sequence-recipe
  "Creates recipes that takes a low quality block and outputs a higher one. Starts at dirt,
  and ends at blockDiamond!"
  [] (doseq [[in out] (partition 2 1 (map (comp blocks name) seq-recipe-input))]
       (add-recipe (item-stack out 1) ["x" \x in])))


(def char-block (create-input-char-binding
                  '(
                     d dirt
                     o cobblestone
                     g github
                     c clojure
                     r redstone
                     )))

(defn recipes []
  (recipe-dsl char-block
     "d
      d" 'github

     "o
      o" 'clojure

     "c
      c
      c" 'swordGold

     "_c_
      _c_
      gcg" 'swordDiamond

     "ccc
      c c" 'helmetGold

     "ccc
      cgc" 'helmetDiamond

     "c c
      c c" 'bootsGold

     "cgc
      cgc" 'bootsDiamond

     "ccc
      c c
      c c" 'legsGold

     "ccc
      cgc
      cgc" 'legsDiamond

     "c c
      ccc
      ccc" 'plateGold

     "cgc
      ccc
      ccc" 'plateDiamond

     "ggg
      g g
      ggg" 'eyeOfEnder

     "ggg
      gcg
      ggg" 'eyeOfEnder 64

     "ccc
      c c
      ccc" 'appleGold 64

     "ccc
      cgc
      ccc" 'fireballCharge 64

     "ooo
      ogo
      oro" 'dispenser 2
    ))

(defn create-recipes []
  (do
    (block-sequence-recipe)
    (recipes)))
