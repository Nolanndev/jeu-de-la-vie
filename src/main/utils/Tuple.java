package main.utils;

public class Tuple {
    
    private int firstValue;
    private int secondValue;
    
    public Tuple(int a, int b) {
        this.firstValue = a;
        this.secondValue = b;
    }

    public int getValue1() {
        return this.firstValue;
    }
    public int getValue2() {
        return this.secondValue;
    }

    public String toString() {
        return "(" + this.firstValue + "," + this.secondValue + ")";
    }
} 
