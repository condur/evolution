(ns clojure-genetic-algorithm-example.clj.core
  (:gen-class)
  (:import (crossover Order1))
  (:require [clojure-genetic-algorithm-example.clj.util :refer :all]
            [clojure-genetic-algorithm-example.clj.fitnesses :as f]
            [clojure.core.matrix :refer [identity-matrix get-row]]
            ;Async
            [clojure.core.async :refer [chan go go-loop >! <! <!! close!]]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(defn create-sample-chrom
  [elements distinct]
  (vec
    (for [index (range elements)]
      {:id        (inc index)
       :from      (str "A" (rand-int-in-range 1 6))
       :to        (str "B" (rand-int-in-range 1 6))
       :commodity (str "ABC" (rand-int-in-range 1 (inc distinct)))
       })))

(defn create-iq-matrix
  [distinct]
  {:commodities (vec (for [index (range distinct)]
                       (str "ABC" (inc index))))
   :matrix      (let [matrix (identity-matrix distinct)]
                  (vec (for [index (range distinct)]
                         (vec (map #(if (== 1 %) 0 (rand-int-in-range 1 10)) (get-row matrix index))))))
   })

(defn generate-population
  [sample-chrom size]
  (repeatedly size (partial shuffle sample-chrom)))

(defn crossover
  [population]
  (loop [remaining (shuffle population)
         seen (transient (into #{} population))]
    (let [f (first remaining)
          s (second remaining)]
      (if (nil? s)
        (persistent! seen)
        (recur (next remaining) (conj! seen (Order1/operate f s)))))))

(defn evaluate
  [chroms settings fitness-fns]
  (loop [coll chroms
         fitnesses (transient [])]
    (if-let [f (first coll)]
      (recur (rest coll)
             (conj! fitnesses (map #(% f settings) fitness-fns)))
      (let [strengths (strengths (count fitness-fns) (persistent! fitnesses))]
        {:best-strength (reduce min strengths) :chroms (map #(assoc {} :strength %1 :chrom %2) strengths chroms)}))))

(defn evolve
  ([sample-chrom settings & fitness-fns]
   (let [chan-evaluate (chan)
         chan-crossover (chan)
         chan-selection (chan)
         chan-progress (chan)
         chan-output (chan)
         population-size (:population-size settings)
         best-chroms-number (quot population-size 4)]

     ;Start
     (go (let [chans (doall
                       (for [_ (range (:sub-populations settings))]
                         (generate-population sample-chrom population-size)))]
           (doseq [c chans] (>! chan-evaluate c))))

     ;Evaluate
     (go-loop []
              (when-let [v (<! chan-evaluate)]
                (let [evaluated (evaluate v settings fitness-fns)]
                  (go (>! chan-selection evaluated))
                  (go (>! chan-progress evaluated)))
                (recur)))

     ;Selection
     (go (loop [prev-population nil]
           (when-let [v (<! chan-selection)]
             (let [current-population (doall (map :chrom (take best-chroms-number (sort-by :strength (:chroms v)))))]
               (if (nil? prev-population)
                 (recur current-population)
                 (do
                   (go (>! chan-crossover (into [] (concat current-population prev-population))))
                   (recur current-population)))))))

     ;Crossover
     (go-loop []
              (when-let [v (<! chan-crossover)]
                (go (>! chan-evaluate (crossover v)))
                (recur)))

     ;Progress
     (go (loop [evolutions 0]
           (when-let [v (<! chan-progress)]
             (if (< evolutions (:max-evolutions settings))
               (recur (inc evolutions))
               (do
                 (go (>! chan-output (map :chrom (filter #(= (:best-strength v) (:strength %)) (:chroms v)))))
                 (close! chan-evaluate)
                 (close! chan-crossover)
                 (close! chan-progress))
               ))))


     (<!! (go (<! chan-output))))))

(defn -main [& args]
  (let [elements (rand-int-in-range 8 20)
        distinct (rand-int-in-range 2 10)
        sample-chrom (create-sample-chrom elements distinct)
        sample-iq-matrix (create-iq-matrix distinct)
        settings {:population-size 60 :sub-populations 4 :max-evolutions 100 :iq-matrix sample-iq-matrix}]

    (println)
    (println (str "Randomly generated elements: " elements ", and distinct commodities: " distinct))

    ;print sample-chrom
    (println)
    (println "Sample Chromosome")0
    (loop [coll sample-chrom]
      (if-let [f (first coll)]
        (do (println f) (recur (rest coll)))))

    ;print sample-iq-matrix
    (println)
    (println "Sample Interface Quality Matrix")
    (println (str "Commodities: " (:commodities sample-iq-matrix)))
    (println "Matrix:")
    (loop [coll (:matrix sample-iq-matrix)]
      (if-let [f (first coll)]
        (do (println f) (recur (rest coll)))))

    ;print results
    (let [results (evolve sample-chrom settings f/interface-quality f/rateability-from f/rateability-to f/rateability-commodity)]
      (loop [coll results
             index 1]
        (if-let [f (first coll)]
          (do
            (println)
            (println (str "Result " index ":"))
            (loop [chrom f]
              (if-let [gene (first chrom)]
                (do (println gene) (recur (rest chrom)))))
            (recur (rest coll) (inc index))))))

    (println)
    ))
