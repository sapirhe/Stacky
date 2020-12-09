package com.example.finalprojectstackoverflowapp.model;

public class SearchWord {
    private int id;
    private String searchWord;
    private String location;

    public SearchWord(int id, String searchWord, String location) {
        this.id = id;
        this.searchWord = searchWord;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
