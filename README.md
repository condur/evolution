A Clojure example of Multi-Objective Genetic Algorithm

To run the application use Leiningen get dependencies and call "lein run".

The input data is randomly generated.

Sample result:

Randomly generated elements: 9, and distinct commodities: 4

Sample Chromosome
{:id 1, :from A4, :to B3, :commodity ABC2}
{:id 2, :from A5, :to B5, :commodity ABC3}
{:id 3, :from A3, :to B2, :commodity ABC4}
{:id 4, :from A4, :to B3, :commodity ABC4}
{:id 5, :from A2, :to B5, :commodity ABC1}
{:id 6, :from A4, :to B1, :commodity ABC4}
{:id 7, :from A2, :to B5, :commodity ABC4}
{:id 8, :from A2, :to B5, :commodity ABC1}
{:id 9, :from A3, :to B5, :commodity ABC4}

Sample Interface Quality Matrix
Commodities: ["ABC1" "ABC2" "ABC3" "ABC4"]
Matrix:
[0 4 5 3]
[4 0 6 3]
[9 8 0 8]
[5 1 6 0]

Result 1:
{:id 1, :from A4, :to B3, :commodity ABC2}
{:id 2, :from A5, :to B5, :commodity ABC3}
{:id 3, :from A3, :to B2, :commodity ABC4}
{:id 6, :from A4, :to B1, :commodity ABC4}
{:id 7, :from A2, :to B5, :commodity ABC4}
{:id 4, :from A4, :to B3, :commodity ABC4}
{:id 9, :from A3, :to B5, :commodity ABC4}
{:id 5, :from A2, :to B5, :commodity ABC1}
{:id 8, :from A2, :to B5, :commodity ABC1}

Result 2:
{:id 1, :from A4, :to B3, :commodity ABC2}
{:id 2, :from A5, :to B5, :commodity ABC3}
{:id 3, :from A3, :to B2, :commodity ABC4}
{:id 6, :from A4, :to B1, :commodity ABC4}
{:id 7, :from A2, :to B5, :commodity ABC4}
{:id 4, :from A4, :to B3, :commodity ABC4}
{:id 9, :from A3, :to B5, :commodity ABC4}
{:id 8, :from A2, :to B5, :commodity ABC1}
{:id 5, :from A2, :to B5, :commodity ABC1}

