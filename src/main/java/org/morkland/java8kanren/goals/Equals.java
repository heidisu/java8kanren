package org.morkland.java8kanren.goals;

import java.util.stream.Stream;
import org.morkland.java8kanren.Goal;
import org.morkland.java8kanren.Substitution;
import org.morkland.java8kanren.Thing;

/**
 * 
 * @author hmo
 */
public class Equals implements Goal{
    Thing x;
    Thing y;
    
    public Equals(Thing x, Thing y){
        this.x = x;
        this.y = y;
    }

    @Override
    public Stream<Substitution> evaluate(Substitution s) {
        Substitution subs= s.unify(x, y);
        if(subs != null){
            return new Success().evaluate(subs);
        }
        return new Unsuccessful().evaluate(subs);
    }  
}
