package com.example.arpit.github_orgs;

/**
 * Created by arpit on 26/3/16.
 */
public class Contributors {
    private String name;
    private int commits;
    public Contributors(){}


    public Contributors(String name, int commits) {
        super();
        this.name = name;

        this.commits = commits;
    }

    public String getname() {
        return name;
    }
    public void setname(String name) {
        this.name = name;
    }

    public int getCommits() {
        return commits;
    }
    public void setCommits(int commits) {
        this.commits = commits;
    }

}
