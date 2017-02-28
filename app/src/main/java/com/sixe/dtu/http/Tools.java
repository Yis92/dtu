package com.sixe.dtu.http;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
/**
 * Created by labiStronger on 2017/2/24.
 */
public class Tools {


    public static String pattern_yyyymmdd = "yyyy-MM-dd";

    public static String formatyyyyMMddhhmmss = "yyyy-MM-dd HH:mm:ss";

    public static String getString(String ss){
        if(Tools.isEmpty(ss)){
            return "";
        }
        ss = ss.toString().replaceAll("\t","").trim();
        ss = ss.toString().replaceAll("\"","").trim();
        ss = ss.toString().replaceAll("\r","").trim();
        ss = ss.toString().replaceAll("\n","").trim();
        ss = ss.toString().replace("\\","").trim();
        return ss;
    }

    /**
     * 判断对象是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null || obj.equals("") || obj.equals("null") || obj.equals("undefined")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断集合是否为空
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(List list) {
        if (null == list || list.size() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取UUID
     * @return
     */
    public static String UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * @方法名：contains
     * @功能说明：字符串包含
     * @param container
     * @param regex
     * @return
     */
    public static boolean contains(String container, String regex) {
        if (container.contains(regex)) {
            return true;
        }
        return false;
    }


    /**
     * MD5加密
     * @param source
     * @return
     */
    public static String toMD5(byte[] source) {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            StringBuffer buf=new StringBuffer();
            for(byte b:md.digest())
                buf.append(String.format("%02x", b&0xff) );
            return buf.toString();
        }catch( Exception e ){
            e.printStackTrace(); return null;
        }
    }

    public static String sendJson(Object obj) {
        try {
            // 处理返回的JSON字符串为乱码的问题
            return new com.google.gson.GsonBuilder().disableHtmlEscaping()
                    .create().toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String formatyyyyMMdd(Date date) {
        if(date==null){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern_yyyymmdd);
        return sdf.format(date);
    }

    public static String formatyyyyMMddhhmmss(Date date) {
        if(date==null){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatyyyyMMddhhmmss);
        return sdf.format(date);
    }


}
