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

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return this.toString() == other.toString();
    }
} 
