(ns advent-of-code-clojure.core)

(defn day8-data
  []
  (lines "day8.txt"))

(defn day8-test-data
  []
  (lines "day8.example.txt"))

(defn parse-command
  [raw]
  (let [[_ cmd val] (re-find #"^(\w{3}) ([\+\-]\d+)$" raw)]
    {:cmd cmd
     :val (Integer. val)}))

(defn action
  [line state]
  (let [cmd (:cmd line)
        val (:val line)
        i (:i state)
        acc (:acc state)]
    (cond (= cmd "nop") (assoc state :i (inc i))
          (= cmd "acc") (assoc state :i (inc i) :acc (+ acc val))
          (= cmd "jmp") (assoc state :i (+ i val)))))


(defn acc-on-loop
  [lines]
  (loop [state {:i 0 :acc 0}
         visited #{}]
    (let [i (:i state)]
      (if (some #(= i %) visited)
        (:acc state)
        (recur
         (action (nth lines i) state)
         (conj visited i))))))

(defn acc-on-happy-termination
  [lines]
  (let [c (count lines)]
    (loop [state {:i 0 :acc 0}
           visited #{}]
      (let [i (:i state)]
        (cond (= i c) (:acc state)
              (> i c) nil
              (some #(= i %) visited) nil
              :else (recur
                     (action (nth lines i) state)
                     (conj visited i)))))))

(defn fix-line
  ([lines i]
   (let [l (vec lines)]
     (assoc l i (fix-line (nth l i)))))
  ([line]
   (let [cmd (:cmd line)]
     (cond (= cmd "nop") (assoc line :cmd "jmp")
           (= cmd "jmp") (assoc line :cmd "nop")
           (= cmd "acc") line))))

(defn acc-after-fix
  [lines]
  (->> lines
       count
       range
       (map (partial fix-line lines))
       (some acc-on-happy-termination)))



(defn day8-task1
  []
  (->> (day8-data)
       (map parse-command)
       acc-on-loop))

(defn day8-task2
  []
  (->> (day8-data)
       (map parse-command)
       acc-after-fix))
