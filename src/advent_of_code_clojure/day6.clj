(ns advent-of-code-clojure.core)
(use '[clojure.string :only (split)])

(defn day6-data
  []
  (-> "resources/day6.txt"
       slurp
       (split #"\n\n")))

(defn any-response
  [responses]
  (->> responses
       (filter #(not= \newline %))
       set
       count))

(defn in-all
  [responses c]
  (every? #(some #{c} %) responses))

(defn all-response
  [raw]
  (let [responses (split raw #"\n")]
    (->> responses
         first
         (filter (partial in-all responses))
         set
         count)))

(defn day6-task1
  []
  (->> (day6-data)
       (map any-response)
       (reduce +)))

(defn day6-task2
  []
  (->> (day6-data)
       (map all-response)
       (reduce +)))
