(ns advent-of-code-clojure.core)

(defn day5-data
  []
  (lines "day5.txt"))

(defn seat-id
  [code]
  (loop [id 0
         rem code]
    (if (empty? rem)
      id
      (let [c (first rem)]
        (recur
         (+ (* id 2) (if (some #{c} [\B \R]) 1 0))
         (rest rem))))))

(defn missing-sequential
  [s]
  (let [ordered (sort s)]
    (loop [exp (first ordered)
           rem ordered]
      (if (not= (first rem) exp)
        exp
        (recur (inc exp) (rest rem))))))

(defn max-id
  [codes]
  (->> codes
       (map seat-id)
       (apply max)))

(defn missing
  [codes]
  (->> codes
       (map seat-id)
       missing-sequential))
