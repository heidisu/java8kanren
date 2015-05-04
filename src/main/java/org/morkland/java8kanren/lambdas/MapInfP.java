package org.morkland.java8kanren.lambdas;

import org.morkland.java8kanren.Substitution;
import org.morkland.java8kanren.Thing;

@FunctionalInterface
public interface MapInfP {
    Thing evaluate(Substitution s);
}
