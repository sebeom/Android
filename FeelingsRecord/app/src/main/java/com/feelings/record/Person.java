package com.feelings.record;

public class Person {

    String name;
    String mobile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Person(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }
}
