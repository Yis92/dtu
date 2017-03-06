package com.sixe.dtu.http.entity.index;

import java.util.List;

/**
 * 菜单栏目
 * Created by liu on 17/3/6.
 */

public class IndexMenu {

    private List<String> name;//菜单栏目名
    private List<String> id;//菜单栏目id,有可能是单位编号,也有可能是dtu编号

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }
}
