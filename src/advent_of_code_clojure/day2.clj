(ns advent-of-code-clojure.core)

(def countif (comp count filter))

(defn schema1
  [num0 num1 char]
  (fn [password]
    (<= num0 (countif #(= char %) password) num1)))

(defn schema2
  [num0 num1 char]
  (fn [password]
    (let [a (nth password (dec num0))
          b (nth password (dec num1))]
      (or
       (and (= a char) (not= b char))
       (and (not= a char) (= b char))))))

(defn valid
  [schema line]
  (let [[_ num0 num1 [char] password] (re-matches #"^(\d+)-(\d+) (\w): (\w*)$" line)]
    ((schema (Integer. num0) (Integer. num1) char) password)))

(defn count-valid
  [schema lines]
  (countif (partial valid schema) lines))

(defn day2-data
  []
  (lines "day2.txt"))

(defn day2-1
  []
  (count-valid schema1 (day2-data)))

(defn day2-2
  []
  (count-valid schema2 (day2-data)))
