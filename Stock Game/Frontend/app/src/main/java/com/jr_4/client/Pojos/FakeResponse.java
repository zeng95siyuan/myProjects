package com.jr_4.client.Pojos;

import com.jr_4.client.Pojos.Portfolio;

import java.util.ArrayList;

public class FakeResponse {

    public FakeResponse(){

    }

    public ArrayList<Portfolio> getResponse(){
        ArrayList<Portfolio> list = new ArrayList<>();
        Portfolio test = new Portfolio("ZSY", 3, 99.99, 100);
        list.add(test);
        return list;
    }
}
