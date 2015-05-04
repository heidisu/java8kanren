package org.morkland.java8kanren;

public class Pair<T> {
    private final T left, right;
    
    public Pair(T left, T right){
        this.left = left;
        this.right = right;
    }
    
    public T fst(){
        return left;
    }
    
    public T snd(){
        return right;
    }
}