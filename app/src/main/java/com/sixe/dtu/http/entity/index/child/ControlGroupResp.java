package com.sixe.dtu.http.entity.index.child;

import com.google.gson.annotations.SerializedName;
import com.sixe.dtu.http.util.CommonResponse;

import java.util.List;

/**
 * 控制器分组返回
 * Created by sunny on 2017/4/17.
 */

public class ControlGroupResp extends CommonResponse<ControlGroupResp> {
    /**
     * group : [{"name":"控制器1","group_id":1,"node_addr":"5"}]
     * tskinfo : {"name":"控制器1","node_addr":"5","tsk_num":"2","tsk":[{"tsk_channel":1,"tsk_describ":"灌溉1","tsk_status:":"1","tsk_type":"1","tsk_dt":"2016-01-02","tsk_tm":"10:10:10","tsk_second":"10000","tsk_surplus":0},{"tsk_channel":2,"tsk_describ":"灌溉2","tsk_status:":"1","tsk_type":"1","tsk_dt":"2016-01-02","tsk_tm":"10:10:10","tsk_second":"10000","tsk_surplus":0}]}
     */

    private TskinfoBean tskinfo;
    private List<GroupBean> group;

    public TskinfoBean getTskinfo() {
        return tskinfo;
    }

    public void setTskinfo(TskinfoBean tskinfo) {
        this.tskinfo = tskinfo;
    }

    public List<GroupBean> getGroup() {
        return group;
    }

    public void setGroup(List<GroupBean> group) {
        this.group = group;
    }

    public static class TskinfoBean {
        /**
         * name : 控制器1
         * node_addr : 5
         * tsk_num : 2
         * tsk : [{"tsk_channel":1,"tsk_describ":"灌溉1","tsk_status:":"1","tsk_type":"1","tsk_dt":"2016-01-02","tsk_tm":"10:10:10","tsk_second":"10000","tsk_surplus":0},{"tsk_channel":2,"tsk_describ":"灌溉2","tsk_status:":"1","tsk_type":"1","tsk_dt":"2016-01-02","tsk_tm":"10:10:10","tsk_second":"10000","tsk_surplus":0}]
         */

        private String name;
        private String node_addr;
        private String tsk_num;
        private List<TskBean> tsk;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNode_addr() {
            return node_addr;
        }

        public void setNode_addr(String node_addr) {
            this.node_addr = node_addr;
        }

        public String getTsk_num() {
            return tsk_num;
        }

        public void setTsk_num(String tsk_num) {
            this.tsk_num = tsk_num;
        }

        public List<TskBean> getTsk() {
            return tsk;
        }

        public void setTsk(List<TskBean> tsk) {
            this.tsk = tsk;
        }

        public static class TskBean {
            /**
             * tsk_channel : 1
             * tsk_describ : 灌溉1
             * tsk_status: : 1
             * tsk_type : 1
             * tsk_dt : 2016-01-02
             * tsk_tm : 10:10:10
             * tsk_second : 10000
             * tsk_surplus : 0
             */

            private String tsk_channel;
            private String tsk_describ;
            @SerializedName("tsk_status:")
            private String _$Tsk_status252; // FIXME check this code
            private String tsk_type;
            private String tsk_dt;
            private String tsk_tm;
            private String tsk_second;
            private String tsk_surplus;


            public String getTsk_describ() {
                return tsk_describ;
            }

            public void setTsk_describ(String tsk_describ) {
                this.tsk_describ = tsk_describ;
            }

            public String get_$Tsk_status252() {
                return _$Tsk_status252;
            }

            public void set_$Tsk_status252(String _$Tsk_status252) {
                this._$Tsk_status252 = _$Tsk_status252;
            }

            public String getTsk_type() {
                return tsk_type;
            }

            public void setTsk_type(String tsk_type) {
                this.tsk_type = tsk_type;
            }

            public String getTsk_dt() {
                return tsk_dt;
            }

            public void setTsk_dt(String tsk_dt) {
                this.tsk_dt = tsk_dt;
            }

            public String getTsk_tm() {
                return tsk_tm;
            }

            public void setTsk_tm(String tsk_tm) {
                this.tsk_tm = tsk_tm;
            }

            public String getTsk_second() {
                return tsk_second;
            }

            public void setTsk_second(String tsk_second) {
                this.tsk_second = tsk_second;
            }

            public String getTsk_channel() {
                return tsk_channel;
            }

            public void setTsk_channel(String tsk_channel) {
                this.tsk_channel = tsk_channel;
            }

            public String getTsk_surplus() {
                return tsk_surplus;
            }

            public void setTsk_surplus(String tsk_surplus) {
                this.tsk_surplus = tsk_surplus;
            }
        }
    }

    public static class GroupBean {
        /**
         * name : 控制器1
         * group_id : 1
         * node_addr : 5
         */

        private String name;
        private int group_id;
        private String node_addr;

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

        public String getNode_addr() {
            return node_addr;
        }

