(ns minecraftcl
  (:use clojure.test))


(defn identity-map [& list] (zipmap list list))
(def item-map (identity-map "swordDiamond" "iron" "stick"))
(def block-map (identity-map "dirt" "iron"))

;; Mocks for add-recipe
(defn add-recipe [stack array] {:stack stack :array array})
(defn item-stack [item qty] {:itemstack item :qty qty})
(defn items [item]
  (if (item-map item)
    {:item (item-map item)}
    nil))
(defn blocks [block]
  (if (block-map block)
    {:block (block-map block)}
    nil))
(require 'minecraft_base)


(defn same [x y] (is (= x y)))

;; Tests:
(deftest add-recipe-is-properly-mocked
  (same {:stack {:itemstack {:item "swordDiamond"}, :qty 1}, :array ["#" "#" "#" \# {:block "dirt"}]}
    (add-recipe (item-stack (items "swordDiamond") 1) ["#", "#", "#" \# (blocks "dirt")])))



(deftest recipe-dsl-invokes-add-recipe-properly
  (same {:stack {:itemstack {:item "swordDiamond"}, :qty 1}, :array ["#" "##" "#" \# {:block "dirt"}]}
    (single-recipe-dsl {\# :dirt}
     "#
      ##
      #" :swordDiamond 1)))

(deftest quantity-doesnt-need-to-be-passed-if-it-is-1
  (same {:stack {:itemstack {:item "swordDiamond"}, :qty 1}, :array ["#" "#" "#" \# {:block "dirt"}]}
    (single-recipe-dsl {\# :dirt}
     "#
      #
      #" :swordDiamond )))


(deftest multiple-recipes-in-one-dsl
  (let [expected {:stack {:itemstack {:item "swordDiamond"}, :qty 1},
                  :array ["#" "#" "#" \# {:block "dirt"}]}]

    (same [expected expected]
      (recipe-dsl {\# :dirt}
                    "#
                     #
                     #" :swordDiamond
        "#
         #
         #" :swordDiamond

        ))))

(deftest output-can-be-a-block
  (same {:stack {:itemstack {:block "dirt"}, :qty 1}, :array ["#" \# {:block "dirt"}]}
    (single-recipe-dsl {\# :dirt}
     "#" :dirt )))

(deftest when-block-names-collide-with-item-names-item-wins
  (same {:stack {:itemstack {:item "iron"}, :qty 1}, :array ["#" \# {:block "dirt"}]}
    (single-recipe-dsl {\# :dirt}
     "#" :iron )))

(deftest you-can-also-use-symbols-to-name-output
  (same {:stack {:itemstack {:item "iron"}, :qty 1}, :array ["#" \# {:block "dirt"}]}
    (single-recipe-dsl {\# :dirt}
     "#" 'iron )))


(deftest recipes-can-contain-scace
  (same {:stack {:itemstack {:item "iron"}, :qty 1}, :array ["# #" \# {:block "dirt"}]}
      (single-recipe-dsl {\# :dirt}
       "# #" 'iron )))

(deftest bigrecipes-require-underscore-instead-of-whitespaces
  (same {:stack {:itemstack {:item "iron"}, :qty 1}, :array ["# #" " # " "  #" \# {:block "dirt"}]}
      (single-recipe-dsl {\# :dirt}
       "#_#
        _#_
        __#" 'iron )))

(deftest get-translations-output-minecraft-translation-array
  (same [\# {:block "dirt"}]
    (get-translations {\# :dirt} ["#", "##", "#"])))


(deftest get-translations-output-minecraft-translation-array-of-items-too
  (same [\# {:item "stick"}]
    (get-translations {\# :stick} ["#", "##", "#"])))


(deftest crating-char-bindings-from-mere-symbols
  (same {\d "diamond"
         \s "sand"}
    (create-input-char-binding
      '(d diamond
        s sand))))

(run-tests 'minecraftcl)
