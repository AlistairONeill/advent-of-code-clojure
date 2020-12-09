(ns advent-of-code-clojure.core)

(defn day9-data
  []
  (map parse-long (lines "day9.txt")))

(defn day9-test-data
  []
  (map parse-long (lines "day9.example.txt")))

(defn invalid?
  [data pre index]
  (let [target (nth data index)]
    (loop [rem (drop (- index pre) data)]
      (let [r (rest rem)
            t (- target (first rem))
            b (some #{t} r)]
        (cond (empty? r) target
              b false
              :else (recur r))))))

(defn first-invalid
  [data pre]
  (->> data
       count
       (range pre)
       (some (partial invalid? data pre))))

(defn contiguous-sum
  [target data]
  (reduce
   (fn [acc x]
     (let [sum (+ (:sum acc) x)
           l (cons x (:l acc))]
       (cond (> sum target) (reduced nil)
             (= sum target) (reduced l)
             :else {:sum sum :l l})))
   {:sum 0 :l '()}
   data))


(defn weakness
  [data pre]
  (let [invalid (first-invalid data pre)]
    (loop [rem data]
      (let [sum (contiguous-sum invalid rem)]
        (if (some? sum) 
          (+ (apply min sum) (apply max sum))
          (recur (rest rem)))))))

(defn day9-task1
  []
  (first-invalid (day9-data) 25))

(defn day9-task2
  []
  (weakness (day9-data) 25))
