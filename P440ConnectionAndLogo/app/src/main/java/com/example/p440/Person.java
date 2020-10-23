package com.example.p440;

import java.util.Date;

public class Person {
    String name;
    Date date;
    String tel;

    public Person() {
    }

    public Person(String name, Date date, String tel) {
        this.name = name;
        this.date = date;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
