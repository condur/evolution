(ns clojure-genetic-algorithm-example.clj.fitnesses
  (:require [clojure-genetic-algorithm-example.clj.util :refer [cross ideal-interval-deviation]]))

(defn interface-quality
  "Fitness function based on Interface Quality Matrix"
  [chrom settings]
  (let [interface-quality (:iq-matrix settings)]
    (loop [coll chrom out 0]
      (let [f (first coll) s (second coll)]
        (if (nil? s)
          out
          (recur (rest coll) (+ out (cross interface-quality (:commodity f) (:commodity s)))))))))

(defn- rateability
  "Base fitness function for calculating rateability"
  [chrom settings reteability-by]
  (loop [coll chrom
         index 1
         out (transient {})]
    (if-let [reteability-value (reteability-by (first coll))]
      (recur (rest coll)
             (inc index)
             (assoc! out reteability-value (conj (get out reteability-value) index)))
      (let [chrom-size (count chrom)]
        (reduce + (map #(ideal-interval-deviation chrom-size (second %)) (persistent! out)))))
    ))

(defn rateability-commodity
  "Fitness function based on Commodity Rateability"
  [chrom settings]
  (rateability chrom settings :commodity))

(defn rateability-from
  "Fitness function based on From Rateability"
  [chrom settings]
  (rateability chrom settings :from))

(defn rateability-to
  "Fitness function based on To Rateability"
  [chrom settings]
  (rateability chrom settings :to))