(ns advent-of-code-clojure.core)


(defn clean-data
  [source]
  (->> source
       lines
       (map parse-long)
       sort
       (cons 0)))

(defn day10-data
  []
  (clean-data "day10.txt"))

(defn day10-example1-data
  []
  (clean-data "day10.example1.txt"))

(defn day10-example2-data
  []
  (clean-data "day10.example2.txt"))


(defn task1
  [data]
  (loop [state {:one 0 :three 0}
         rem data]
    (if (= 1 (count rem)) (* (:one state) (inc (:three state)))
        (recur
         (let [diff (- (second rem) (first rem))]
           (cond (= 1 diff) (update state :one inc)
                 (= 3 diff) (update state :three inc)
                 :else state))
         (rest rem)))))


(defn split-3
  [data]
  (loop [state {:current []
                :all []
                :rem data}]
    (let [current (:current state)
          all (:all state)
          rem (:rem state)
          n (first rem)]
      (if (= 1 (count rem)) (conj all (conj current n))
          (recur
           (if (= 3 (- (second rem) n))
             {:current [] :all (conj all (conj current n)) :rem (rest rem)}
             {:current (conj current n) :all all :rem (rest rem)}))))))

(defn split-4
  [data]
  (loop [state {:current []
                :all []
                :rem data}]
    (let [current (:current state)
          all (:all state)
          rem (:rem state)
          n (first rem)
          c (count rem)]
      (if (= 1 c) (conj all (conj current n))
          (let [m (second rem)]
            (if (= 2 c) (conj all (conj current n m))
                (recur
                 (if (> (- (nth rem 2) n) 3)
                   {:current [m] :all (conj all (conj current n m)) :rem (drop 2 rem)}
                   {:current (conj current n) :all all :rem (rest rem)}))))))))

(defn split-4-groups
  [data]
  (loop [acc []
         rem (map split-4 data)]
    (if (empty? rem) acc
        (recur
         (reduce conj acc (first rem))
         (rest rem)))))



(defn perms
  [data]
  (if (< (count data) 3) 1
      (let [split (split-4-groups (split-3 data))]
        (if (not= 1 (count split))
          (reduce * (map perms split))
          (let [n (quot (count data) 2)]
            (+ (perms (concat (take n data) (drop (inc n) data)))
               (* (perms (take (inc n) data)) (perms (drop n data)))))))))


(defn task2
  [data]
  (perms data))
