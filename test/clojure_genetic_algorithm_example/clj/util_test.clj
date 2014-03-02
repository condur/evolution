(ns clojure-genetic-algorithm-example.clj.util-test
  (:require [clojure-genetic-algorithm-example.clj.util :refer :all]
            [midje.sweet :refer :all]
            [midje.repl :as mr :exclude [record?]]
            [criterium.core :as c]))

(facts "about 'bit-compare'"
       (fact "x < y => 0"
             (bit-compare 1 2) => 0)
       (fact "x = y => 1"
             (bit-compare 1 1) => 0)
       (fact "x > y => 1"
             (bit-compare 2 1) => 1))

(facts "about 'strengths'"
       (fact "number of fitness functions + fitness calculation results => the strength of each collection compared to the others"
             (strengths 4 [[1 2] [3 4] [5 6] [7 8]]) => [0 2 4 6]))

(let [iq-matrix {:commodities ["ABC1" "ABC2" "ABC3" "ABC4" "ABC5"]
                 :matrix      [[0 3 8 6 9] [6 0 5 10 2] [5 1 0 2 4] [9 4 2 0 3] [4 5 5 7 0]]}]

  (facts "about 'cross fns'"
         (fact "cross of any 2 commodities => matrix value"
               (cross iq-matrix "ABC1" "ABC1") => 0))
  )
