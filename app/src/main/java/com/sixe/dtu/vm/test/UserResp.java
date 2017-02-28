package com.sixe.dtu.vm.test;

import java.util.List;

/**
 * Created by liu on 17/2/27.
 */

public class UserResp {

    private String text;
    private List<String> images;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
