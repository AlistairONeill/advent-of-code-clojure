(ns advent-of-code-clojure.core)
(use '[clojure.string :only (split)])

(defn day4-data []
  (slurp "resources/day4.txt"))

(defn split-keys
  [input]
  (let [[_ k v] (re-matches #"^(.*):(.*)$" input)]
    [k v]))

(defn parse-passport
  [data]
  (->> #"[ \n]"
       (split data)
       (map split-keys)
       (into {})))

(defn parse-passports
  [data]
  (->> #"\n\n"
       (split data)
       (map parse-passport)))


(defn field-validator
  [field validator]
  (fn [passport]
    (->> field
         passport
         validator)))

(defn validate-all
  [validators]
  (fn [passport]
    (try
      (loop [rem validators]
        (if (empty? rem)
          true
          (if ((first rem) passport)
            (recur (rest rem))
            false)))
      (catch Exception e false)
      )))

(defn count-valid
  [data validator]
  (->> data
       parse-passports
       (countif validator)))

(defn validate-exists
  [field]
  (field-validator field some?))

(defn int-range-validator
  [min max]
  (fn [value]
    (let [i (Integer/parseInt value)]
      (and (>= i min) (<= i max)))))

(defn regex-validator
  [regex]
  (fn [value]
    (some? (re-matches regex value))))

(defn contains-validator
  [values]
  (fn [value]
    (some #(= % value) values)))

(def byr-validator (int-range-validator 1920 2002))
(def iyr-validator (int-range-validator 2010 2020))
(def eyr-validator (int-range-validator 2020 2030))
(def hcl-validator (regex-validator #"^#[0-9a-f]{6}$"))
(def ecl-validator (contains-validator ["amb" "blu" "brn" "gry" "grn" "hzl" "oth"]))
(def pid-validator (regex-validator #"^\d{9}$"))
(defn hgt-validator
  [value]
  (let [[_ num units] (re-matches #"^(\d+)(\w+)$" value)
        i (Integer/parseInt num)]
    (cond
      (= units "cm") (and (>= i 150) (<= i 193))
      (= units "in") (and (>= i 59) (<= i 76))
      :else false)))

(def task1Validator
  (validate-all
   [
    (validate-exists "byr")
    (validate-exists "iyr")
    (validate-exists "eyr")
    (validate-exists "hgt")
    (validate-exists "hcl")
    (validate-exists "ecl")
    (validate-exists "pid")
    ]))

(def task2Validator
  (validate-all
   [
    (field-validator "byr" byr-validator)
    (field-validator "iyr" iyr-validator)
    (field-validator "eyr" eyr-validator)
    (field-validator "hgt" hgt-validator)
    (field-validator "hcl" hcl-validator)
    (field-validator "ecl" ecl-validator)
    (field-validator "pid" pid-validator)
    ]))
