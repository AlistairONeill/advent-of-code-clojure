(ns advent-of-code-clojure.core)

(defn fix
  "Finds the n entries which sum to target and returns their product"
  [n entries target]
  (loop [entries entries
         target target]
    (if (= n 0)
      (if (= target 0) 1 nil)
      (if (empty? entries)
        nil
        (let [entry (first entries)
              result (fix (dec n) (rest entries) (- target entry))]
          (if (some? result)
            (* result entry)
            (recur (rest entries) target)))))))

(defn day1-data
  []
  (->> "day1.txt"
       (lines)
       (map #(Integer. %))))

(defn day1-1
  []
  (fix 2 (day1-data) 2020))

(defn day1-2
  []
  (fix 3 (day1-data) 2020))
