package com.example.cgpacalculator;

public class Grade {
    private String gradeName;
    private double gradePoint;

    public Grade(String gradeName, double gradePoint){
        this.gradeName=gradeName;
        this.gradePoint=gradePoint;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public double getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(double gradePoint) {
        this.gradePoint = gradePoint;
    }
}
