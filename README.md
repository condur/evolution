<b>A Clojure example of Multi-Objective Genetic Algorithm</b>

To run the application use Leiningen get dependencies and call "lein run".

The input data is randomly generated.

Sample result:

Randomly generated elements: 9, and distinct commodities: 4

Sample Chromosome

{:id 1, :from A4, :to B3, :commodity ABC2}<br>
{:id 2, :from A5, :to B5, :commodity ABC3}<br>
{:id 3, :from A3, :to B2, :commodity ABC4}<br>
{:id 4, :from A4, :to B3, :commodity ABC4}<br>
{:id 5, :from A2, :to B5, :commodity ABC1}<br>
{:id 6, :from A4, :to B1, :commodity ABC4}<br>
{:id 7, :from A2, :to B5, :commodity ABC4}<br>
{:id 8, :from A2, :to B5, :commodity ABC1}<br>
{:id 9, :from A3, :to B5, :commodity ABC4}<br>

Sample Interface Quality Matrix

Commodities: ["ABC1" "ABC2" "ABC3" "ABC4"]

Matrix:<br>
[0 4 5 3]<br>
[4 0 6 3]<br>
[9 8 0 8]<br>
[5 1 6 0]<br>

Result 1:<br>
{:id 1, :from A4, :to B3, :commodity ABC2}<br>
{:id 2, :from A5, :to B5, :commodity ABC3}<br>
{:id 3, :from A3, :to B2, :commodity ABC4}<br>
{:id 6, :from A4, :to B1, :commodity ABC4}<br>
{:id 7, :from A2, :to B5, :commodity ABC4}<br>
{:id 4, :from A4, :to B3, :commodity ABC4}<br>
{:id 9, :from A3, :to B5, :commodity ABC4}<br>
{:id 5, :from A2, :to B5, :commodity ABC1}<br>
{:id 8, :from A2, :to B5, :commodity ABC1}<br>

Result 2:<br>
{:id 1, :from A4, :to B3, :commodity ABC2}<br>
{:id 2, :from A5, :to B5, :commodity ABC3}<br>
{:id 3, :from A3, :to B2, :commodity ABC4}<br>
{:id 6, :from A4, :to B1, :commodity ABC4}<br>
{:id 7, :from A2, :to B5, :commodity ABC4}<br>
{:id 4, :from A4, :to B3, :commodity ABC4}<br>
{:id 9, :from A3, :to B5, :commodity ABC4}<br>
{:id 8, :from A2, :to B5, :commodity ABC1}<br>
{:id 5, :from A2, :to B5, :commodity ABC1}<br>

