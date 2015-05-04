package org.morkland.java8kanren;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class SubstitutionTest {
   
    /**
     * Frame 14 - 17 of Chapter 9
     */
   @Test
   public void testWalk(){
       List<Pair<Thing>> list = new ArrayList<>();
       Thing z = new Thing();
       Thing a = new Thing ("a");
       Thing x = new Thing();
       Thing w = new Thing();
       Thing y = new Thing();
       Pair p1 = new Pair(z, a);
       Pair p2 = new Pair(x, w);
       Pair p3 = new Pair(y, z);
       list.add(p1);
       list.add(p2);
       list.add(p3);
       Substitution s = new Substitution(list);
       Assert.assertEquals(s.walk(z), a);
       Assert.assertEquals(s.walk(y), a);
       Assert.assertEquals(s.walk(x), w);
       Assert.assertEquals(s.walk(w), w);
   }
   
   /**
    * Frame 44 chapter 9
    */
   @Test
   public void testWalkStar(){
       Thing x = new Thing();
       Thing y = new Thing();
       Thing z = new Thing();
       Thing a = new Thing("a");
       Thing c = new Thing("c");
       Thing list = new Thing(new Pair(a, new Thing(new Pair(z, c))));
       List<Pair<Thing>> s = new ArrayList<>();
       s.add(new Pair(y, list));
       s.add(new Pair(x, y));
       s.add(new Pair(z, a));
       Substitution subs = new Substitution(s);
       
       // walked - results in (a z c)
       Thing walkThing = subs.walk(x);
       Pair<Thing> p1 = (Pair<Thing>) walkThing.getThing();
       Assert.assertEquals(p1.fst(), a);
       Pair<Thing> p2 = (Pair<Thing>) p1.snd().getThing();
       Assert.assertEquals(p2.fst(), z);
       Assert.assertEquals(p2.snd(), c);
       
       // walkStared - results in (a a c)
       Thing walkStarThing = subs.walkStar(x);
       p1 = (Pair<Thing>) walkStarThing.getThing();
       Assert.assertEquals(p1.fst(), a);
       p2 = (Pair<Thing>) p1.snd().getThing();
       Assert.assertEquals(p2.fst(), a);
       Assert.assertEquals(p2.snd(), c);
   }
   
   @Test
   public void testExt(){
       List<Pair<Thing>> liste = new ArrayList<>();
       Thing x = new Thing();
       Thing y = new Thing();
       Thing e = new Thing("e");
       liste.add(new Pair(x, e));
       Substitution s = new Substitution(liste);
       Assert.assertEquals(s.walk(y), y);
       Assert.assertEquals(s.ext(y, x).walk(y), e);
   }
   
   /**
    * Frame 56 chapter 9
    */
   @Test
   public void testReify(){
       Thing x = new Thing();
       Thing y = new Thing();
       Thing z = new Thing();
       Thing w = new Thing();
       Thing a = new Thing("a");
       Thing c = new Thing("c");
       List<Pair<Thing>> s = new ArrayList<>();
       Pair<Thing> pair = new Pair<>(z, new Thing(new Pair<>(w, new Thing(new Pair<>(c, w)))));
       s.add(new Pair<>(y, new Thing(pair)));
       s.add(new Pair<>(x, y));
       s.add(new Pair<>(z, a));
       Substitution subs = new Substitution(s);
       
       Thing res = subs.walkStar(x).reify();
       Pair<Thing> p1= (Pair<Thing>) res.getThing();
       Assert.assertEquals(p1.fst(), a);
       Pair<Thing> p2 = (Pair<Thing>) p1.snd().getThing();
       Assert.assertEquals(p2.fst().getThing(), "_0");
       Pair<Thing> p3 = (Pair<Thing>) p2.snd().getThing();
       Assert.assertEquals(p3.fst(), c);
       Assert.assertEquals(p3.snd().getThing(), "_0");
   }
   
   /**
    * Frame 53 chapter 9
    */
   @Test
   public void testReify2(){
       Thing w = new Thing();
       Thing x = new Thing();
       Thing y = new Thing();
       Pair<Thing> q = new Pair(w, new Thing(new Pair<>(x, y)));
       Thing r = new Thing(q);
       Thing res = Substitution.empty().reify(r).walkStar(r);
       Pair<Thing> p1 = (Pair<Thing>) res.getThing();
       Assert.assertEquals(p1.fst().getThing(), "_0");
       Pair<Thing> p2 = (Pair<Thing>) p1.snd().getThing();
       Assert.assertEquals(p2.fst().getThing(), "_1");
       Assert.assertEquals(p2.snd().getThing(), "_2");
   }
}
