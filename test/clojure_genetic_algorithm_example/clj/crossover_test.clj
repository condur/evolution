(ns clojure-genetic-algorithm-example.clj.crossover-test
  (:import (crossover Order1))
  (:require [midje.sweet :refer :all]))

(let [sample-chrom [{:commodity "ABC5", :from "A5", :id 1, :to "B3"} {:commodity "ABC2", :from "A2", :id 2, :to "B1"}
                    {:commodity "ABC4", :from "A1", :id 3, :to "B2"} {:commodity "ABC1", :from "A3", :id 4, :to "B5"}
                    {:commodity "ABC3", :from "A2", :id 5, :to "B1"} {:commodity "ABC5", :from "A1", :id 6, :to "B1"}
                    {:commodity "ABC3", :from "A4", :id 7, :to "B2"} {:commodity "ABC4", :from "A5", :id 8, :to "B2"}
                    {:commodity "ABC1", :from "A3", :id 9, :to "B2"} {:commodity "ABC5", :from "A5", :id 10, :to "B1"}]]

  (facts "about 'Java Crossover - Order1'"

         (fact "input duplicate chroms => exactly the same chrom"
               (Order1/operate sample-chrom sample-chrom) => sample-chrom)

         (fact "input 2 different chroms => a 3 different chrom"
               (let [different-sample-chrom (loop [shuffled-sample-chrom (shuffle sample-chrom)]
                                              (if (= shuffled-sample-chrom sample-chrom)
                                                (recur (shuffle sample-chrom))
                                                shuffled-sample-chrom))]
                 (Order1/operate sample-chrom different-sample-chrom) =not=> (or sample-chrom different-sample-chrom)))
         )
  )
