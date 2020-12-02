(ns advent-of-code-clojure.core
  (:require [clojure.java.io :as io]))

(defn lines [fName]
  (line-seq (io/reader (str "resources/" fName))))
