(ns advent-of-code-clojure.core
  (:require [clojure.java.io :as io]))

(def countif (comp count filter))

(defn rev-subs
  [s n]
  (subs s 0 (- (count s) n)))

(defn lines [fName]
  (line-seq (io/reader (str "resources/" fName))))

(defn parse-long
  [n]
  (Long/parseLong n))
