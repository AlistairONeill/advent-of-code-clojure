(ns advent-of-code-clojure.core
  (:require [clojure.java.io :as io]))

(def countif (comp count filter))

(defn lines [fName]
  (line-seq (io/reader (str "resources/" fName))))
