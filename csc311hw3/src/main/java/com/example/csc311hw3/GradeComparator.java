package com.example.csc311hw3;

import java.io.Serializable;
import java.util.Comparator;

//Basically for the priority queue order
public class GradeComparator implements Comparator<Grade>, Serializable {
    public int compare(Grade g1, Grade g2) {
    if (g1.getScore() < g2.getScore())
        return 1;
    else if (g1.getScore() > g2.getScore())
        return -1;
    return 0;
    }
}

