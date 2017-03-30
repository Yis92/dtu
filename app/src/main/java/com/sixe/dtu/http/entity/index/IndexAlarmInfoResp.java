package com.sixe.dtu.http.entity.index;

import android.os.Parcel;
import android.os.Parcelable;

import com.sixe.dtu.http.util.CommonResponse;

import java.util.List;

/**
 * 报警信息返回
 * Created by Administrator on 2017/3/30.
 */

public class IndexAlarmInfoResp extends CommonResponse<List<IndexAlarmInfoResp>> implements Parcelable {

    private String name;//要素名称
    private String describ;//要素说明
    private String data_no;//这个可以不显示出来，但设置的时候需要回传
    private String up;//报警上限
    private String low;//报警下限
    private String lasting;//持续时间
    private String interval;//报警间隔
    private String enable;//是否报警



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrib() {
        return describ;
    }

    public void setDescrib(String describ) {
        this.describ = describ;
    }

    public String getData_no() {
        return data_no;
    }

    public void setData_no(String data_no) {
        this.data_no = data_no;
    }

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getLasting() {
        return lasting;
    }

    public void setLasting(String lasting) {
        this.lasting = lasting;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.describ);
        dest.writeString(this.data_no);
        dest.writeString(this.up);
        dest.writeString(this.low);
        dest.writeString(this.lasting);
        dest.writeString(this.interval);
        dest.writeString(this.enable);
    }

    public IndexAlarmInfoResp() {
    }

    protected IndexAlarmInfoResp(Parcel in) {
        this.name = in.readString();
        this.describ = in.readString();
        this.data_no = in.readString();
        this.up = in.readString();
        this.low = in.readString();
        this.lasting = in.readString();
        this.interval = in.readString();
        this.enable = in.readString();
    }

    public static final Creator<IndexAlarmInfoResp> CREATOR = new Creator<IndexAlarmInfoResp>() {
        @Override
        public IndexAlarmInfoResp createFromParcel(Parcel source) {
            return new IndexAlarmInfoResp(source);
        }

        @Override
        public IndexAlarmInfoResp[] newArray(int size) {
            return new IndexAlarmInfoResp[size];
        }
    };
}
