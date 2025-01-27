package com.example.guess_results;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Module implements Parcelable {
    private int id;
    private String name;
    private float coef;
    private float eval;
    private float evalPerc;
    private float exam;
    private float examPerc;

    // Constructor with all fields
    public Module(int id, String name, float coef, float eval, float evalPerc, float exam, float examPerc) {
        this.id = id;
        this.name = name;
        this.coef = coef;
        this.eval = eval;
        this.evalPerc = evalPerc;
        this.exam = exam;
        this.examPerc = examPerc;
    }

    // Constructor without ID (useful for inserting new modules)
    public Module(String name, float coef, float eval, float evalPerc, float exam, float examPerc) {
        this.name = name;
        this.coef = coef;
        this.eval = eval;
        this.evalPerc = evalPerc;
        this.exam = exam;
        this.examPerc = examPerc;
    }

    protected Module(Parcel in) {
        id = in.readInt();
        name = in.readString();
        coef = in.readFloat();
        eval = in.readFloat();
        evalPerc = in.readFloat();
        exam = in.readFloat();
        examPerc = in.readFloat();
    }

    public static final Creator<Module> CREATOR = new Creator<Module>() {
        @Override
        public Module createFromParcel(Parcel in) {
            return new Module(in);
        }

        @Override
        public Module[] newArray(int size) {
            return new Module[size];
        }
    };

    // Getters and Setters
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

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coef=" + coef +
                ", eval=" + eval +
                ", evalPerc=" + evalPerc +
                ", exam=" + exam +
                ", examPerc=" + examPerc +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeFloat(coef);
        dest.writeFloat(eval);
        dest.writeFloat(evalPerc);
        dest.writeFloat(exam);
        dest.writeFloat(examPerc);
    }
}
