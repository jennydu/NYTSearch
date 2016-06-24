package com.codepath.nytimessearch;

import java.io.Serializable;

/**
 * Created by jenniferdu on 6/22/16.
 */
public class Filters implements Serializable {
    public String news_desk;
    public String sort;
    public String begin_date;

    public String getNews_desk() {
        return news_desk;
    }

    public String getSort() {
        return sort;
    }

    public String getBegin_date() {
        return begin_date;
    }



    public void setNews_desk(String news_desk) {
        this.news_desk = news_desk;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }



}
