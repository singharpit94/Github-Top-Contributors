package com.example.arpit.github_orgs;

import java.security.PublicKey;

/**
 * Created by arpit on 26/3/16.
 */
public class Name implements Comparable<Name> {
    private String name;

    private int forks;
    public Name(){}


    public Name(String name, int forks) {
        super();
        this.name = name;

        this.forks = forks;
    }

    public String getname() {
        return name;
    }
    public void setname(String name) {
        this.name = name;
    }

    public int getForks() {
        return forks;
    }
    public void setForks(int forks) {
        this.forks = forks;
    }

    public int compareTo(Name compareFruit) {

        int compareQuantity = ((Name) compareFruit).getForks();

        //ascending order
       // return this.forks - compareQuantity;

        //descending order
        return compareQuantity - this.forks;

    }
}
