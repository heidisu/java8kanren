# java8kanren
After reading "The Reasoned Schemer" I had to try to write a version of minikanren myself. 
The implementation is basically the last section "Connecting the wires", translated to java. It is not complete or perfect, but you can run (simple) goals including conde. The test classes show examples from chapter 1 and 9 in the book.

## What's going on?
The object *Thing* is holding an inner object, and if that inner object is null the Thing is a fresh variable. 
```java
Thing x = new Thing(); // fresh variable
Thing x = new Thing("apple"); 
```
A *Substitution* is a list of pair of things. The left hand side of each pair is a variable, and it is associated with the value on the right hand side.
A substitution can be *walk*ed for a variable by finding the pair with that variable on the left hand side, and if the value on the right also is a variable then see if that variable is on the left of another pair and so on, util a non-variable is reached or the value on the right does not exist in on the left of another pair. If a substitution contains the following pairs
```
(z, "plum") (x, y) (y, z)
```
then the substitution walked with *x* would give *plum*.

A *Goal* is a lambda expression which takes a substitution and returns a stream of substitutions.  

### Todos
* Replace the Scheme-like list with "Pair"s with a standard list.
* Refactor with lambdas so similar if-else blocks can be replaced with "CaseInfinity".
* Make "conde" accept if-then-expression with more than one goal in the "then"-part.
* Do something with the "Thing". It is probably not a good thing.
