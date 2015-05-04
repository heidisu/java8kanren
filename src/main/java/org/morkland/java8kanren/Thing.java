package org.morkland.java8kanren;
/**
 * The implementation of Var
 */
public class Thing {
    private Object thing;
    
    public Thing(Object thing){
        this.thing = thing;
    }
    
    public Thing(){
        this.thing = null;
    }
    
    public boolean isVar(){
        return !isPrimitive() && !isPair();
    }
    
    public boolean isPrimitive() {
        return thing != null
                && ((thing instanceof Integer)
                || (thing instanceof String)
                || (thing instanceof Double)
                || (thing instanceof Float)
                || (thing instanceof Long)
                || (thing instanceof Boolean));

    }
    
    public boolean isPair(){
        return thing != null && thing instanceof Pair;
    }
    
    public Object getThing(){
        return thing;
    }
    
    public void setThing(Object thing){
        this.thing = thing;
    }
    
    public boolean eq(Thing t){
        return thing!= null && thing.equals(t.thing);
    }
    
    public Thing reify(){
        return Substitution.empty().reify(this).walkStar(this);
    }
    
     public static Thing reify_name(int n){
        return new Thing("_"+ n);
    }
}
