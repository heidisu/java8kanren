package org.morkland.java8kanren;

import org.morkland.java8kanren.lambdas.MapInfP;
import org.morkland.java8kanren.goals.Success;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Core {

    public static Stream<Thing> run(int n, Thing x, Goal... goals) {
        if (n > 0) {
            return mapInf(n, s -> s.walkStar(x).reify(), allAux(goals).evaluate(Substitution.empty()));
        }
        return Stream.empty();
    }

    public static Stream<Thing> mapInf(int n, MapInfP p, Stream<Substitution> a) {
        Iterator<Substitution> iterator = a.iterator();
        if (!iterator.hasNext()) {
            return Stream.empty();
        }
        Substitution first = iterator.next();
        if (!iterator.hasNext()) {
            return Stream.of(p.evaluate(first));
        } else {
            Iterable<Substitution> iterable = () -> iterator;
            Stream<Substitution> rest = StreamSupport.stream(iterable.spliterator(), false);
            Stream<Thing> mappedRest = n > 1 ? mapInf(n - 1, p, rest) : Stream.empty();
            return Stream.concat(Stream.of(p.evaluate(first)), mappedRest);
        }
    }

    public static Stream<Substitution> bind(Stream<Substitution> subs, Goal g) {
        Iterator<Substitution> iterator = subs.iterator();
        if (!iterator.hasNext()) {
            return Stream.empty();
        }
        Substitution first = iterator.next();
        if (!iterator.hasNext()) {
            return g.evaluate(first);
        } else {
            Iterable<Substitution> iterable = () -> iterator;
            Stream<Substitution> rest = StreamSupport.stream(iterable.spliterator(), false);
            return mplus(g.evaluate(first), bind(rest, g));
        }
    }

    public static Stream<Substitution> mplus(Stream<Substitution> subs1, Stream<Substitution> subs2) {
        return Stream.concat(subs1, subs2);
    }

    public static Goal allAux(Goal... g) {
        if (g.length == 0) {
            return new Success();
        } else if (g.length == 1) {
            return g[0];
        } else {
            final Goal[] rest = Arrays.copyOfRange(g, 1, g.length);
            return (s1) -> bind(g[0].evaluate(s1), (s2) -> allAux(rest).evaluate(s2));
        }
    }

    public static Goal ife(Goal g0, Goal g1, Goal g2) {
        return s -> mplus(allAux(g0, g1).evaluate(s), g2.evaluate(s));
    }

    public static Goal condAux(Goal elseGoal, Goal... ifGoals) {
        if (ifGoals.length == 0) {
            return allAux(elseGoal);
        } else {
            Goal[] lastGoals = new Goal[ifGoals.length - 2];
            for (int i = 2; i < ifGoals.length; i++) {
                lastGoals[i - 2] = ifGoals[i];
            }
            return ife(ifGoals[0], ifGoals[1], condAux(elseGoal, lastGoals));
        }
    }

    public static Goal conde(Goal elseGoal, Goal... ifGoals) {
        return condAux(elseGoal, ifGoals);
    }
}