        public void setNode_addr(String node_addr) {
            this.node_addr = node_addr;
        }
    }


//
//    /**
//     * group : [{"name":"控制器1","group_id":1,"node_addr":"5"}]
//     * tskinfo : {"name":"控制器1","node_addr":"5","state":"1","tsk_num":"2","tsk":[{"tsk_channel":1,"tsk_describ":"灌溉1","tsk_type":"1","tsk_dt":"2016-01-02","tsk_tm":"10:10:10","tsk_second":"10000","tsk_surplus":0},{"tsk_channel":2,"tsk_describ":"灌溉2","tsk_type":"1","tsk_dt":"2016-01-02","tsk_tm":"10:10:10","tsk_second":"10000","tsk_surplus":0}]}
//     */
//
//    private TskinfoBean tskinfo;
//    private List<GroupBean> group;
//
//    public TskinfoBean getTskinfo() {
//        return tskinfo;
//    }
//
//    public void setTskinfo(TskinfoBean tskinfo) {
//        this.tskinfo = tskinfo;
//    }
//
//    public List<GroupBean> getGroup() {
//        return group;
//    }
//
//    public void setGroup(List<GroupBean> group) {
//        this.group = group;
//    }
//
//    public static class TskinfoBean {
//        /**
//         * name : 控制器1
//         * node_addr : 5
//         * state : 1
//         * tsk_num : 2
//         * tsk : [{"tsk_channel":1,"tsk_describ":"灌溉1","tsk_type":"1","tsk_dt":"2016-01-02","tsk_tm":"10:10:10","tsk_second":"10000","tsk_surplus":0},{"tsk_channel":2,"tsk_describ":"灌溉2","tsk_type":"1","tsk_dt":"2016-01-02","tsk_tm":"10:10:10","tsk_second":"10000","tsk_surplus":0}]
//         */
//
//        private String name;
//        private String node_addr;
//        private String tsk_num;
//        private List<TskBean> tsk;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getNode_addr() {
//            return node_addr;
//        }
//
//        public void setNode_addr(String node_addr) {
//            this.node_addr = node_addr;
//        }
//
//        public String getTsk_num() {
//            return tsk_num;
//        }
//
//        public void setTsk_num(String tsk_num) {
//            this.tsk_num = tsk_num;
//        }
//
//        public List<TskBean> getTsk() {
//            return tsk;
//        }
//
//        public void setTsk(List<TskBean> tsk) {
//            this.tsk = tsk;
//        }
//
//        public static class TskBean {
//            /**
//             * tsk_channel : 1
//             * tsk_describ : 灌溉1
//             * tsk_type : 1
//             * tsk_dt : 2016-01-02
//             * tsk_tm : 10:10:10
//             * tsk_second : 10000
//             * tsk_surplus : 0
//             */
//
//            private String tsk_channel;
//            private String tsk_describ;
//            private String tsk_type;
//            private String tsk_dt;
//            private String tsk_tm;
//            private String tsk_second;
//            private String tsk_surplus;
//            private String tsk_status;
//
//            public String getTsk_status() {
//                return tsk_status;
//            }
//
//            public void setTsk_status(String tsk_status) {
//                this.tsk_status = tsk_status;
//            }
//
//            public String getTsk_channel() {
//                return tsk_channel;
//            }
//
//            public void setTsk_channel(String tsk_channel) {
//                this.tsk_channel = tsk_channel;
//            }
//
//            public void setTsk_surplus(String tsk_surplus) {
//                this.tsk_surplus = tsk_surplus;
//            }
//
//            public String getTsk_describ() {
//                return tsk_describ;
//            }
//
//            public void setTsk_describ(String tsk_describ) {
//                this.tsk_describ = tsk_describ;
//            }
//
//            public String getTsk_type() {
//                return tsk_type;
//            }
//
//            public void setTsk_type(String tsk_type) {
//                this.tsk_type = tsk_type;
//            }
//
//            public String getTsk_dt() {
//                return tsk_dt;
//            }
//
//            public void setTsk_dt(String tsk_dt) {
//                this.tsk_dt = tsk_dt;
//            }
//
//            public String getTsk_tm() {
//                return tsk_tm;
//            }
//
//            public void setTsk_tm(String tsk_tm) {
//                this.tsk_tm = tsk_tm;
//            }
//
//            public String getTsk_second() {
//                return tsk_second;
//            }
//
//            public void setTsk_second(String tsk_second) {
//                this.tsk_second = tsk_second;
//            }
//
//            public String getTsk_surplus() {
//                return tsk_surplus;
//            }
//        }
//    }
//
//    public static class GroupBean {
//        /**
//         * name : 控制器1
//         * group_id : 1
//         * node_addr : 5
//         */
//
//        private String name;
//        private int group_id;
//        private String node_addr;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public int getGroup_id() {
//            return group_id;
//        }
//
//        public void setGroup_id(int group_id) {
//            this.group_id = group_id;
//        }
//
//        public String getNode_addr() {
//            return node_addr;
//        }
//
//        public void setNode_addr(String node_addr) {
//            this.node_addr = node_addr;
//        }
//    }
}
