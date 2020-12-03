(ns advent-of-code-clojure.core)

(defn n-rest
  [n data]
  (if (or (empty? data) (<= n 0))
    data
    (recur (dec n) (rest data))))

(defn trees-hit
  [rows dX dY]
  (loop [count 0
         x 0
         remaining rows]
    (if (empty? remaining)
      count
      (recur (if (hits x (first remaining)) (inc count) count) (+ x dX) (n-rest dY remaining)))))

(defn hits
  [x row] 
  (= (nth row (mod x (count row))) \#))

(defn day3-data
  []
  (lines "day3.txt"))

(defn day3-1
  []
  (trees-hit (day3-data) 3 1))


(def gradients
  '((1 1)
    (3 1)
    (5 1)
    (7 1)
    (1 2)))

(defn day3-2
  []
  (let [f (partial apply trees-hit (day3-data))]
    (->> gradients
         (map f)
         (reduce *))))


