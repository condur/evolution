(ns clojure-genetic-algorithm-example.clj.core-test
  (:require [clojure-genetic-algorithm-example.clj.util :refer [rand-int-in-range]]
            [clojure-genetic-algorithm-example.clj.fitnesses :as f]
            [clojure-genetic-algorithm-example.clj.core :refer :all]
            [midje.sweet :refer :all]
            [midje.repl :as mr]
            [criterium.core :as c]))


(facts "about 'create-sample-chrom'"
       (let [elements (rand-int-in-range 2 10)
             distinct (rand-int-in-range 2 10)]
         (create-sample-chrom elements distinct) =not=> nil))

(facts "about 'interface quality matrix'"
       (let [distinct (rand-int-in-range 2 10)]
         (create-iq-matrix distinct) => (contains {:commodities anything :matrix anything})
         (count (:commodities (create-iq-matrix distinct))) => distinct
         (count (:matrix (create-iq-matrix distinct))) => distinct
         (count (nth (:matrix (create-iq-matrix distinct)) 0)) => distinct))

(let [sample-chrom [{:commodity "ABC5", :from "A5", :id 1, :to "B3"} {:commodity "ABC2", :from "A2", :id 2, :to "B1"}
                    {:commodity "ABC4", :from "A1", :id 3, :to "B2"} {:commodity "ABC1", :from "A3", :id 4, :to "B5"}
                    {:commodity "ABC3", :from "A2", :id 5, :to "B1"} {:commodity "ABC5", :from "A1", :id 6, :to "B1"}
                    {:commodity "ABC3", :from "A4", :id 7, :to "B2"} {:commodity "ABC4", :from "A5", :id 8, :to "B2"}
                    {:commodity "ABC1", :from "A3", :id 9, :to "B2"} {:commodity "ABC5", :from "A5", :id 10, :to "B1"}]

      sample-iq-matrix {:commodities ["ABC1" "ABC2" "ABC3" "ABC4" "ABC5"]
                        :matrix      [[0 3 8 6 9] [6 0 5 10 2] [5 1 0 2 4] [9 4 2 0 3] [4 5 5 7 0]]}

      settings {:population-size 60 :sub-populations 4 :max-evolutions 10 :iq-matrix sample-iq-matrix}]

  (facts "about 'generate population'"
         (generate-population sample-chrom (rand-int-in-range 2 10)) =not=> nil)

  (facts "about 'evaluate'"
         (evaluate (generate-population sample-chrom (rand-int-in-range 2 10)) settings
                   [f/interface-quality f/rateability-from f/rateability-to]) =not=> nil)

  (facts "about 'multi-evaluation'"
         (evolve sample-chrom settings
                 f/interface-quality f/rateability-from f/rateability-to f/rateability-commodity) =not=> nil)
  )




