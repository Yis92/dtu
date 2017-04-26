package com.loveplusplus.update;

class Constants {


    // json {"url":"http://192.168.205.33:8080/Hello/app_v3.0.1_Other_20150116.apk","versionCode":2,"updateMessage":"版本更新信息"}

    //    static final String APK_DOWNLOAD_URL = "url";
//    static final String APK_UPDATE_CONTENT = "updateMessage";
//    static final String APK_VERSION_CODE = "versionCode";
    static final String APK_DOWNLOAD_URL = "appUrl";
    static final String APK_UPDATE_CONTENT = "appDesc";
    static final String APK_VERSION_CODE = "verCode";


    static final int TYPE_NOTIFICATION = 2;

    static final int TYPE_DIALOG = 1;

    static final String TAG = "UpdateChecker";

    //    static final String UPDATE_URL = "https://raw.githubusercontent.com/feicien/android-auto-update/develop/extras/update.json";
//    static final String UPDATE_URL = "http://api.gb6m.com/index/index.php?c=index&m=userOther&a=updateVersionNew";
    static final String UPDATE_URL = "http://139.129.239.172:8080/comSys/app/version";
}
