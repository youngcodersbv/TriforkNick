package com.Nick.DishProject.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Amount {
    private List<Integer> ing = new ArrayList<>();
    private List<String> am = new ArrayList<>();

    public List<Integer> getIng() {
        return ing;
    }

    public void setIng(List<Integer> ing) {
        this.ing = ing;
    }

    public List<String> getAm() {
        return am;
    }

    public void setAm(List<String> am) {
        this.am = am;
    }
}
