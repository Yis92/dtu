package com.sixe.dtu.http.entity.dtu;

import com.sixe.dtu.http.util.CommonResponse;

import java.util.List;

/**
 * Created by liu on 17/3/3.
 */

public class DtuGroupShowResp extends CommonResponse<DtuGroupShowResp> {

    private List<GroupData> groupdata;
    private List<Group> group;

    public List<GroupData> getGroupdata() {
        return groupdata;
    }

    public void setGroupdata(List<GroupData> groupdata) {
        this.groupdata = groupdata;
    }

    public List<Group> getGroup() {
        return group;
    }

    public void setGroup(List<Group> group) {
        this.group = group;
    }

    public class Group {

        private String name;
        private int group_id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }
    }


    public class GroupData {
        private String name;
        private String value;
        private String id;
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
