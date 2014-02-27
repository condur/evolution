(ns clojure-genetic-algorithm-example.clj.util
  (:require [clojure.math.numeric-tower :refer [abs]]))

(defn rand-int-in-range
  "Returns a random integer between min (inclusive) and max (exclusive)."
  [min max]
  {:pre [(integer? min)
         (integer? max)
         (<= min max)]}
  (+ min (rand-int (- max min))))

(defn bit-compare
  "Comparator. Returns zero when x is 'less than' or 'equal to' y
  and returns one when x is 'greater than' y"
  [^Long x ^Long y]
  (if (> x y) 1 0))

(def bit-compare-m (memoize bit-compare))

(defn strengths
  "Comparator. Return a sequence of 'strength' of each item compared to each other.
  'Strength' is in context of SPEA2 - multi-objective genetic algorithm
  http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.112.5073"
  [fitness-fns full-coll]
  (let [out (repeat fitness-fns 0)]
    (loop [coll full-coll
           strengths (transient [])]
      (if-let [f (first coll)]
        (recur (rest coll)
               (conj! strengths (loop [coll-local full-coll
                                       out-local out]
                                  (if-let [f-local (first coll-local)]
                                    (recur (rest coll-local)
                                           (map + out-local (map bit-compare-m f f-local)))
                                    (reduce + out-local)))))
        (persistent! strengths)))))

(defn ideal-interval-deviation
  "Calculate ideal interval deviation"
  [chrom-size indexes]
  (let [indexes-size (count indexes)]
    (if (= 1 indexes-size)
      (abs (- (quot chrom-size 2) 1 (first indexes)))
      (let [ideal-interval (quot chrom-size indexes-size)]
        (loop [coll indexes
               out 0]
          (let [f (first coll)
                s (second coll)]
            (if (nil? s)
              out
              (recur (rest coll)
                     (+ out (int (abs (- f s ideal-interval))))))))))))

(defn cross
  "Determine the interface quality cross"
  [interface-quality ^String commodity-1 ^String commodity-2]
  (((:matrix interface-quality)
    (.indexOf (:commodities interface-quality) commodity-1))
   (.indexOf (:commodities interface-quality) commodity-2)))

(def cross-m (memoize cross))


