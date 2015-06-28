(ns realfa.db)

(def default-db
  {:name   "re-frame"
   :matrix (into {} (map #(vector % (into [] (repeat 7 0)))
                         (range 2015 (+ 2015 8))))})

(declare other-data)



(def app-db
  (->> (range 2015 2023)
       (map #(vector % (int-array 10 0)))
       (into {})
       (merge other-data)))
