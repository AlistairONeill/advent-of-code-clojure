(ns advent-of-code-clojure.core)
(use '[clojure.string :only (split)])
(defn day7-data
  []
  (lines "day7.txt"))

(defn day7-example-data
  []
  (lines "day7.example.txt"))


(defn bag-name
  [raw]
  (re-find #"[a-zA-Z]+ [a-zA-Z]+" raw))

(defn bag-count
  [raw]
  (Integer. (re-find #"\d+" raw)))

(defn parse-contents
  [raw]
  (if (= raw "no other bags")
    {}
    (->> (split raw #", ")
         (map #(identity [(bag-name %) (bag-count %)]))
         (into {}))))

(defn rule
  [raw]
  (let [[container contents] (split (rev-subs raw 1) #" contain ")]
    [(bag-name container)
     (parse-contents contents)]))


(defn rules
  [raw]
  (->> raw
       (map rule)
       (into {})))

(defn can-contain?
  [rules internal parent]
  (let [contents (get rules parent)]
    (cond (= contents {}) false
          (contains? contents internal) true
          :else (some (partial can-contain? rules internal) (keys contents)))))

(defn contents-of
  "This includes itself!"
  [rules parent]
  (let [contents (get rules parent)]
    (if (= contents {}) 1
        (->> contents
             (map #(* (contents-of rules (first %)) (last %)))
             (reduce +)
             +
             inc ))))

(defn distinct-containers
  [rules type]
  (->> rules
       keys
       (filter (partial can-contain? rules type))
       count))

(defn day7-task1
  []
  (-> (day7-data)
      rules
      (distinct-containers "shiny gold")))

(defn day7-task2
  []
  (-> (day7-data)
      rules
      (contents-of "shiny gold")
      dec))
