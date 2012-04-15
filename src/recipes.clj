(ns clojurecraft)


(def char-block (create-input-char-binding
                  '(
                     t dirt
                     s sand
                     c cobblestone
                     a sandStone
                     o oreCoal
                     i oreIron
                     l oreLapis
                     r oreRedstone
                     g oreGold
                     d oreDiamond)))

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

(defn block-sequence-recipe
  "Creates recipes that takes a low quality block and outputs a higher one. Starts at dirt,
  and ends at blockDiamond!"
  [] (doseq [[in out] (partition 2 1 (map (comp blocks name) seq-recipe-input))]
       (add-recipe (item-stack out 1) ["x" \x in])))

(defn create-recipes []
  (do
    (block-sequence-recipe)
    (single-recipe-dsl {\x :dirt}
      "_x_
      x_x
      _x_" :plateDiamond 10
      )
    ))
