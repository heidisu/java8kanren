# java8kanren
After reading "The Reasoned Schemer" I had to try to write a version of minikanren myself. 
The implementation is basically the last section "Connecting the wires", translated to java. It is not complete or perfect, but you can run (simple) goals including conde. The test classes show examples from chapter 1 and 9 in the book.


### Todos
* Replace the Scheme-like list with "Pair"s with a standard list.
* Refactor with lambdas so similar if-else blocks can be replaced with "CaseInfinity".
* Make "conde" accept if-then-expression with more than one goal in the "then"-part.
* Do something with the "Thing". It is probably not a good thing.
