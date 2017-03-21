package com.nitin.pole.repository.pojo;

/**
 * Created by Nitin on 3/18/2017.
 */

public class Option {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private String id;
    private String value;
    private int count;

    @Override
    public String toString() {
        return value;
    }
}