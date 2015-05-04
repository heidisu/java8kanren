package org.morkland.java8kanren;

import java.util.ArrayList;
import java.util.List;

public class Substitution {

    List<Pair<Thing>> subs = new ArrayList<>();

    public Substitution(List<Pair<Thing>> subs) {
        this.subs = subs;
    }

    public Pair assq(Thing v) {
        for (Pair pair : subs) {
            if (pair.fst().equals(v)) {
                return pair;
            }
        }
        return null;
    }

    public Thing walk(Thing v) {
        if (v.isVar()) {
            Pair<Thing> a = assq(v);
            if (a != null) {
                return walk(a.snd());
            }
            return v;
        } 
        return v;
    }

    public Thing walkStar(Thing v) {
        v = walk(v);
        if (v.isPair()) {
            Pair<Thing> p = (Pair<Thing>) v.getThing();
            return new Thing(new Pair(walkStar(p.fst()), walkStar(p.snd())));
        }
        return v;
    }

    public Substitution reify(Thing v) {
        v = walk(v);
        if (v.isVar()) {
            return ext(v, Thing.reify_name(subs.size()));
        } else if (v.isPair()) {
            Pair<Thing> p = (Pair<Thing>) v.getThing();
            return reify(p.fst()).reify(p.snd());
        }
        return this;
    }

    public Substitution ext(Thing x, Thing y) {
        List<Pair<Thing>> list = new ArrayList<>();
        list.add(new Pair(x, y));
        list.addAll(subs);
        return new Substitution(list);
    }

    public Substitution unify(Thing v, Thing w) {
        v.setThing(walk(v).getThing());
        w.setThing(walk(w).getThing());
        if (v.eq(w)) {
            return this;
        } else if (v.isVar()) {
            return ext(v, w);
        } else if (w.isVar()) {
            return ext(w, v);
        } else if (v.isPair() && w.isPair()) {
            Pair<Thing> p = (Pair<Thing>) v.getThing();
            Pair<Thing> q = (Pair<Thing>) w.getThing();
            Substitution s = unify(p.fst(), q.fst());
            if (s != null) {
                return s.unify(p.snd(), q.snd());
            } else {
                return null;
            }
        } else if (v.equals(w)) {
            return this;
        } else {
            return null;
        }
    }

    public static Substitution empty() {
        return new Substitution(new ArrayList<>());
    }
}
