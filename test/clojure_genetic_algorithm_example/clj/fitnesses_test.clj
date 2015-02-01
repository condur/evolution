(ns clojure-genetic-algorithm-example.clj.fitnesses-test
  (:require [clojure-genetic-algorithm-example.clj.fitnesses :as f]
            [midje.sweet :refer :all]
            [midje.repl :as mr]))

(let [sample-chrom [{:commodity "ABC5", :from "A5", :id 1, :to "B3"} {:commodity "ABC2", :from "A2", :id 2, :to "B1"}
                    {:commodity "ABC4", :from "A1", :id 3, :to "B2"} {:commodity "ABC1", :from "A3", :id 4, :to "B5"}
                    {:commodity "ABC3", :from "A2", :id 5, :to "B1"} {:commodity "ABC5", :from "A1", :id 6, :to "B1"}
                    {:commodity "ABC3", :from "A4", :id 7, :to "B2"} {:commodity "ABC4", :from "A5", :id 8, :to "B2"}
                    {:commodity "ABC1", :from "A3", :id 9, :to "B2"} {:commodity "ABC5", :from "A5", :id 10, :to "B1"}]

      sample-iq-matrix {:commodities ["ABC1" "ABC2" "ABC3" "ABC4" "ABC5"]
                        :matrix      [[0 3 8 6 9] [6 0 5 10 2] [5 1 0 2 4] [9 4 2 0 3] [4 5 5 7 0]]}

      settings {:iq-matrix sample-iq-matrix}]

  (facts "about 'fitness fns'"
         (f/interface-quality sample-chrom settings) => 61
         (f/rateability-from sample-chrom settings) => 12
         (f/rateability-to sample-chrom settings) => 11)
  )




