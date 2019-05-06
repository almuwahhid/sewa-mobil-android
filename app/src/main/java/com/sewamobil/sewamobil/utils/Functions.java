package com.sewamobil.sewamobil.utils;

import android.content.Context;
import android.util.Log;

import com.sewamobil.sewamobil.menu.biodata.Model.UserModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import lib.almuwahhid.utils.LibUi;

public class Functions {
    public static String rupiahFormat(float rupiah){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(rupiah);
    }

    public static void saveUserPreferenece(Context context, UserModel model){
        Log.d("ehm", "saveUserPreferenece: "+model.getTelp());
        LibUi.setSPString(context, ProjectConstant.SP_uid, model.getId_member());
        LibUi.setSPString(context, ProjectConstant.SP_alamat, model.getAlamat());
        LibUi.setSPString(context, ProjectConstant.SP_jk, model.getJenis_kelamin());
        LibUi.setSPString(context, ProjectConstant.SP_name, model.getNama_lengkap());
        LibUi.setSPString(context, ProjectConstant.SP_telp, model.getTelp());
        LibUi.setSPString(context, ProjectConstant.SP_tgllahir, model.getTgl_lahir());
        LibUi.setSPString(context, ProjectConstant.SP_username, model.getUsername());
        LibUi.setSPString(context, ProjectConstant.SP_instansi, model.getInstansi());
        LibUi.setSPString(context, ProjectConstant.SP_pekerjaan, model.getPekerjaan());
        LibUi.setSPString(context, ProjectConstant.SP_noktp, model.getNo_ktp());
    }

    public static UserModel getUser(Context context){
        UserModel model = new UserModel();
        model.setId_member(LibUi.getSPString(context, ProjectConstant.SP_uid));
        model.setAlamat(LibUi.getSPString(context, ProjectConstant.SP_alamat));
        model.setJenis_kelamin(LibUi.getSPString(context, ProjectConstant.SP_jk));
        model.setNama_lengkap(LibUi.getSPString(context, ProjectConstant.SP_name));
        model.setTelp(LibUi.getSPString(context, ProjectConstant.SP_telp));
        model.setTgl_lahir(LibUi.getSPString(context, ProjectConstant.SP_tgllahir));
        model.setUsername(LibUi.getSPString(context, ProjectConstant.SP_username));
        model.setInstansi(LibUi.getSPString(context, ProjectConstant.SP_instansi));
        model.setPekerjaan(LibUi.getSPString(context, ProjectConstant.SP_pekerjaan));
        model.setNo_ktp(LibUi.getSPString(context, ProjectConstant.SP_noktp));
        return  model;
    }

    public static void removeUserPreference(Context context){
        LibUi.removeSPString(context, ProjectConstant.SP_uid);
        LibUi.removeSPString(context, ProjectConstant.SP_alamat);
        LibUi.removeSPString(context, ProjectConstant.SP_jk);
        LibUi.removeSPString(context, ProjectConstant.SP_name);
        LibUi.removeSPString(context, ProjectConstant.SP_telp);
        LibUi.removeSPString(context, ProjectConstant.SP_tgllahir);
        LibUi.removeSPString(context, ProjectConstant.SP_uid);
        LibUi.removeSPString(context, ProjectConstant.SP_username);
        LibUi.removeSPString(context, ProjectConstant.SP_instansi);
        LibUi.removeSPString(context, ProjectConstant.SP_pekerjaan);
        LibUi.removeSPString(context, ProjectConstant.SP_noktp);
    }

    public static boolean isUserLogin(Context context){
        if(LibUi.getSPString(context, ProjectConstant.SP_uid).equals(""))
            return false;
         else
            return true;
    }
}
