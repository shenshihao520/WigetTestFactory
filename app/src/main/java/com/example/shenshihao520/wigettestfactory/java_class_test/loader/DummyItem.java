package com.example.shenshihao520.wigettestfactory.java_class_test.loader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenshihao520 on 2017/8/22.
 */

public class DummyItem {

    private List<People> peopleList = new ArrayList<>();
    interface Change{
        void onChange();
    }
    public void save (People people,Change change){
        peopleList.add(people);
        if(change!=null){
            change.onChange();
        }
    }
    public List<People> getPeopleList (){
        return peopleList;
    }


}

