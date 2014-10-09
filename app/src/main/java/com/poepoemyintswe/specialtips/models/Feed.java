package com.poepoemyintswe.specialtips.models;

/**
 * Created by poepoe on 10/7/14.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Feed implements Serializable{

    public Integer id;
    public String title;
    public String permalink;
    public String content;
    public String excerpt;
    public String date;
    public String author;
    public List<String> categories = new ArrayList<String>();
    public List<Object> tags = new ArrayList<Object>();

}