package com.barry.note.model;

import com.jph.takephoto.model.TImage;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/9.
 */

public class MyEvent {

    public static int videotype=0;
    public static int imgtype=1;
    int TYPE;

    public MyEvent() {
    }

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    ArrayList<TImage> images;

    public MyEvent(ArrayList<TImage> images) {
        this.images = images;
    }

    public ArrayList<TImage> getImages() {
        return images;
    }

    public void setImages(ArrayList<TImage> images) {
        this.images = images;
    }

    String videUrl;

    public String getVideUrl() {
        return videUrl;
    }

    public void setVideUrl(String videUrl) {
        this.videUrl = videUrl;
    }
}
