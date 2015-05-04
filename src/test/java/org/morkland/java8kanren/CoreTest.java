package org.morkland.java8kanren;

import org.morkland.java8kanren.goals.Equals;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.morkland.java8kanren.goals.Success;
import org.morkland.java8kanren.goals.Unsuccessful;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test data from chapter 1 of Reasoned Schemer, frame number refers to frame in
 * that chapter
 */
@Test
public class CoreTest {

    private static final Thing t = new Thing(true);
    private static final Thing f = new Thing(false);

    @Test
    public void frame10() {
        Thing x = new Thing();
        Goal g = new Unsuccessful();
        testRun(x, 1, new Goal[]{g}, new Object[]{});
    }

    @Test
    public void frame11() {
        Thing x = new Thing();
        Goal g = new Equals(x, t);
        testRun(x, 1, new Goal[]{g}, new Object[]{true});
    }

    @Test
    public void frame12() {
        Thing x = new Thing();
        Goal g1 = new Unsuccessful();
        Goal g2 = new Equals(x, t);
        testRun(x, 1, new Goal[]{g1, g2}, new Object[]{});
    }

    @Test
    public void frame13() {
        Thing x = new Thing();
        Goal g1 = new Success();
        Goal g2 = new Equals(x, t);
        testRun(x, 1, new Goal[]{g1, g2}, new Object[]{true});
    }

    @Test
    public void frame26() {
        Thing x = new Thing();
        Thing y = new Thing();
        Goal g1 = new Equals(y, t);
        Goal g2 = new Equals(t, x);
        testRun(x, 1, new Goal[]{g1, g2}, new Object[]{true});
    }

    @Test
    public void frame31() {
        Thing x = new Thing();
        Thing y = new Thing();
        Thing z = new Thing();
        Goal g = new Equals(new Thing(new Pair(y, z)), x);
        testRun(x, 1, new Goal[]{g}, new Object[]{new Object[]{"_0", "_1"}});
    }

    @Test
    public void frame32() {
        Thing x = new Thing();
        Thing y = new Thing();
        Thing z = new Thing();
        Goal g = new Equals(new Thing(new Pair(y, new Thing(new Pair(z, y)))), x);
        testRun(x, 1, new Goal[]{g}, new Object[]{new Object[]{"_0", new Object[]{"_1", "_0"}}});
    }

    @Test
    public void frame47() {
        Thing x = new Thing();
        Thing olive = new Thing("olive");
        Thing oil = new Thing("oil");
        Goal g = Core.conde(new Unsuccessful(),
                new Equals(olive, x), new Success(),
                new Equals(oil, x), new Success());
        testRun(x, 2, new Goal[]{g}, new Object[]{"olive", "oil"});
    }

    @Test
    public void frame48() {
        Thing x = new Thing();
        Thing olive = new Thing("olive");
        Thing oil = new Thing("oil");
        Goal g = Core.conde(new Unsuccessful(),
                new Equals(olive, x), new Success(),
                new Equals(oil, x), new Success());
        testRun(x, 1, new Goal[]{g}, new Object[]{"olive"});
    }

    @Test
    public void frame54() {
        Thing x = new Thing();
        Thing y = new Thing();
        Thing r = new Thing();
        Thing split = new Thing("split");
        Thing navy = new Thing("navy");
        Thing pea = new Thing("pea");
        Thing bean = new Thing("bean");
        Goal g1 = Core.conde(new Unsuccessful(),
                new Equals(split, x), new Equals(pea, y),
                new Equals(navy, x), new Equals(bean, y));
        Goal g2 =  new Equals(new Thing(new Pair<>(x, y)), r);
        testRun(r, 3, new Goal[]{g1, g2}, new Object[]{new Object[]{"split", "pea"}, new Object[]{"navy", "bean"}});
    }

    private void testRun(Thing runThing, int number, Goal[] goals, Object[] expected) {
        Stream<Thing> res = Core.run(number, runThing, goals);
        List<Thing> list = res.map(t -> t).collect(Collectors.toList());
        if (expected.length == 0) {
            Assert.assertTrue(list.isEmpty());
        }
        for (int i = 0; i < expected.length; i++) {
            checkAssertion(list.get(i), expected[i]);
        }
    }

    private void checkAssertion(Thing actual, Object expected) {
        Object actualThing = actual.getThing();
        if (actualThing instanceof Pair) {
            Thing first = (Thing) ((Pair) actualThing).fst();
            Thing second = (Thing) ((Pair) actualThing).snd();
            checkAssertion(first, ((Object[]) expected)[0]);
            checkAssertion(second, ((Object[]) expected)[1]);
        } else {
            Assert.assertEquals(actualThing, expected);
        }
    }
}
