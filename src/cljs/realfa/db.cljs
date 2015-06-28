(ns realfa.db)

(def default-db
  {:name   "re-frame"
   :matrix (into {} (map #(vector % (long-array 7 0))
                         (range 2015 (+ 2015 8))))})
