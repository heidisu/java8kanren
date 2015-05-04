package org.morkland.java8kanren;

import java.util.stream.Stream;

@FunctionalInterface
public interface Goal {
    Stream<Substitution> evaluate(Substitution s);
}
