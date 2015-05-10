package org.morkland.java8kanren.goals;

import java.util.stream.Stream;
import org.morkland.java8kanren.Goal;
import org.morkland.java8kanren.Substitution;

public class Unsuccessful implements Goal{

    @Override
    public Stream<Substitution> evaluate(Substitution s) {
        return Stream.empty();
    }
    
}
