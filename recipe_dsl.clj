(ns conjcraft ^{:doc "Basic Recipe DSL functions."}
  (:use [clojure.string :only [split-lines trim]]))


(defn get-output-item [output-name]
  (let [normalized-name (name output-name)
        item (items normalized-name)]
    (if item item (blocks normalized-name))))

(defn create-input-char-binding [symbol-list]
  (let
    [zip-zymbol-list (partition 2 (map name symbol-list))]
    (into {} (map (fn [[k v]] [(first k) v]) zip-zymbol-list))))


(defn get-translations [input-char-binding input]
  (let [charset (set (apply concat input))
        clean-charset (remove #(or (= \space %) (= \_ %)) charset)
        clean-binding-name (comp get-output-item input-char-binding)]
    (mapcat #(vector % (clean-binding-name %)) clean-charset)))

(defn safe-add-recipe
  "Like add-recipe, but catches exceptions and gives better error reporting"
  [stack array]
  (try (add-recipe stack array)
    (catch Exception e
      (do
        (print "Bad recipe: ")
        (prn stack array)
        (.printStackTrace e))))
  )

(defn single-recipe-dsl
  ([input-char-binding recipe-str output-name output-qty]
    (let [output-item (get-output-item output-name)
          trim-and-space #(-> % trim (clojure.string/replace #"_" " "))
          input (->> recipe-str split-lines (map trim-and-space))
          translations (get-translations input-char-binding input)]
      (safe-add-recipe (item-stack output-item output-qty) (concat input translations))))

  ([input-char-binding recipe-str output-name]
    (single-recipe-dsl input-char-binding recipe-str output-name 1)))

(defn non-lazy-map [f coll] (doall (map f coll)))

(defn recipe-dsl [input-char-binding & single-recipes]
  (let [recipe-partitions (->> (partition-by string? single-recipes) (partition 2))
        recipe-args (map #(concat (first %) (second %)) recipe-partitions)]
    (non-lazy-map (partial apply single-recipe-dsl input-char-binding) recipe-args)))
