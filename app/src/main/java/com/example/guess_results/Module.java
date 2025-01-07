package com.example.guess_results;

public class Module {
    private int id;
    private String name;
    private float coef;
    private float eval;
    private float evalPerc;
    private float exam;
    private float examPerc;

    // Constructeur
    public Module(int id, String name, float coef, float eval, float evalPerc, float exam, float examPerc) {
        this.id = id;
        this.name = name;
        this.coef = coef;
        this.eval = eval;
        this.evalPerc = evalPerc;
        this.exam = exam;
        this.examPerc = examPerc;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCoef() {
        return coef;
    }

    public void setCoef(float coef) {
        this.coef = coef;
    }

    public float getEval() {
        return eval;
    }

    public void setEval(float eval) {
        this.eval = eval;
    }

    public float getEvalPerc() {
        return evalPerc;
    }

    public void setEvalPerc(float evalPerc) {
        this.evalPerc = evalPerc;
    }

    public float getExam() {
        return exam;
    }

    public void setExam(float exam) {
        this.exam = exam;
    }

    public float getExamPerc() {
        return examPerc;
    }

    public void setExamPerc(float examPerc) {
        this.examPerc = examPerc;
    }
}
