package com.sewamobil.sewamobil.utils;

import com.sewamobil.sewamobil.BuildConfig;

public class ProjectConstant {
    public static String sp_notation = BuildConfig.APPLICATION_ID;
    public static final String SP_username = sp_notation+"_username";
    public static final String SP_name = sp_notation+"_name";
    public static final String SP_tgllahir = sp_notation+"_tgllahir";
    public static final String SP_jk = sp_notation+"_jk";
    public static final String SP_alamat = sp_notation+"_alamat";
    public static final String SP_uid = sp_notation+"_uid";
    public static final String SP_telp = sp_notation+"_telp";
    public static final String SP_noktp = sp_notation+"_noktp";
    public static final String SP_pekerjaan = sp_notation+"_pekerjaan";
    public static final String SP_instansi = sp_notation+"_instansi";

    public static final String WEBVIEW_STYLE        = "<html> <link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet'> <body  style=\"text-align:justify;background-color: transparent;font-size:15px;color:#616161;padding:8px;font-family:'Roboto'\"> %s </body></Html>";

    public static final String API_URL = "tutorial-sourcecode.com/sewamobil/API/";
    public static final String API_REGISTER = API_URL+"";
    public static final String API_LOGIN = API_URL+"";
    public static final String API_KONFIRMASI_BOOKING = API_URL+"";
    public static final String API_CHECKBOOKING = API_URL+"";
    public static final String API_BOOKING = API_URL+"";
    public static final String API_LISTBOOKING = API_URL+"";
    public static final String API_LISTWISATA = API_URL+"";
}
