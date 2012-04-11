(ns minecraftcl
  (:use [clojure.string :only [split-lines trim]]))

(defn get-translations [input-char-binding input]
  (let [charset (set (apply concat input))
        clean-charset (remove #(or (= \space %) (= \_ %)) charset)
        ]
    (mapcat #(vector % (->> % input-char-binding name blocks)) clean-charset)))

(defn get-output-item [output-name]
  (let [normalized-name (name output-name)
        item (items normalized-name)]
       (if item item (blocks normalized-name))))

(defn single-recipe-dsl
  ([input-char-binding recipe-str output-name output-qty]
    (let [output-item (get-output-item output-name)
          input (->> recipe-str split-lines (map #(clojure.string/replace (trim %) #"_" " ")))
          translations (get-translations input-char-binding input)]
      (add-recipe (item-stack output-item output-qty) (concat input translations))))

  ([input-char-binding recipe-str output-name]
    (single-recipe-dsl input-char-binding recipe-str output-name 1)))

(defn recipe-dsl [input-char-binding & single-recipes]
  (let [recipe-partitions (->> (partition-by string? single-recipes) (partition 2))
        recipe-args (map #(concat (first %) (second %)) recipe-partitions)
        recipe-with-binding (partial single-recipe-dsl input-char-binding)]
       (map #(apply recipe-with-binding %) recipe-args)))
