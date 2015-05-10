package org.morkland.java8kanren.goals;

import java.util.stream.Stream;
import org.morkland.java8kanren.Goal;
import org.morkland.java8kanren.Substitution;

public class Success implements Goal{

    @Override
    public Stream<Substitution> evaluate(Substitution s) {
        return Stream.of(s);
    }
    
}
